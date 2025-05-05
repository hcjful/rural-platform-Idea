package com.rural.platform.mapper;

import com.rural.platform.entity.User;
import com.rural.platform.page.Page;
import com.rural.platform.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
	List<User> selectAll();
	//根据用户名查找用户的方法
	User findUserByCode(@Param("userCode") String userCode);

	//查询用户总行数的方法
	public int selectUserCount(User user);

	//分页查询用户的方法
	public List<User> selectUserPage(@Param("page") Page page, @Param("user")User user);

	//添加用户的方法
	public int insertUser(User user);

	//根据用户id修改用户状态的方法
	public int updateUserState(User user);

	//根据用户id将用户状态修改为删除状态
	public int setUserDelete(Integer userId);

	//根据用户id修改用户昵称的方法
	public int updateNameById(User user);

	//根据用户id修改密码的方法
	public int updatePwdById(User user);
	int insert(User user);
    int countByUserCode(@Param("userCode") String userCode);
    int countByEmail(@Param("email") String email);
    int countByPhone(@Param("phone") String phone);

    User findById(Long id);
    
    User findByUsername(String username);
    
    List<User> findAll();
    
    List<User> findByRole(String roleCode);
    
    int update(User user);
    
    int delete(Long id);
    
    int countByUsername(String username);
    
    List<User> search(@Param("username") String username, 
                     @Param("phone") String phone, 
                     @Param("roleCode") String roleCode);

    User selectById(Long id);
    UserVO selectUserInfo(Long userId);
    int updateById(User user);
    int updateAvatar(@Param("userId") Long userId, @Param("avatar") String avatar);
    int updatePhone(@Param("userId") Long userId, @Param("phone") String phone);
    int updateEmail(@Param("userId") Long userId, @Param("email") String email);
}
