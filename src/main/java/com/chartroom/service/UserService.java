package com.chartroom.service;

import com.chartroom.dao.UserMapper;
import com.chartroom.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by icinfo on 2017-09-06.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public boolean checkLogin(String username,String password){
        User user = userMapper.selectByUsername(username);
        if(user == null)
            return false;
        if(!password.equals(user.getPassword())){
            return false;
        }
        return true;
    }

    public User getUserByUsername(String username){
        return userMapper.selectByUsername(username);
    }
}
