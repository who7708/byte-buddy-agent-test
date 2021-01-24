package com.example.agent.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Chris
 * @version 1.0.0
 * @date 2021/01/24
 */
public class GsonUtils {
    private static final Gson gson = new GsonBuilder()
            .create();
    ;

    public static Gson getGson() {
        // if (gson == null) {
        //     synchronized (GsonUtils.class) {
        //         if (gson == null) {
        //             gson = new GsonBuilder()
        //                     .create();
        //         }
        //     }
        // }
        return gson;
    }

    public static String toJson(Object obj) {
        return getGson().toJson(obj);
    }
}
