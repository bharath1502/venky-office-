package com.chatak.pg.acq.dao;

import com.chatak.pg.acq.dao.model.PGUserProfile;
import com.chatak.pg.user.bean.AuthenticateUserResponse;
import com.chatak.pg.user.bean.DeleteUserRequest;
import com.chatak.pg.user.bean.DeleteUserResponse;
import com.chatak.pg.user.bean.LoginUserRequest;
import com.chatak.pg.user.bean.UpdateUserRequest;
import com.chatak.pg.user.bean.UpdateUserResponse;

public interface UserDao {
	
	public PGUserProfile createUser(PGUserProfile pgUserProfile) ;
	
	public void activateUser(PGUserProfile pgUserProfile) ;
	
	public PGUserProfile loginUser(LoginUserRequest request);
	
	public AuthenticateUserResponse authenticateUser(String token, String clientIp);
	
	public boolean createAuthUser(Long profileId, String clientIp, String token);
	
	public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest);
	
	public DeleteUserResponse deleteUser(DeleteUserRequest deleteUserRequest);
	
}
