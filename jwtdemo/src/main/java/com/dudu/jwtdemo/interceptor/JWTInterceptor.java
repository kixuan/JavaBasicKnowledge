package com.dudu.jwtdemo.interceptor;

import com.dudu.jwtdemo.mbg.model.User;
import com.dudu.jwtdemo.utils.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(JWTInterceptor.class);
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader(this.tokenHeader);// 从请求头获取token tokenHeader: Authorization
        if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
            String authToken = authHeader.substring(this.tokenHead.length());// The part after "Bearer "
            String username = jwtTokenUtil.getUserNameFromToken(authToken);// 获取当前登入的用户信息
            LOGGER.info("checking username:{}", username);
            if (username != null) {
                // 当前有用户登入
                User user = new User();
                user.setName(username);
                // 验证token
                if (jwtTokenUtil.validateToken(authToken, user)) {
                    // 验证Token成功
                    return true;// 放行
                }

            }
        }
        // 没有用户登录，返回消息进行提醒
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "用户未登入");
        map.put("state", false);
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
