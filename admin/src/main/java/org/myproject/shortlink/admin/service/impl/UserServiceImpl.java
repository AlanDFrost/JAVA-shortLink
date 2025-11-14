package org.myproject.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.myproject.shortlink.admin.common.convention.exception.ClientException;
import org.myproject.shortlink.admin.common.enums.UserErrorCodeEnum;
import org.myproject.shortlink.admin.dao.entity.UserDO;
import org.myproject.shortlink.admin.dao.mapper.UserMapper;
import org.myproject.shortlink.admin.dto.request.UserRegisterReqDTO;
import org.myproject.shortlink.admin.dto.response.UserRespDTO;
import org.myproject.shortlink.admin.service.UserService;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/*
* 用户接口实现层
* */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;

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
        int inserted = baseMapper.insert(BeanUtil.toBean(requestParam, UserDO.class));
        if (inserted < 1) throw new ClientException(UserErrorCodeEnum.USER_SAVE_FAILED);

        userRegisterCachePenetrationBloomFilter.add(requestParam.getUserName());
    }
}
