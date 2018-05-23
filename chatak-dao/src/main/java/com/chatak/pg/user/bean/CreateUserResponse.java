/**
 * 
 */
package com.chatak.pg.user.bean;


public class CreateUserResponse extends Response {

	private static final long serialVersionUID = 1533691811684673764L;
	
	private Long profile_id;

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
