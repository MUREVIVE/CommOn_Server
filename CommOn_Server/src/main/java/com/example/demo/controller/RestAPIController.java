package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.UserModel;
import com.example.demo.util.Result;

@RestController
public class RestAPIController {

	@RequestMapping("/rest")
	public String restTest() { // @RequestParam String str
		return "REST TEST!!";
	}
	
	@PostMapping(value = "/locate")
	public String locate(HttpServletRequest request, Model model, HttpServletResponse response) {
		String user_x = request.getParameter("user_x");
		String user_y = request.getParameter("user_y");
		String start_x = request.getParameter("start_x");
		String start_y = request.getParameter("start_y");
		String end_x = request.getParameter("end_x");
		String end_y = request.getParameter("end_y");
		
		
		System.out.println("user\tx: " + user_x+"\ty: "+user_y);
		
		System.out.println("start\tx: " + start_x+"\ty: "+start_y);
		
		System.out.println("end\tx: " + end_x+"\ty: "+end_y);
		
		
		return "Server[mobile] Send : OK";
	}

	@RequestMapping("/rest2")
	public String restTest(@RequestParam String str) { // @RequestParam String str
		return str + "REST TEST!!";
	}
	
}
