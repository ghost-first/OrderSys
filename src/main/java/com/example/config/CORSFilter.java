package com.example.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Configuration
//解决Access-Control-Allow-Origin跨域问题
class CORSFilter extends OncePerRequestFilter {
    public void doFilterInternal(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        response.setContentType("text/html;charset=UTF-8");

        res.setContentType("text/html;charset=UTF-8");

        response.setHeader("XDomainRequestAllowed","1");//不可以放在后面

        //放行所有,类似*,这里的*完全无效
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");

        System.out.println("执行了");

        //允许请求方式
        response.setHeader("Access-Control-Allow-Methods", "POST,PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");

        //需要放行header头部字段 如需鉴权字段，自行添加，如Authorization等
        response.setHeader("Access-Control-Allow-Headers",
                "content-type,x-requested-with,Authorization," +
                        "authorization,Origin,No-Cache,X-Requested-With,If-Modified-Since," +
                        " Pragma, Last-Modified, Cache-Control,Expires, Content-Type, X-E4M-With,userId,token");

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return ;
        } else {
            chain.doFilter(request, response);
        }
    }
}
