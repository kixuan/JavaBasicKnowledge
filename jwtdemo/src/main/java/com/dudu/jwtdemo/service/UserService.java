package com.dudu.jwtdemo.service;

import com.dudu.jwtdemo.mbg.model.User;

import java.util.List;

public interface UserService {
    String login(User user);
    User getInfoById(Integer id);
}
