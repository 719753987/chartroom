package com.chartroom.util;

import java.util.UUID;

/**
 * Created by icinfo on 2017-09-15.
 */
public class StringUtil {

    public static final String USERNAME_KEY = "userKey";

    public static String getUuid(){
        return UUID.randomUUID().toString().replace("-","").toLowerCase();
    }
}
