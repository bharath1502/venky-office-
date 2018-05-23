package com.chatak.pg.user.bean;


public class UpdateMerchantResponse extends Response{
	private boolean isUpdated;
	private String password;
	private boolean isDeclined;

	public boolean isUpdated() {
		return isUpdated;
	}

	public void setUpdated(boolean isUpdated) {
		this.isUpdated = isUpdated;
	}

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

	public boolean isDeclined() {
		return isDeclined;
	}

	public void setDeclined(boolean isDeclined) {
		this.isDeclined = isDeclined;
	}
	
}
