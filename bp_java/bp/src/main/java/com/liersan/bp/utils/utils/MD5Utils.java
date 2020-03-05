package com.liersan.bp.utils.utils;

import org.springframework.util.DigestUtils;

/**
 * MD5加密工具,生成32位加密的密码
 */
public class MD5Utils {
    public static String MD5Salt(String password , String salt){
        return DigestUtils.md5DigestAsHex((password+salt).getBytes());
    }

    public static String MD5NoSalt(String password){
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }
}
