package org.myproject.shortlink.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.myproject.shortlink.admin.dao.entity.UserDO;
import org.myproject.shortlink.admin.dto.request.UserLoginReqDTO;
import org.myproject.shortlink.admin.dto.request.UserRegisterReqDTO;
import org.myproject.shortlink.admin.dto.request.UserUpdateReqDTO;
import org.myproject.shortlink.admin.dto.response.UserLoginRespDTO;
import org.myproject.shortlink.admin.dto.response.UserRespDTO;

/*
* 用户接口层
* */
public interface UserService extends IService<UserDO> {

    /*
    * 根据用户名称返回用户信息
    * @param： username 用户名
    * @return： 用户返回实体
    */
    UserRespDTO getUserByUsername(String username);

    /*
    查询用户名是否已存在（已存在=不可用，不存在=可用）
     */
    Boolean hasUserName(String username);

    /*
    注册用户
    @param：注册用户实体
     */
    void register(UserRegisterReqDTO requestParam);

    /*
    根据用户名更新用户信息
     */
    void update(UserUpdateReqDTO requestParam);

    /*
    用户登录实现
     */
    UserLoginRespDTO login(UserLoginReqDTO userLoginReqDTO);

    /*
    验证用户登录
     */
    Boolean checklogin(String username, String token);

    /*
    用户退出登录
     */
    void logout(String username, String token);
}
