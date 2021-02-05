package com.chenfanyf.demo2.controller;

import com.chenfanyf.demo2.service.LoginS;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginC {

    @Resource
    private LoginS loginS;

    @PostMapping()
    public List<String> login(@RequestBody String name,String pwd){


        List<String> list=new ArrayList();
        list.add("123");
        return list;
    }
}
