package com.chenfanyf.demo2.dto;

import lombok.Data;

@Data
public class TokenVo {

    private String type = "Bearer";
    private String token;
    private String username;
    private String role;

    public TokenVo(String token){
        this.token = token;
    }

}
