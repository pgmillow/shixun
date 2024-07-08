package com.penguin.interceptors;

import com.penguin.pojo.Result;
import com.penguin.utils.JwtUtil;
import com.penguin.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {

        //lingpaiyanzheng
        String token = request.getHeader("Authorization");
        try{
            Map<String,Object> claims = JwtUtil.parseToken(token);

            ThreadLocalUtil.set(claims);

            return true;
        }catch (Exception e){
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
