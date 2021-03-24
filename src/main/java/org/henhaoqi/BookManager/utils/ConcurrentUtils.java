package org.henhaoqi.BookManager.utils;

import org.henhaoqi.BookManager.entity.User;

public class ConcurrentUtils {
    private static ThreadLocal<User> host = new ThreadLocal<>();

    public static User getHost(){
        return host.get();
    }

    public static void setHost(User user){
        host.set(user);
    }
}
