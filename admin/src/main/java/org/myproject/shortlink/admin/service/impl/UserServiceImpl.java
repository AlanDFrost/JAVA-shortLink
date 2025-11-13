package org.myproject.shortlink.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.myproject.shortlink.admin.dao.entity.UserDO;
import org.myproject.shortlink.admin.dao.mapper.UserMapper;
import org.myproject.shortlink.admin.dto.response.UserRespDTO;
import org.myproject.shortlink.admin.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/*
* 用户接口实现曾
* */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {
    @Override
    public UserRespDTO getUserByUsername(String username) {
        LambdaQueryWrapper<UserDO>  queryWrapper = Wrappers.lambdaQuery(UserDO.class).eq(UserDO::getUserName, username);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        System.out.println("查询结果：" + userDO);
        UserRespDTO result = new UserRespDTO();
        BeanUtils.copyProperties(userDO, result);
        return result;
    }
}
