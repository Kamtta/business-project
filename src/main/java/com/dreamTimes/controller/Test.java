package com.dreamTimes.controller;

import com.dreamTimes.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/portal/user/")
public class Test {

    @RequestMapping(value = "login.do")
    public User show(){
        User user = new User();
        user.setUsername("chenjingrong");
        user.setPassword("123456788");
        user.setPhone("15882222546");
        return user;
    }
}
