package com.rural.platform.service;

import com.rural.platform.entity.User;
import com.rural.platform.page.Page;
import com.rural.platform.response.Response; // 修改为正确的Response包路径
import com.rural.platform.vo.UserInfoVo;
import com.rural.platform.dto.RegisterRequest;

import java.util.List;

public interface UserService {

	List<User> findAll();
	Response<String> login(UserInfoVo userInfoVo); // 修改为正确的Response类型
	User register(RegisterRequest request);
    boolean checkUserExists(String userCode);
    boolean checkEmailExists(String email);
    boolean checkPhoneExists(String phone);
//	//根据用户名查找用户的业务方法
//	public User findUserByCode(String userCode);
//
//	//分页查询用户的业务方法
//	public Page queryUserPage(Page page, User user);
//
//	//添加用户的业务方法
//	public Result saveUser(User user);
//
//	//修改用户状态的业务方法
//	public Result updateUserState(User user);
//
//	//根据用户id删除用户的业务方法
//	public int deleteUserById(Integer userId);
//
//	//修改用户昵称的业务方法
//	public Result updateUserName(User user);
//
//	//重置密码的业务方法
//	public Result resetPwd(Integer userId);
}
