package com.paipaipai.service.impl;

import com.paipaipai.entity.User;
import com.paipaipai.entity.UserExample;
import com.paipaipai.mapper.UserMapper;
import com.paipaipai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by weibo on 2016/12/25.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    public List<User> getUserByCondition(String name, Integer age) throws  Exception {

        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(name).andAgeEqualTo(age);
        return  this.userMapper.selectByExample(example);
    }
}
