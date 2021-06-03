package com.example.demo.service;

public interface JwtService {
	boolean isUsable(String jwt);
	<T> String create(String key,T data, String subject);
}
