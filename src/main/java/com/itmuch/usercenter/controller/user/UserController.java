package com.itmuch.usercenter.controller.user;


import com.itmuch.usercenter.domain.entity.user.User;

import com.itmuch.usercenter.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private  UserService userService;

    @GetMapping("/{id}")
    public User findById(@PathVariable Integer id) {
        log.info("我被请求了...");
        return this.userService.findById(id);
    }

}
