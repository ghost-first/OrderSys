//package com.example.config;
//
//import org.springframework.core.annotation.Order;
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Order(value = 0) // 不能使用AspectJ,这里存在耦合的问题，因为不知道使用的权限的名称，并且权限字符串存在变化，所以要取消变化
//@WebFilter(filterName = "myCorsFilter", urlPatterns = "/*")
//public class CORSFilter implements Filter {
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
//            throws IOException, ServletException {
//
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "*");
//        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Content-Length, Authorization, Accept,X-Requested-With,token");
//        response.setHeader("Access-Control-Allow-Credentials","true");
//        chain.doFilter(servletRequest, servletResponse);
//    }
//
//}
//
