package org.henhaoqi.BookManager.service;

import org.henhaoqi.BookManager.entity.User;
import org.henhaoqi.BookManager.utils.ConcurrentUtils;
import org.springframework.stereotype.Service;

@Service
public class HostHolder {

    public User getUser(){
        return ConcurrentUtils.getHost();
    }

    public void setUser(User user){
        ConcurrentUtils.setHost(user);
    }
}
