package org.myproject.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Week;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.myproject.shortlink.project.common.convention.exception.ClientException;
import org.myproject.shortlink.project.common.convention.exception.ServiceException;
import org.myproject.shortlink.project.common.enums.ValidDateTypeEnum;
import org.myproject.shortlink.project.dao.entity.LinkAccessStatsDO;
import org.myproject.shortlink.project.dao.entity.ShortLinkDO;
import org.myproject.shortlink.project.dao.entity.ShortLinkGoToDO;
import org.myproject.shortlink.project.dao.mapper.LinkAccessStatsMapper;
import org.myproject.shortlink.project.dao.mapper.ShortLinkGoToMapper;
import org.myproject.shortlink.project.dao.mapper.ShortLinkMapper;
import org.myproject.shortlink.project.dto.request.ShortLinkCreateReqDTO;
import org.myproject.shortlink.project.dto.request.ShortLinkPageReqDTO;
import org.myproject.shortlink.project.dto.request.ShortLinkUpdateReqDTO;
import org.myproject.shortlink.project.dto.response.ShortLinkCreateRespDTO;
import org.myproject.shortlink.project.dto.response.ShortLinkGroupCountQueryRespDTO;
import org.myproject.shortlink.project.dto.response.ShortLinkPageRespDTO;
import org.myproject.shortlink.project.service.ShortLinkService;
import org.myproject.shortlink.project.toolkit.HashUtil;
import org.myproject.shortlink.project.toolkit.LinkUtil;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.myproject.shortlink.project.common.constant.RedisKeyConstant.*;

/*
短链接接口实现层
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ShortLinkServiceimpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO> implements ShortLinkService {
    private final RBloomFilter<String> shotUriCreateCachePenetrationBloomFilter;
    private final ShortLinkGoToMapper shortLinkGoToMapper;
    private final StringRedisTemplate stringRedisTemplate;
    private final RedissonClient redissonClient;
    private final LinkAccessStatsMapper linkAccessStatsMapper;

    @Override
    public ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO requestparam) {
        String shortLinkSuffix = generateSuffix(requestparam);
        String fullShortUrl = requestparam.getDomain() + "/" + shortLinkSuffix;
        ShortLinkDO shortLinkDO = BeanUtil.toBean(requestparam, ShortLinkDO.class);
        shortLinkDO.setShortUri(shortLinkSuffix);
        shortLinkDO.setFullShortUrl(fullShortUrl);

        ShortLinkGoToDO shortLinkGoToDO = ShortLinkGoToDO.builder().fullShortUrl(fullShortUrl).gid(requestparam.getGid()).build();
        try {
            baseMapper.insert(shortLinkDO);
            shortLinkGoToMapper.insert(shortLinkGoToDO);
        } catch (DuplicateKeyException ex) {
            LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                            .eq(ShortLinkDO::getFullShortUrl, fullShortUrl);
            ShortLinkDO hasShortLinkDO = baseMapper.selectOne(queryWrapper);
            if (hasShortLinkDO != null) {
                log.warn("短链接 {} 重复入库", fullShortUrl);
                throw new ServiceException("短链接生成重复");
            }
        }
        /** 缓存预热 */
        stringRedisTemplate.opsForValue().set(
                String.format(GOTO_SHORT_LINK_KEY, fullShortUrl),
                requestparam.getOriginUrl(),
                LinkUtil.getLinkCacheValidTime(requestparam.getValidDate()), TimeUnit.MILLISECONDS
        );
        shotUriCreateCachePenetrationBloomFilter.add(fullShortUrl);
        return ShortLinkCreateRespDTO.builder()
                .fullShortUrl(shortLinkDO.getFullShortUrl())
                .originUrl(requestparam.getOriginUrl())
                .gid(requestparam.getGid())
                .build();
    }

    @Override
    public IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO requestparam) {
        LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                .eq(ShortLinkDO::getGid, requestparam.getGid())
                .eq(ShortLinkDO::getDelFlag, 0)
                .eq(ShortLinkDO::getEnableStatus, 0);
        IPage<ShortLinkDO> resultPage = baseMapper.selectPage(requestparam, queryWrapper);

        return resultPage.convert(each -> BeanUtil.toBean(each, ShortLinkPageRespDTO.class));
    }

    @Override
    public List<ShortLinkGroupCountQueryRespDTO> listGroupShortLinkCount(List<String> gids) {
        LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                        .in(ShortLinkDO::getGid, gids)
                        .eq(ShortLinkDO::getEnableStatus, 0)
                        .groupBy(ShortLinkDO::getGid)
                        .select(ShortLinkDO::getGid);
        List<Map<String, Object>> sortLinkDOList = baseMapper.selectMaps(queryWrapper);
        return BeanUtil.copyToList(sortLinkDOList, ShortLinkGroupCountQueryRespDTO.class);
    }

    @SneakyThrows
    @Override
    public void restoreUrl(String shortUri, ServletRequest request, ServletResponse response) {
        String serverName = request.getServerName();
        String fullShortUrl = "http://" + serverName + "/" + shortUri;

        // 先去 Redis 里面查有没有短链接。如果没有再去布隆过滤器查，不存在直接返回，存在再去缓存查是否是null，不是再拿锁去数据库查
        String originalLink = stringRedisTemplate.opsForValue().get(String.format(GOTO_SHORT_LINK_KEY, fullShortUrl));
        if (StrUtil.isNotBlank(originalLink)) {
            ((HttpServletResponse)response).sendRedirect(originalLink);
            shortLinkStats(fullShortUrl, null, request, response);
            return ;
        }

        boolean isContained = shotUriCreateCachePenetrationBloomFilter.contains(fullShortUrl);
        if (!isContained) {
            ((HttpServletResponse)response).sendRedirect("/page/notfound");
            return ;
        }
        String gotoIsNullShortLink = stringRedisTemplate.opsForValue().get(String.format(GOTO_IS_NULL_SHORT_LINK_KEY, fullShortUrl));
        if (StrUtil.isNotBlank(gotoIsNullShortLink)) {
            ((HttpServletResponse)response).sendRedirect("/page/notfound");
            return;
        }

        RLock lock = redissonClient.getLock(String.format(LOCK_GOTO_SHORT_LINK_KEY, fullShortUrl));
        lock.lock();
        try {
            /** 双重判定锁，后续拿到的锁可以执行以跳过去数据库查找的时间 */
            originalLink = stringRedisTemplate.opsForValue().get(String.format(GOTO_SHORT_LINK_KEY, fullShortUrl));
            if (StrUtil.isNotBlank(originalLink)) {
                shortLinkStats(fullShortUrl, null, request, response);
                ((HttpServletResponse)response).sendRedirect(originalLink);
                return ;
            }
            LambdaQueryWrapper<ShortLinkGoToDO> goToDOqueryWrapper = Wrappers.lambdaQuery(ShortLinkGoToDO.class).eq(ShortLinkGoToDO::getFullShortUrl, fullShortUrl);
            ShortLinkGoToDO shortLinkGoToDO = shortLinkGoToMapper.selectOne(goToDOqueryWrapper);
            if (shortLinkGoToDO == null) {
                stringRedisTemplate.opsForValue().set(StrUtil.format(GOTO_IS_NULL_SHORT_LINK_KEY, fullShortUrl), "-", 30, TimeUnit.SECONDS);
                ((HttpServletResponse)response).sendRedirect("/page/notfound");
                return;
            }

            LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                    .eq(ShortLinkDO::getGid, shortLinkGoToDO.getGid())
                    .eq(ShortLinkDO::getFullShortUrl, fullShortUrl)
                    .eq(ShortLinkDO::getEnableStatus, 0)
                    .eq(ShortLinkDO::getDelFlag, 0);
            ShortLinkDO shortLinkDO = baseMapper.selectOne(queryWrapper);
            if (shortLinkDO == null || (LinkUtil.getLinkCacheValidTime(shortLinkDO.getValidDate()) == 0)) {
                stringRedisTemplate.opsForValue().set(StrUtil.format(GOTO_IS_NULL_SHORT_LINK_KEY, fullShortUrl), "-", 30, TimeUnit.SECONDS);
                ((HttpServletResponse)response).sendRedirect("/page/notfound");
                return;
            }

            stringRedisTemplate.opsForValue().set(
                    String.format(GOTO_SHORT_LINK_KEY, fullShortUrl),
                    shortLinkDO.getOriginUrl(),
                    LinkUtil.getLinkCacheValidTime(shortLinkDO.getValidDate()), TimeUnit.MILLISECONDS
            );
            stringRedisTemplate.opsForValue().set(StrUtil.format(GOTO_SHORT_LINK_KEY, fullShortUrl), shortLinkDO.getOriginUrl());
            shortLinkStats(fullShortUrl, shortLinkDO.getGid(), request, response);
            ((HttpServletResponse)response).sendRedirect(shortLinkDO.getOriginUrl());
        } finally {
            lock.unlock();
        }
    }

    private void shortLinkStats(String fullShortUrl, String gid, ServletRequest request, ServletResponse response) {
        if (StrUtil.isBlank(gid)) {
            LambdaQueryWrapper<ShortLinkGoToDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkGoToDO.class).eq(ShortLinkGoToDO::getFullShortUrl, fullShortUrl);
            ShortLinkGoToDO shortLinkGoToDO = shortLinkGoToMapper.selectOne(queryWrapper);
            gid = shortLinkGoToDO.getGid();
        }

        AtomicBoolean uvFirstFlag = new AtomicBoolean();
        Cookie[] cookies = ((HttpServletRequest) request).getCookies();
        Runnable addReqsponseCookiesTask = () -> {
            String uv = UUID.fastUUID().toString();
            Cookie uvCookie = new Cookie("uv", uv);
            uvCookie.setMaxAge(60 * 60 * 24 * 30);
            String path = URLUtil.url(fullShortUrl).getPath();
            uvCookie.setPath(path);
            ((HttpServletResponse) response).addCookie(uvCookie);
            uvFirstFlag.set(Boolean.TRUE);
            stringRedisTemplate.opsForSet().add("short-link:stats:uv:" + fullShortUrl, uv);
        };
        if (ArrayUtil.isNotEmpty(cookies)) {
            Arrays.stream(cookies).filter(each -> Objects.equals(each.getName(), "uv")).findFirst()
                    .map(Cookie::getValue).ifPresentOrElse(
                            each -> {
                                Long uvAdded = stringRedisTemplate.opsForSet().add("short-link:stats:uv:" + fullShortUrl, each);
                                uvFirstFlag.set(uvAdded != null && uvAdded > 0);
                            }, addReqsponseCookiesTask);
        } else {
            addReqsponseCookiesTask.run();
        }
        String remoteAddr = LinkUtil.getActualIp((HttpServletRequest) request);
        Long uipAdded = stringRedisTemplate.opsForSet().add("short-link:stats:uv:" + fullShortUrl, remoteAddr);
        boolean uipFirstFlag = uipAdded != null && uipAdded > 0;

        int hour = DateUtil.hour(new Date(), true);
        Week week = DateUtil.dayOfWeekEnum(new Date());
        int weekValue = week.getIso8601Value();

        LinkAccessStatsDO linkAccessStatsDO = LinkAccessStatsDO.builder()
                .pv(1)
                .uv(uvFirstFlag.get() ? 1 : 0)
                .uip(uipFirstFlag ? 1 : 0)
                .hour(hour)
                .weekday(weekValue)
                .fullShortUrl(fullShortUrl)
                .gid(gid)
                .date(new Date())
                .build();
        linkAccessStatsMapper.shortLinkStats(linkAccessStatsDO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateShortLink(ShortLinkUpdateReqDTO requestparam) {
        LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                .eq(ShortLinkDO::getGid, requestparam.getGid())
                .eq(ShortLinkDO::getFullShortUrl, requestparam.getFullShortUrl())
                .eq(ShortLinkDO::getDelFlag, 0)
                .eq(ShortLinkDO::getEnableStatus, 0);
        ShortLinkDO hasShortLinkDO = baseMapper.selectOne(queryWrapper);
        if (hasShortLinkDO == null) {
            throw new ClientException("短链接记录不存在");
        }

        ShortLinkDO shortLinkDO = ShortLinkDO.builder()
                .domain(hasShortLinkDO.getDomain())
                .shortUri(hasShortLinkDO.getShortUri())
                .clickNum(hasShortLinkDO.getClickNum())
                .favicon(hasShortLinkDO.getFavicon())
                .createdType(hasShortLinkDO.getCreatedType())
                .gid(requestparam.getGid())
                .originUrl(requestparam.getOriginUrl())
                .describe(requestparam.getDescribe())
                .validDateType(requestparam.getValidDateType())
                .validDate(requestparam.getValidDate())
                .build();

        if (Objects.equals(hasShortLinkDO.getGid(), requestparam.getGid())) {
            LambdaUpdateWrapper<ShortLinkDO> updateWrapper =  Wrappers.lambdaUpdate(ShortLinkDO.class).eq(ShortLinkDO::getFullShortUrl, requestparam.getFullShortUrl())
                    .eq(ShortLinkDO::getGid, requestparam.getGid())
                    .eq(ShortLinkDO::getEnableStatus, 0)
                    .eq(ShortLinkDO::getDelFlag, 0)
                    .set(Objects.equals(requestparam.getValidDateType(), ValidDateTypeEnum.PERMANENT.getType()), ShortLinkDO::getValidDate, null);
            baseMapper.update(shortLinkDO, updateWrapper);
        } else {
            LambdaUpdateWrapper<ShortLinkDO> updateWrapper =  Wrappers.lambdaUpdate(ShortLinkDO.class).eq(ShortLinkDO::getFullShortUrl, requestparam.getFullShortUrl())
                    .eq(ShortLinkDO::getGid, requestparam.getGid())
                    .eq(ShortLinkDO::getEnableStatus, 0)
                    .eq(ShortLinkDO::getDelFlag, 0)
                    .set(Objects.equals(requestparam.getValidDateType(), ValidDateTypeEnum.PERMANENT.getType()), ShortLinkDO::getValidDate, null);
            baseMapper.delete(updateWrapper);
            baseMapper.insert(shortLinkDO);
        }
    }

    private String generateSuffix(ShortLinkCreateReqDTO requestparam) {
        int customGnerateCount = 0;
        String shortUri;
        String originalUrl = requestparam.getOriginUrl();
        while (true) {
            if (customGnerateCount > 10) {
                throw new ServiceException("短链接频繁生成，请稍后再试");
            }
            originalUrl += System.currentTimeMillis();
            shortUri = HashUtil.hashToBase62(originalUrl);

            if (!shotUriCreateCachePenetrationBloomFilter.contains(requestparam.getDomain() + "/" + shortUri)) {
                break;
            }
            customGnerateCount++;
        }
        return shortUri;
    }
}
