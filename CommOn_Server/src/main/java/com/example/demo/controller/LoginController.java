package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.UserModel;
import com.example.demo.service.JwtService;
import com.example.demo.service.UserService;
import com.example.demo.util.Result;

@RestController
public class LoginController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtService jwtService;

	@PostMapping(value = "/login")
	public Result login2(HttpServletRequest request, Model model, HttpServletResponse response) {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String ftoken = request.getParameter("token");
		Result result = Result.successInstance();
		
		System.out.println("id : " + id);
		System.out.println("pw : " + pw);
		System.out.println("token : " + ftoken);
		
		userService.setFtoken(id,ftoken);
		
		UserModel loginUser = new UserModel(id, pw);
		String Result_suc = "FAIL";
		String token = null;
		if (verify(id, pw)) {	//MANAGER
			token = jwtService.create(loginUser.getIdent() + ":key",loginUser, loginUser.getIdent() + ":subject");
			Result_suc = "SUCCESS";	//when connecting and id, pw verifying Result_suc
		}
		if(Result_suc.equals("SUCCESS")) {
			response.setHeader("Authorization", Result_suc + " " + token);
		}
		else if(Result_suc.equals("FAIL")){
			response.setHeader("Authorization", Result_suc);
		}
		result.setData(loginUser);


		return result;
	}

	public boolean verify(String id, String pw) {
		List<UserModel> m_user = userService.verifyUser(id,pw);
		if (m_user.size() > 0)
			return true;
		else
			return false;
	}
}
