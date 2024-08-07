package com.dudu.jwtdemo.service.impl;


import com.dudu.jwtdemo.mbg.mapper.UserMapper;
import com.dudu.jwtdemo.mbg.model.User;
import com.dudu.jwtdemo.mbg.model.UserExample;
import com.dudu.jwtdemo.service.UserService;
import com.dudu.jwtdemo.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public String login(User user) {
        String token = null;
        // 查询条件
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(user.getName())
                .andPasswordEqualTo(user.getPassword());
        List<User> users = userMapper.selectByExample(example);
        if (users != null && !users.isEmpty()) {
            // 表示用户登入成功,生成token
            token = jwtTokenUtil.generateToken(user);
            return token;
        }
        return token;
    }

    @Override
    public User getInfoById(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        // 获取oldToken并刷新
        String token = jwtTokenUtil.generateToken(user);
        jwtTokenUtil.refreshHeadToken(token);
        return user;
    }
}
