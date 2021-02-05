package com.chenfanyf.demo2.service;


import com.chenfanyf.demo2.common.api.ApiCode;
import com.chenfanyf.demo2.common.exception.BaseException;
import com.chenfanyf.demo2.config.security.jwt.JwtTokenProvider;
import com.chenfanyf.demo2.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class LoginS {

    @Resource
    private UserRepo userRepo;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private JwtTokenProvider jwtTokenProvider;




    private String generateJwtToken(String username,String password){
        if(!userRepo.existsByUsername(username)){
            throw new BaseException(ApiCode.NOT_FOUND);
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                username,password
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

}
