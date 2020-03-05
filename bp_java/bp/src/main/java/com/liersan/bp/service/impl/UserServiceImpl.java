package com.liersan.bp.service.impl;

import com.liersan.bp.dto.UserDTO;
import com.liersan.bp.entity.User;
import com.liersan.bp.mapper.UserMapper;
import com.liersan.bp.service.UserService;
import com.liersan.bp.utils.entity.StatusCode;
import com.liersan.bp.utils.exception.BpException;
import com.liersan.bp.utils.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * 用户service层实现类
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登录/注册方法
     * @param user 用户信息,里面有用户的手机号和密码
     * @return 返回不包含敏感信息的dto对象
     */
    @Transactional
    public UserDTO userLogin(User user) {
        if (!RegExpUtils.phone(user.getAccount()))
            throw new BpException(StatusCode.InvalidPhone);
        if (!RegExpUtils.password(user.getPassword()))
            throw new BpException(StatusCode.InvalidPassword);
        //先查看是否有这个手机号,若是没有这个手机号,则进行注册,若是有这个手机号,则进行密码加密再次校验
        User dbSelectUser = new User();
        dbSelectUser.setAccount(user.getAccount());
        int userCount = userMapper.selectCount(dbSelectUser);
        //对密码进行加密
        user.setPassword(MD5Utils.MD5Salt(user.getPassword(),user.getAccount()));
        //若是查询到的数据不为1 , 则用户没有注册 , 给用户进行注册
        if (userCount != 1){
            //用户名为手机号
            user.setUsername(user.getAccount());
            //id为uuid随机生成
            user.setUId(UUIDUtils.UUID());
            //头像为默认头像
            user.setImgUrl("../../static/logo/logo.png");
            // 查询全部用户 若是第一个注册 则默认为博主
            List<User> userList = userMapper.selectAll();
            if (userList.size() == 0)
                user.setStatus(0);
            //插入进数据库
            int count = userMapper.insert(user);
            //只要不为1 则操作失败 抛出异常
            if (count != 1)
                throw new BpException(StatusCode.UserRegisterFailure);
            //转换成dto对象返回给前端
            UserDTO userDTO = BeanHelper.copyProperties(user, UserDTO.class);
            return userDTO;
        }
        // 若是为1 则表明用户已经注册过 就进行密码校验
        User verifyPassword = userMapper.selectOne(user);
        //若是这次校验密码为空 , 则抛出异常 , 说明其密码错误
        if (verifyPassword == null)
            throw new BpException(StatusCode.InvalidPassword);
        //若是没有没有抛出异常 , 则向前端返回dto对象
        UserDTO userDTO = BeanHelper.copyProperties(verifyPassword, UserDTO.class);
        return userDTO;
    }

    /**
     * 重新设置用户密码
     * @param map map其中包含用户手机号 , 新密码 , 和旧密码
     */
    @Transactional
    public void userReset(Map<String,String> map) {
        System.out.println(map);
        if (!RegExpUtils.phone(map.get("account")))
            throw new BpException(StatusCode.InvalidPhone);
        if (!RegExpUtils.password(map.get("oldPassword")) || !RegExpUtils.password(map.get("newPassword")))
            throw new BpException(StatusCode.InvalidPassword);
        //首先查出是不是有这个手机号和这个老密码
        User dbSelectUser = new User();
        dbSelectUser.setAccount(map.get("account"));
        dbSelectUser.setPassword(MD5Utils.MD5Salt(map.get("oldPassword"),map.get("account")));
        User selectOne = userMapper.selectOne(dbSelectUser);
        // 通过手机号和旧密码查出这个用户若是为空
        // 则证明密码或者手机号输入错误 , 抛出异常
        if (selectOne == null)
            throw new BpException(StatusCode.InvalidUserReset);
        //若是不为空 则更新新密码
        selectOne.setPassword(MD5Utils.MD5Salt(map.get("newPassword"),map.get("account")));
        int count = userMapper.updateByPrimaryKey(selectOne);
        // 不为1 则更新失败 抛出异常
        if (count != 1)
            throw new BpException(StatusCode.InvalidUserReset);
    }

    /**
     * 重设用户昵称
     * @param map 其中包括用户id和新昵称
     * @return 返回不包含用户敏感信息的dto对象
     */
    @Transactional
    public UserDTO resetUsername(Map<String, String> map) {
        System.out.println(map);
        //先检验昵称 , 若是昵称不正确 , 则抛出异常
        if (!RegExpUtils.username(map.get("username")))
            throw new BpException(StatusCode.InvalidUsername);
        User user = new User();
        user.setUId(map.get("uId"));
        // 首先查出此用户
        User selectOne = userMapper.selectOne(user);
        selectOne.setUsername(map.get("username"));
        //再次进行更新
        int count = userMapper.updateByPrimaryKey(selectOne);
        // 如果不为1 则抛出异常
        if (count != 1)
            throw new BpException(StatusCode.InvalidUsername);
        //转换成dto对象返回给前端
        UserDTO userDTO = BeanHelper.copyProperties(selectOne, UserDTO.class);
        return userDTO;
    }

    /**
     * 修改用户头像
     * @param uId 用户的id
     * @param file 图片
     * @return 不包含敏感信息的用户DTO对象
     */
    @Transactional
    public UserDTO resetImgUrl(String uId , MultipartFile file) {
        if (StringUtils.isEmpty(uId) || file == null)
            throw new BpException(StatusCode.FileUploadError);
        String imgUrl = OSSUtils.upload(file);
        User user = new User();
        user.setUId(uId);
        //通过id查询到此用户
        User selectUser = userMapper.selectOne(user);
        //重新设置用户的头像地址
        selectUser.setImgUrl(imgUrl);
        //然后进行更新
        int count = userMapper.updateByPrimaryKey(selectUser);
        //若是不为1则更新失败
        if (count != 1)
            throw new BpException(StatusCode.FileUploadError);
        UserDTO userDTO = BeanHelper.copyProperties(selectUser, UserDTO.class);
        return userDTO;
    }

    /**
     * 获得全部用户的dto信息
     * @return 返回所有用户的dto信息
     */
    @Override
    public List<UserDTO> getAllUser() {
        List<User> userList = userMapper.selectAll();
        List<UserDTO> userDTOList = BeanHelper.copyWithCollection(userList, UserDTO.class);
        return userDTOList;
    }

    /**
     * 通过用户名查找对应的用户信息
     * @param username 查找的用户名
     * @return 对应的用户集合
     */
    @Override
    public List<UserDTO> getUserByUsername(String username) {
        if (username.equals("null"))
            username = "";
        Example example = new Example(User.class);
        example.createCriteria().andLike("username","%" + username + "%");
        List<User> userList = userMapper.selectByExample(example);
        // 转换成dto集合
        List<UserDTO> userDTOList = BeanHelper.copyWithCollection(userList, UserDTO.class);
        return userDTOList;
    }

    /**
     * 通过用户id精确查找用户信息
     * @param uId 用户id
     * @return 返回对应的用户信息
     */
    @Override
    public UserDTO getUserByUId(String uId) {
        User user = userMapper.selectByPrimaryKey(uId);
        UserDTO userDTO = BeanHelper.copyProperties(user, UserDTO.class);
        return userDTO;
    }

    /**
     * 通过用户id设置用户为博主
     * @param uId 用户id
     */
    @Override
    @Transactional
    public void putUserZero(String uId) {
        User user = userMapper.selectByPrimaryKey(uId);
        user.setStatus(0);
        int count = userMapper.updateByPrimaryKey(user);
        if (count != 1)
            throw new BpException(StatusCode.Failure);
    }

    /**
     * 通过用户id设置用户为用户
     * @param uId 用户id
     */
    @Override
    @Transactional
    public void putUserOne(String uId) {
        User user = userMapper.selectByPrimaryKey(uId);
        user.setStatus(1);
        int count = userMapper.updateByPrimaryKey(user);
        if (count != 1)
            throw new BpException(StatusCode.Failure);
    }
}
