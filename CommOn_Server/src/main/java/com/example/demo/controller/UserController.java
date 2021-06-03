package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.TUserModel;
import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;
import com.example.demo.util.Result;
import com.google.gson.JsonObject;

@RestController
public class UserController {
	@Autowired
	UserService userService;

	@RequestMapping("/usertest")
	public Result UserTest(Model model) {

		Result result = Result.successInstance();
		result.setData(userService.getFriends_set("test1"));

		return result;
	}

	@PostMapping(value = "/getfriendlist")
	public Result getFriendList(HttpServletRequest request, Model model, HttpServletResponse response) {
		String id = request.getParameter("id");

		Result result = Result.successInstance();

		List<TUserModel> friends = userService.getFriends(id);
		result.setData(friends);

		return result;
	}

	@PostMapping(value = "/getfriendset")
	public Result getFriendSet(HttpServletRequest request, Model model, HttpServletResponse response) {
		String id = request.getParameter("id");

		Result result = Result.successInstance();

		List<UserModel> friends = userService.getFriends_set(id);
		result.setData(friends);

		return result;
	}

	@PostMapping(value = "/updateMember")
	public Result updateMember(HttpServletRequest request, Model model, HttpServletResponse response) {
		boolean isExist = false;
		
		String id = request.getParameter("id");
		String target_id = request.getParameter("target_id");
		String option1 = request.getParameter("option1");
		String option2 = request.getParameter("option2");

		Result result = Result.successInstance();
		result.setData("OK");
		
		for (UserModel u : userService.printUser()) {
			if (u.getIdent().equals(target_id)) {
				isExist = true;
			}
		}
		
		if (!isExist) 
			return result.setData("NOTEXIST");
		
		for(TUserModel tum : userService.getUserRelation(id, target_id)) 
			return result.setData("DUPLICATE");
		
		
		if (id.compareTo(target_id) > 0) {
			userService.updateMember(target_id, id, Integer.parseInt(option2), Integer.parseInt(option1));
		} else if (id.compareTo(target_id) < 0) {
			userService.updateMember(id, target_id, Integer.parseInt(option1), Integer.parseInt(option2));
		} else {
			result.setData("ISYOU"); // equals.
		}

		return result;
	}

	@PostMapping(value = "/deleteMember")
	public Result deleteMember(HttpServletRequest request, Model model, HttpServletResponse response) {
		String id = request.getParameter("id");
		String target_id = request.getParameter("target_id");

		// System.out.println("recieve : id[" + id + "]");

		Result result = Result.successInstance();
		result.setData("OK");

		if (id.compareTo(target_id) > 0) {
			userService.deleteMember(target_id, id);
		} else if (id.compareTo(target_id) < 0) {
			userService.deleteMember(id, target_id);
		} else {
			result.setData("FAIL"); // equals.
		}

		return result;
	}

	@PostMapping(value = "/setmylocation")
	public Result setMyLocation(HttpServletRequest request, Model model, HttpServletResponse response) {
		String id = request.getParameter("id");
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");

		Result result = Result.successInstance();

		System.out.println("recieve : id[" + id + "], lati[" + latitude + "] longi[" + longitude + "]");
		userService.setLocation(id, Double.parseDouble(latitude), Double.parseDouble(longitude));
		List<UserModel> friends = userService.getFriends_set(id);
		result.setData(friends);

		return result;
	}

	@PostMapping(value = "/setoption")
	public Result setOption(HttpServletRequest request, Model model, HttpServletResponse response) {
		String id = request.getParameter("id");
		String target_id = request.getParameter("target_id");
		String option = request.getParameter("option");

		Result result = Result.successInstance();
		System.out.println("111111");
		userService.setOption(id, target_id, Integer.parseInt(option));
		result.setData("OK");

		return result;
	}

	@PostMapping(value = "/setmyfriend")
	public Result setMyFriend(HttpServletRequest request, Model model, HttpServletResponse response) {
		String id = request.getParameter("id");
		String target_id = request.getParameter("target_id");

		Result result = Result.successInstance();

		// userService.isCorrectOperation()
		// messageQueueService.insertToMessageQueue()
		// Let (Looper) {
		// to push messageQueue records
		// for pushing events.}

		result.setData("OK");

		return result;
	}

	@RequestMapping("/userlist") // userlist 반환.
	public String list(Model model) {
		List<UserModel> m_user = userService.printUser();
		model.addAttribute("userList", m_user);

		return "userlist";
	}

	@ResponseBody
	@RequestMapping("/jsontest")
	public String test1() {

		JsonObject obj = new JsonObject();

		obj.addProperty("title", "테스트3");
		obj.addProperty("content", "테스트3 내용");

		JsonObject data = new JsonObject();

		data.addProperty("time", "12:00");

		obj.add("data", data);

		return obj.toString();

	}
}
