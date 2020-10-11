package com.itheima.health.security_config;


import com.alibaba.fastjson.JSONObject;
import com.itheima.health.dto.ErrorInfoDTO;
import com.itheima.health.utils.jwt.JwtUtils;
import com.itheima.health.utils.jwt.UserInfo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 验证成功当然就是进行鉴权了
 * 登录成功之后走此类进行鉴权操作
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtProperties jwtProperties;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JwtProperties jwtProperties) {
        super(authenticationManager);
        this.jwtProperties = jwtProperties;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String tokenHeader = request.getHeader(JwtUtils.TOKEN_NAME);
        // 如果请求头中没有Authorization信息则直接放行了
        if (tokenHeader == null || !tokenHeader.startsWith(JwtUtils.TOKEN_PREFIX)) {
//                response.setContentType("application/json; charset=utf-8");
//                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                ServletOutputStream out = response.getOutputStream();
//                 Result result = new Result(false,"认证失败，请先登录！");
//                 String s = JSONObject.toJSONString(result);
//                out.write(s.getBytes());
//                out.flush();
                chain.doFilter(request, response);
        }else{
            // 如果请求头中有token，则进行解析，并且设置认证信息
            SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader,response));
            super.doFilterInternal(request, response, chain);
        }

    }

    // 这里从token中获取用户信息并新建一个token
    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader,HttpServletResponse response) throws IOException {
        String token = tokenHeader.replace(JwtUtils.TOKEN_PREFIX, "");
        ServletOutputStream out = response.getOutputStream();
        UserInfo userInfo = null;
        ErrorInfoDTO  errorInfoDTO =  new ErrorInfoDTO();
        try {
            userInfo = JwtUtils.getInfoFromToken(token,jwtProperties.getPublicKey());
            System.out.println("校验用户对象："+userInfo.toString());
            String username =userInfo.getUsername();
            System.out.println("校验 用户名："+username);
            if (username != null) {
                String[] roles1 = userInfo.getRoles().split("-");
                List<String> list = Arrays.asList(roles1);
                List<GrantedAuthority> listRoles = new ArrayList<GrantedAuthority>();
                for (String s : list) {
                    listRoles.add(new SimpleGrantedAuthority(s));
                }
                return new UsernamePasswordAuthenticationToken(username, null, listRoles);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            errorInfoDTO.setFlag(false);
            errorInfoDTO.setMessage("认证解析失败");
            String s = JSONObject.toJSONString(errorInfoDTO);
            out.write(s.getBytes());
            out.flush();
            out.close();
        }
        return  null;
    }
}
