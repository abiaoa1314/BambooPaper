package com.liersan.bp.service;

import com.liersan.bp.dto.UserDTO;
import com.liersan.bp.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 用户service层接口
 */
public interface UserService {
    /**
     * 用户登录/注册方法
     * @param user 用户信息,里面有用户的手机号和密码
     * @return 返回不包含敏感信息的dto对象
     */
    UserDTO userLogin(User user);

    /**
     * 重新设置用户密码
     * @param map map其中包含用户手机号 , 新密码 , 和旧密码
     */
    void userReset(Map<String,String> map);

    /**
     * 重设用户昵称
     * @param map 其中包括用户id和新昵称
     * @return 返回不包含用户敏感信息的dto对象
     */
    UserDTO resetUsername(Map<String, String> map);

    /**
     * 修改用户头像
     * @param uId 用户的id
     * @param file 图片
     * @return 不包含敏感信息的用户DTO对象
     */
    UserDTO resetImgUrl(String uId , MultipartFile file);

    /**
     * 获得全部用户的dto信息
     * @return 返回所有用户的dto信息
     */
    List<UserDTO> getAllUser();

    /**
     * 通过用户名查找对应的用户信息
     * @param username 查找的用户名
     * @return 对应的用户集合
     */
    List<UserDTO> getUserByUsername(String username);

    /**
     * 通过用户id精确查找用户信息
     * @param uId 用户id
     * @return 返回对应的用户信息
     */
    UserDTO getUserByUId(String uId);

    /**
     * 通过用户id设置用户为博主
     * @param uId 用户id
     */
    void putUserZero(String uId);

    /**
     * 通过用户id设置用户为用户
     * @param uId 用户id
     */
    void putUserOne(String uId);
}
