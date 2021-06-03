package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;
import com.example.demo.util.FcmUtil;
import com.example.demo.util.Result;

@RestController
public class FCM_Controller {

	@Autowired
	UserService userService;

	@Autowired
	FcmUtil fcmUtil;

	@RequestMapping(value = "/fcmtest.do")
	public @ResponseBody String fcmtest(HttpServletRequest request,HttpServletResponse response, Model model)
	throws Exception{
		String token="eSb4oOT7Q9-aZ24cBAmASI:APA91bHJXvNM86SiHtRcvSyKzoxZqA_oDBNTScZiuULL5sGKNxMYkCICJmHKetzQEil4-37LjNTjfWz8mQJjAk6aDSGkx3kVkfQyD8eRFNeaAIdu9Mdjy4sCkHVr3vwOZzf_mial776t";
		
		String title = "test1" + "님의 미팅 요청입니다.";
		String context = "참가자 : " + "test2,test3,test4" +"\n"
				+"위치 : " + "아주대학교 이디야점" + "\n"
				+"좌표 : " + "37.2" +"/"+"123.4";
		String id = "test4";
		
		fcmUtil.send_FCM(token,title,context,id);
		
		return "complete.";
	}
	
	
	@PostMapping(value = "/sendinvitation")
	public Result sendInvitation(HttpServletRequest request, Model model, HttpServletResponse response) {
		System.out.println("sendinvi active.");
		String id = request.getParameter("id"); // test1
		String location_name = request.getParameter("location_name");
		String location_lati = request.getParameter("location_lati");
		String location_longi = request.getParameter("location_longi");
		String[] friendsid = request.getParameter("friendsid").split(","); // test2,test3,

		String title = id + "님의 미팅 요청입니다.";
		String context = "참가자 : " + request.getParameter("friendsid") +"\n"
				+"위치 : " + location_name + "\n"
				+"좌표 : " + location_lati +"/"+location_longi;
		
		for(UserModel u : userService.printUser()) {
			for(String s : friendsid) {
				
				if(s.equals(u.getIdent())) {
					System.out.println("find!");
					fcmUtil.send_FCM(u.getToken(), title, context,s);
					break;
				}else {
					System.out.println("not find! / " +"s : " + s + "/ u : " + u.getIdent());
				}
			}
			
		}
		
		Result result = Result.successInstance();
		result.setData("OK");

		return result;
	}
}