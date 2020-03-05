package com.liersan.bp.utils.utils;

import java.util.regex.Pattern;

/**
 * 正则表达式校验封装
 */
public class RegExpUtils {
    //手机号码
    public static Boolean phone(String phone){
        return Pattern.matches("^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9]|17[0-8])\\d{8}$", phone);
    }

    //密码:以字母开头，长度在6~18之间，只能包含字母、数字和下划线
    public static Boolean password(String password){
        return Pattern.matches("^[a-zA-Z]\\w{5,17}$", password);
    }

    //昵称:长度为1-15之间的所有字符串
    public static Boolean username(String username){
        return Pattern.matches("^.{1,15}$",username);
    }
}
