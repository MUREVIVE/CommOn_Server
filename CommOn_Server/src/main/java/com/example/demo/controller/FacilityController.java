package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.calculator.Calculator;
import com.example.demo.model.ClusterModel;
import com.example.demo.model.FacilityModel;
import com.example.demo.service.FacilityService;
import com.example.demo.util.Result;

@RestController
public class FacilityController {
	@Autowired
	FacilityService facilityService;

	@Autowired
	Calculator calculator;

	/*
	 * @RequestMapping("/facility") // calculate public String public String
	 * location_recommend(Model model) { List<FacilityModel> m_facility =
	 * calculator.
	 * getPoint("ident = 'test1' or ident = 'test2' or ident = 'test3' or ident = 'test4'"
	 * );
	 * 
	 * model.addAttribute("facilities", m_facility);
	 * 
	 * return "facility"; }
	 */

	@RequestMapping("/facility_test") // calculate public String
	public String getFacilityList(Model model) {
		List<FacilityModel> m_facility = facilityService.printFacility();
		model.addAttribute("facilities", m_facility);

		return "facility";
	}

	@PostMapping(value = "/getlandmark")
	public Result getLandmark(HttpServletRequest request, Model model, HttpServletResponse response) {
		String id = request.getParameter("id"); // test1
		String[] users = request.getParameter("users").split(","); // test2,test3,

		StringBuilder sb = new StringBuilder();
		sb.append("ident = '" + id + "'");
		for (int i = 0; i < users.length; i++)
			sb.append(" or ident = '" + users[i] + "'");

		System.out.println("condition test : [" + sb.toString() + "]");
		List<FacilityModel> landmarks;
		landmarks = calculator.getPoint(sb.toString()); // parse from id + users Datas.
		System.out.println("landmarks size : " + landmarks.size());
		Result result = Result.successInstance();
		result.setData(landmarks);

		return result;
	}
}
