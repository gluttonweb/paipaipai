package com.paipaipai.controller;

import com.paipaipai.common.RedisClient;
import com.paipaipai.mapper.UserMapper;
import com.paipaipai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by weibo on 2016/12/16.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisClient redisClient;

    @RequestMapping("/delete/{id}")
    public Object delete(@PathVariable("id") int id){
        try {
            int rs = this.userMapper.deleteByPrimaryKey(id);
            return  rs;
        } catch (Exception e) {
            return  e;
        }
    }

    @RequestMapping("/getUserNameFromRedis/{id}")
    public Object getUserNameFromRedis(@PathVariable("id") Integer id) {
        try {
            if (null == id || id <= 0) return "参数为空";
            String userName = redisClient.get(String.valueOf(id));
            return userName;
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        }
    }

}