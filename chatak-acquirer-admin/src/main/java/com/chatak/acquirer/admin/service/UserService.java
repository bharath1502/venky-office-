package com.chatak.acquirer.admin.service;

import java.util.List;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.UserData;
import com.chatak.pg.bean.Response;
import com.chatak.pg.model.AccessLogsDTO;
import com.chatak.pg.model.AdminUserDTO;
import com.chatak.pg.model.GenericUserDTO;

public interface UserService {
	public void saveUser(UserData userData)throws ChatakAdminException;
	
	public List<AdminUserDTO> searchUser(AdminUserDTO adminUserDTO)throws ChatakAdminException;
	
	public UserData getUserData(Long userId)throws ChatakAdminException;
	
	public void updateUser(UserData userData)throws ChatakAdminException;
	
	public List<AccessLogsDTO> getAllPgUserActivityLogs() throws ChatakAdminException;
	
	public List<GenericUserDTO> searchGenericUser(GenericUserDTO adminUserDTO )
			throws ChatakAdminException;
	
	public UserData getUserDataOnUsersGroupType(Long userId,String usersGroupType) throws ChatakAdminException;
	
	public List<GenericUserDTO> searchAdminUser(GenericUserDTO adminUserDTO ) throws ChatakAdminException;
	
	public List<GenericUserDTO> searchMerchantUser(GenericUserDTO adminUserDTO ) throws ChatakAdminException;
	
	public UserData validateEmailId(String emailId) throws ChatakAdminException;
	
	public UserData deleteMerchantUser (Long userId,String usersGroupType) throws ChatakAdminException;
	
	public UserData validateEmailForAdminId(String emailId) throws ChatakAdminException;

	public List<Long> getAdminRoleList() throws ChatakAdminException;

	public List<Long> getMerchantRoleList() throws ChatakAdminException;
	
	public Response changeUserStatus(UserData userData, String userStatus) throws ChatakAdminException;
	
	public UserData validateUserName(String userName) throws ChatakAdminException;
	
	public List<AdminUserDTO> searchAdminUserList() throws ChatakAdminException;
	
	public List<AdminUserDTO> searchMerchantUserList() throws ChatakAdminException;
	
	public Response unblockAdminUser(String userName) throws ChatakAdminException;

	public Response unblockMerchantUser(String userName)throws ChatakAdminException;
	
	public UserData merchantIdByMerchantName(String merchantId) throws ChatakAdminException;

}
