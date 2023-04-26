package com.evan.winfile.common.util;

import java.util.UUID;

/**
 * @author Evan
 * @date 2020-07-06
 */
public class UUIDGenerator {
    public static String generate() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}