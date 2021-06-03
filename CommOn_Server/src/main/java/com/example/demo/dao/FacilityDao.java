package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.FacilityModel;

@Mapper
public interface FacilityDao {
	List<FacilityModel> getFacility();
	List<FacilityModel> getBestFacility();
	List<FacilityModel> getBestLandMark(@Param("_condition") String cond);
}
