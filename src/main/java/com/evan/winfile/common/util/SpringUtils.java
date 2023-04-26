package com.evan.winfile.common.util;

import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Evan
 * @date 2022-09-28
 */
public class SpringUtils {
    private static ApplicationContext CONTEXT;

    public static void setContext(ApplicationContext context){
        synchronized (SpringUtils.class){
            if(CONTEXT==null){
                CONTEXT = context;
            }
        }
    }

    public static ApplicationContext getContext() {
        return CONTEXT;
    }

    public static <T> T getBean(Class<T> clazz){
        return CONTEXT.getBean(clazz);
    }
    public static <T> List<T> getBeans(Class<T> clazz){
        Map<String, T> map = CONTEXT.getBeansOfType(clazz);
        List<T> list = new ArrayList<>(16);
        map.forEach((k,v) -> list.add(v));
        return list;
    }
}