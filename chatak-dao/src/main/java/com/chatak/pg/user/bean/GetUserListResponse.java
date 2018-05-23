package com.chatak.pg.user.bean;

import java.util.List;

public class GetUserListResponse  extends Response
{
   private List<UserInfo> userList;

/**
 * @return the userList
 */
public List<UserInfo> getUserList() {
	return userList;
}

/**
 * @param userList the userList to set
 */
public void setUserList(List<UserInfo> userList) {
	this.userList = userList;
}
}
