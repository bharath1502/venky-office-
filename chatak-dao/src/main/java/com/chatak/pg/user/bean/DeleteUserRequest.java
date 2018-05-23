package com.chatak.pg.user.bean;

public class DeleteUserRequest
{
	private Long profile_id;
	public String validate()
	{
		String message = "";
		if(profile_id == null)
		{
			message="Profile Id is the Required field";
		} 
		return message;
	}
	
	/**
	 * @return the profile_id
	 */
	public Long getProfile_id() {
		return profile_id;
	}
	/**
	 * @param profile_id the profile_id to set
	 */
	public void setProfile_id(Long profile_id) {
		this.profile_id = profile_id;
	}
	
 
}
