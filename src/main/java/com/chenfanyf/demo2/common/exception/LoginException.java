package com.chenfanyf.demo2.common.exception;


import com.chenfanyf.demo2.common.api.ApiCode;

/**
 * @author Jinhe
 * @version v1
 * @date 2020/11/10/010.
 * @email mjh2019@gmail.com
 * @description
 */
public class LoginException extends BaseException {
    private static final long serialVersionUID = -3157438982569715170L;

    public LoginException(String message) {
        super(message);
    }

    public LoginException(Integer errorCode, String message) {
        super(errorCode, message);
    }

    public LoginException(ApiCode apiCode) {
        super(apiCode);
    }
}
