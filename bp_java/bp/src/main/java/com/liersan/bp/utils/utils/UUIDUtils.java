package com.liersan.bp.utils.utils;

import java.util.UUID;

/**
 * UUID工具类 , 生成36位ID
 */
public class UUIDUtils {
    public static String UUID(){
        return UUID.randomUUID().toString();
    }
}
