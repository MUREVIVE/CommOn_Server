package com.example.demo.service;


public interface ExceptionService {
	public static final long UnauthorizedException_serialVersionUID = -2238030302650813813L;
	public static final String UnauthorizedException_error_msg = "An user fail to authorizate.";

	public void UnauthorizedException(Class<?> c);
	public void setLogger(Class<?> c);
}
