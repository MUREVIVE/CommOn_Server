package com.example.demo.service.lmpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.service.ExceptionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExceptionServiceImpl implements ExceptionService{
	
	public Logger logger; 
	
	@Override
	public void UnauthorizedException(Class<?> c) {
		setLogger(c);
		logger.warn(UnauthorizedException_error_msg);
	}
	
	public void setLogger(Class<?> c) {
		this.logger = LoggerFactory.getLogger(c);
	}
	
}
