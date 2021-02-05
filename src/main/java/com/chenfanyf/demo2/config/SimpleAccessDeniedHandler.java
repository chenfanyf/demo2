package com.chenfanyf.demo2.config;

import com.chenfanyf.demo2.common.api.ApiCode;
import com.chenfanyf.demo2.common.api.ApiResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Jinhe in 2020. [e-mail: 943348115@qq.com]
 * @version  v1
 */
@Slf4j
@Component
public class SimpleAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        log.info("visitor comes without permission. [{}]", accessDeniedException.getMessage());

        ApiResult<Boolean> result = ApiResult.fail(ApiCode.NOT_PERMISSION);
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(result));
    }
}
