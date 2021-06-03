package com.example.demo.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.service.ExceptionService;
import com.example.demo.service.JwtService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor{
	private static final String HEADER_AUTH = "Authorization";

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ExceptionService es;
	
	@Autowired
	private JwtService jwtService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		final String token = request.getHeader(HEADER_AUTH);
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		logger.info("Interceptor call [com.example.demo.filter]->[preHandle method]");
		logger.info("Ident : [" + id + "] pw : ["+ pw +"] Token : [" + token +"]");
		
		if(token != null && jwtService.isUsable(token)){
			return true;
		}else{
			es.UnauthorizedException(this.getClass());
			return false;
		}
	}
	
}