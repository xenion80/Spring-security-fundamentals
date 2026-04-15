package com.codingShuttle.SecurityApp.SecurityApplication.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Logger;
@Component
@Slf4j
@Order(0)
public class LoggingFilter extends OncePerRequestFilter {


    @Override

    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long Starttime=System.currentTimeMillis();
        log.info("Incoming method-> Method: {}, URI: {}",request.getMethod(),request.getRequestURI());
        filterChain.doFilter(request,response);
        long timeTaken=System.currentTimeMillis()-Starttime;
        log.info("Outgoing Response-> Status: {}, Time taken: {}",response.getStatus(),timeTaken);

    }
}
