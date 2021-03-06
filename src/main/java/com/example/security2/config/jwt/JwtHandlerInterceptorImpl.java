package com.example.security2.config.jwt;

import com.example.security2.dto.ResultDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtHandlerInterceptorImpl implements HandlerInterceptor {

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        final String token = request.getHeader(Constant.AUTH_HEADER);

        try {
            if (token == null) {
                responseError(response, Constant.AUTH_INVALID);
                return false;
            }
            log.debug("token: {} ", token);
            jwtService.isUsable(token);
            return true;
        } catch (ExpiredJwtException expiredJwtException) {
            responseError(response, Constant.TOKEN_TIME_OUT);
            return false;
        } catch (Exception e) {
            responseError(response, Constant.AUTH_INVALID);
            return false;
        }

    }

    private void responseError(HttpServletResponse response, String message) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ResultDto resultDto = new ResultDto(false, message, null);
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(mapper.writeValueAsString(resultDto));
    }
}
