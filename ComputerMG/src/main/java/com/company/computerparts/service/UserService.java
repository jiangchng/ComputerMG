package com.company.computerparts.service;

import com.company.computerparts.dao.UserDao;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao){
        this.userDao = userDao;
    }


    public boolean authenticate(String username, String password) {
        return userDao.authenticate(username, password);
    }
}
