package com.example.demo.service;

import java.util.List;

import com.example.demo.model.ClusterModel;
import com.example.demo.model.FacilityModel;

public interface FacilityService {
	List<FacilityModel> printFacility();
	List<FacilityModel> printBestFacility();
	List<FacilityModel> printBestLandmark(String cond);
}
