package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.TUserModel;
import com.example.demo.model.UserModel;

@Mapper
public interface UserDao {
	List<UserModel> getUser();

	List<UserModel> targetUser(@Param("_id") String id, @Param("_pw") String pw);
	List<UserModel> targetUserbyID(@Param("_id") String id);

	void joinUser(@Param("_id") String id, @Param("_pw") String pw);
	List<TUserModel> getFriendUser(@Param("_id") String id);
	void updateMember(@Param("_id") String id, @Param("_target_id") String target_id, @Param("_option1") int option1, @Param("_option2") int option2);
	void deleteMember(@Param("_id") String id, @Param("_target_id") String target_id);
	List<UserModel> getFriendSetUser(@Param("_id") String id);
	void setLocation(@Param("_id") String id, @Param("_latitude") double latitude, @Param("_longitude") double longitude);
	void setOption1(@Param("_id") String id, @Param("_target_id") String target_id, @Param("_option") int option);
	void setOption2(@Param("_id") String id, @Param("_target_id") String target_id, @Param("_option") int option);
	List<UserModel> getUserByCond(@Param("_condition") String cond);
	void setFtoken(@Param("_id") String id, @Param("_token") String token);
	List<TUserModel> printRelation(@Param("_id1") String id1, @Param("_id2") String id2);
}
