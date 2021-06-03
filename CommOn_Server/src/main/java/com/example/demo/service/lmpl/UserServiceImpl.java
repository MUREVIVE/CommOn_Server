package com.example.demo.service.lmpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserDao;
import com.example.demo.model.TUserModel;
import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao dao;
	
	@Override
	public List<UserModel> printUserByCond(String cond){
		List<UserModel> user = dao.getUserByCond(cond);
		return user;
	}
	
	
	@Override
	public List<UserModel> printUser(){
		List<UserModel> user = dao.getUser();
		return user;
	}

	@Override
	public List<UserModel> verifyUser(String id, String pw) {
		List<UserModel> user = dao.targetUser(id, pw);
		return user;
	}
	
	@Override
	public List<UserModel> verifyUser(String id) {
		List<UserModel> user = dao.targetUserbyID(id);
		return user;
	}
	
	@Override
	public void insertUser(String id, String pw) {
		dao.joinUser(id, pw);
		return;
	}
	
	@Override
	public void updateMember(String id, String target_id, int option, int option2) {
		dao.updateMember(id, target_id, option, option2);
		return;
	}
	
	@Override
	public void deleteMember(String id, String target_id) {
		dao.deleteMember(id, target_id);
		return;
	}
	
	@Override
	public List<TUserModel> getFriends(String id){
		List<TUserModel> user = dao.getFriendUser(id);
		return user;
	}
	
	@Override
	public List<UserModel> getFriends_set(String id){
		List<UserModel> user = dao.getFriendSetUser(id);
		return user;
	}
	
	@Override
	public void setLocation(String id,double latitude, double longitude) {
		dao.setLocation(id,latitude,longitude);
		return;
	}
	
	@Override
	public void setOption(String id,String target_id,int option) {
		dao.setOption1(id,target_id,option);
		dao.setOption2(id,target_id,option);
		return;
	}
	
	@Override
	public void setFtoken(String id, String token) {
		dao.setFtoken(id,token);
		return;
	}
	
	@Override
	public List<TUserModel> getUserRelation(String id1,String id2){
		return dao.printRelation(id1,id2);
	}
}
