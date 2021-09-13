package com.itmuch.usercenter.user;

import com.itmuch.usercenter.dao.user.UserMapper;
import com.itmuch.usercenter.domain.entity.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UserService {
    @Autowired
    private  UserMapper userMapper;

    public User findById(Integer id) {
        return this.userMapper.selectByPrimaryKey(id);
    }
}
