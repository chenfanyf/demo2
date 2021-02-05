/*
 * Copyright 2019-2029 geekidea(https://github.com/geekidea)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chenfanyf.demo2.common.handler;

import com.alibaba.fastjson.JSON;
import com.chenfanyf.demo2.common.RequestDetail;
import com.chenfanyf.demo2.common.RequestDetailThreadLocal;
import com.chenfanyf.demo2.common.api.ApiCode;
import com.chenfanyf.demo2.common.api.ApiResult;
import com.chenfanyf.demo2.common.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author geekidea
 * @date 2018-11-08
 */
@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ApiResult<List<String>> handleMethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        printRequestDetail();
        BindingResult bindingResult = ex.getBindingResult();
        List<String> list = new ArrayList<>();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            list.add(fieldError.getDefaultMessage());
        }
        Collections.sort(list);
        log.error(getApiCodeString(ApiCode.PARAMETER_EXCEPTION) + ":" + JSON.toJSONString(list));
        return ApiResult.fail(ApiCode.PARAMETER_EXCEPTION, list);
    }


    @ExceptionHandler(value = LoginException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Boolean> sysLoginExceptionHandler(LoginException exception) {
        printRequestDetail();
        printApiCodeException(ApiCode.LOGIN_EXCEPTION, exception);
        return ApiResult.fail(ApiCode.LOGIN_EXCEPTION);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Boolean> exceptionHandler(AccessDeniedException exception) {
        printRequestDetail();
        printApiCodeException(ApiCode.NOT_PERMISSION, exception);
        return ApiResult.fail(ApiCode.NOT_PERMISSION);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Boolean> httpMessageNotReadableException(HttpMessageNotReadableException exception) {
        printRequestDetail();
        printApiCodeException(ApiCode.PARAMETER_EXCEPTION, exception);
        return ApiResult.fail(ApiCode.PARAMETER_EXCEPTION);
    }


    @ExceptionHandler(value = HttpMediaTypeException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Boolean> httpMediaTypeException(HttpMediaTypeException exception) {
        printRequestDetail();
        printApiCodeException(ApiCode.HTTP_MEDIA_TYPE_EXCEPTION, exception);
        return ApiResult.fail(ApiCode.HTTP_MEDIA_TYPE_EXCEPTION);
    }


    @ExceptionHandler(value = {BaseException.class})
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Boolean> aiurpayExceptionHandler(BaseException exception) {
        printRequestDetail();
        log.error("Exception: {}", exception.getLocalizedMessage());
        int errorCode;
        if (exception instanceof BusinessException) {
            errorCode = ApiCode.BUSINESS_EXCEPTION.getCode();
        } else if (exception instanceof DaoException) {
            errorCode = ApiCode.DAO_EXCEPTION.getCode();
        } else if (exception instanceof VerificationCodeException) {
            errorCode = ApiCode.VERIFICATION_CODE_EXCEPTION.getCode();
        } else {
            errorCode = ApiCode.SPRING_BOOT_PLUS_EXCEPTION.getCode();
        }
        return new ApiResult<Boolean>()
                .setCode(errorCode)
                .setMessage(exception.getMessage());
    }


    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Boolean> authenticationExceptionHandler(AuthenticationException exception) {
        printRequestDetail();
        printApiCodeException(ApiCode.AUTHENTICATION_EXCEPTION, exception);
        return ApiResult.fail(ApiCode.AUTHENTICATION_EXCEPTION);
    }


    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<String> httpRequestMethodNotSupportedExceptionHandler(Exception exception) {
        printRequestDetail();
        printApiCodeException(ApiCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION, exception);
        return ApiResult.fail(ApiCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION.getCode(), exception.getMessage());
    }


    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Boolean> exceptionHandler(Exception exception) {
        printRequestDetail();
        printApiCodeException(ApiCode.SYSTEM_EXCEPTION, exception);
        return ApiResult.fail(ApiCode.SYSTEM_EXCEPTION);
    }


    private void printRequestDetail() {
        RequestDetail requestDetail = RequestDetailThreadLocal.getRequestDetail();
        if (requestDetail != null) {
            log.error("异常来源：ip: {}, path: {}", requestDetail.getIp(), requestDetail.getPath());
        }
    }


    private String getApiCodeString(ApiCode apiCode) {
        if (apiCode != null) {
            return String.format("errorCode: %s, errorMessage: %s", apiCode.getCode(), apiCode.getMessage());
        }
        return null;
    }


    private void printApiCodeException(ApiCode apiCode, Exception exception) {
        log.error(getApiCodeString(apiCode), exception);
    }

}
