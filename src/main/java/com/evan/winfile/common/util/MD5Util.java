package com.evan.winfile.common.util;

import org.springframework.util.DigestUtils;

/**
 * @author Evan
 * @date 2022-11-18
 */
public class MD5Util {
    public static String md5(String path){
        return DigestUtils.md5DigestAsHex(path.getBytes());
    }
}