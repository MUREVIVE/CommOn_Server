package com.example.demo.service;

import java.util.List;

import com.example.demo.model.TUserModel;
import com.example.demo.model.UserModel;

public interface UserService {
	List<UserModel> printUser();
	List<UserModel> verifyUser(String id, String pw);
	List<UserModel> verifyUser(String id);
	void insertUser(String id, String pw);
	List<TUserModel> getFriends(String id);
	void updateMember(String id, String target_id, int option1, int option2);
	void deleteMember(String id, String target_id);
	List<UserModel> getFriends_set(String id);
	void setLocation(String id,double latitude, double longitude);
	void setOption(String id,String target_id,int option);
	List<UserModel> printUserByCond(String cond);
	void setFtoken(String id,String token);
	List<TUserModel> getUserRelation(String id1,String id2);
}