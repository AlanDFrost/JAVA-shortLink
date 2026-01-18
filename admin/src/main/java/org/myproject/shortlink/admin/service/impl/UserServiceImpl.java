package org.myproject.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.myproject.shortlink.admin.common.constant.RedisCacheConstant;
import org.myproject.shortlink.admin.common.convention.exception.ClientException;
import org.myproject.shortlink.admin.common.enums.UserErrorCodeEnum;
import org.myproject.shortlink.admin.dao.entity.UserDO;
import org.myproject.shortlink.admin.dao.mapper.UserMapper;
import org.myproject.shortlink.admin.dto.request.UserLoginReqDTO;
import org.myproject.shortlink.admin.dto.request.UserRegisterReqDTO;
import org.myproject.shortlink.admin.dto.request.UserUpdateReqDTO;
import org.myproject.shortlink.admin.dto.response.UserLoginRespDTO;
import org.myproject.shortlink.admin.dto.response.UserRespDTO;
import org.myproject.shortlink.admin.service.GroupService;
import org.myproject.shortlink.admin.service.UserService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/*
* 用户接口实现层
* */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;
    private final RedissonClient redissonClient;
    private final StringRedisTemplate stringRedisTemplate;
    private final GroupService groupService;

    @Override
    public UserRespDTO getUserByUsername(String username) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class).eq(UserDO::getUserName, username);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        if (userDO == null) throw new ClientException(UserErrorCodeEnum.USER_NOTFOUND);

        UserRespDTO result = new UserRespDTO();
        BeanUtils.copyProperties(userDO, result);
        return result;
    }

    @Override
    public Boolean hasUserName(String username) {
        return !userRegisterCachePenetrationBloomFilter.contains(username);
    }

    @Override
    public void register(UserRegisterReqDTO requestParam) {
        if (!hasUserName(requestParam.getUserName())) {
            throw new ClientException(UserErrorCodeEnum.USER_NAME_EXIST);
        }
        RLock lock = redissonClient.getLock(RedisCacheConstant.LOCK_USER_register_KEY + requestParam.getUserName());
        try {
            if (lock.tryLock()) {
                int inserted = baseMapper.insert(BeanUtil.toBean(requestParam, UserDO.class));
                if (inserted < 1) throw new ClientException(UserErrorCodeEnum.USER_SAVE_FAILED);

                userRegisterCachePenetrationBloomFilter.add(requestParam.getUserName());
                groupService.saveGroup(requestParam.getUserName(), "默认分组");
                return;
            }
            throw new ClientException(UserErrorCodeEnum.USER_NAME_EXIST);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void update(UserUpdateReqDTO requestParam) {
        // TODO：验证当前用户是否为登录用户(智能更改自己的信息)
        LambdaUpdateWrapper<UserDO> updateWrapper = Wrappers.lambdaUpdate(UserDO.class)
                .eq(UserDO::getUserName, requestParam.getUserName());
        baseMapper.update(BeanUtil.toBean(requestParam, UserDO.class), updateWrapper);
    }

    @Override
    public UserLoginRespDTO login(UserLoginReqDTO requestParam) {
        /*
        登录必须满足3个eq：1.用户名存在 2.密码正确 3.用户没有被注销
        登录应该验证是否应景登录，否则每次点击登录都可以重复登录
         */
         LambdaQueryWrapper<UserDO> wrapper = Wrappers.lambdaQuery(UserDO.class)
                 .eq(UserDO::getUserName, requestParam.getUsername())
                 .eq(UserDO::getPassword, requestParam.getPassword())
                 .eq(UserDO::getDelFlag, 0);
        UserDO userDO = baseMapper.selectOne(wrapper);
         if (userDO == null) throw new ClientException(UserErrorCodeEnum.USER_NOTFOUND);

         Boolean haslogin = stringRedisTemplate.hasKey("login_" + requestParam.getUsername());
         if (haslogin != null && haslogin) {
             stringRedisTemplate.expire("login_" + requestParam.getUsername(), 30L, TimeUnit.DAYS);
             throw new ClientException("用户已登陆，请退出后重试");
         }
         String uuid = UUID.randomUUID().toString();
         /*
         防止重复登陆，用哈希表
         Key: login_用户名
         Value: key（token标识） val：json字符串（session）
          */
        stringRedisTemplate.opsForHash().put("login_" + requestParam.getUsername(), uuid, JSON.toJSONString(userDO));
        stringRedisTemplate.expire("login_" + requestParam.getUsername(), 30L, TimeUnit.DAYS);
        return new UserLoginRespDTO(uuid);
    }

    @Override
    public Boolean checklogin(String username, String token) {
        Object remoteToken = stringRedisTemplate.opsForHash().get("login_" + username, token);
        return remoteToken != null;
    }

    @Override
    public void logout(String username, String token) {
        if (checklogin(username, token)) {
            stringRedisTemplate.delete("login_" + username);
            return;
        }
        throw new ClientException("用户token不存在或者未登录");
    }
}
