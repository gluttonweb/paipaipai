package com.paipaipai.service.impl;

import com.paipaipai.entity.User;
import com.paipaipai.mapper.UserMapper;
import com.paipaipai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by weibo on 2016/12/25.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User getById(Integer id) {
        if (null == id) return null;

        User user = this.userMapper.getUserById(id);
        return user;
    }
}
