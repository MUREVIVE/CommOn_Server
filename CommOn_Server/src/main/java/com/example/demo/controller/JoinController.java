package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.UserModel;
import com.example.demo.service.JwtService;
import com.example.demo.service.UserService;
import com.example.demo.util.Result;

@RestController
public class JoinController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private JwtService jwtService;

	@PostMapping(value = "/join")
	public Result join2(HttpServletRequest request, Model model, HttpServletResponse response) {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		Result result = Result.successInstance();

		System.out.println("id : " + id);
		System.out.println("pw : " + pw);
		//UserModel loginUser = new UserModel(id, pw);

		result.setData("FAIL");
		if (verify(id)) { // MANAGER
			executeJoin(id,pw);
			result.setData("OK");

		}


		return result;
	}

	public boolean verify(String id) {
		List<UserModel> m_user = userService.verifyUser(id);
		if (m_user == null || m_user.size() == 0)
			return true;
		else
			return false;
	}

	public void executeJoin(String id, String pw) {
		userService.insertUser(id, pw);
	}
	
	@RequestMapping("/jointest")
	public String join3(Model model) {
		executeJoin("test6", "test6");
		System.out.println("ok!");
		return "ok";
	}
}
