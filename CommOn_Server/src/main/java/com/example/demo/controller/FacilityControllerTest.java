package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.calculator.Calculator;
import com.example.demo.model.FacilityModel;
import com.example.demo.service.FacilityService;

@Controller
public class FacilityControllerTest {
	@Autowired
	FacilityService facilityService;

	@Autowired
	Calculator calculator;

	@RequestMapping("/facility") // calculate public String
	public String location_recommend(Model model) {
		List<FacilityModel> m_facility = calculator.getPoint("ident = 'test1' or ident = 'test2' or ident = 'test3' or ident = 'test4'");

		model.addAttribute("facilities", m_facility);

		return "facility";
	}
}
