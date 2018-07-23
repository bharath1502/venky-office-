package com.chatak.pg.user.bean;


public class UpdateTerminalResponse extends Response{
	
	

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private boolean isUpdated;
	
	private Long terminal_id;

	/**
	 * @return the terminal_id
	 */
	public Long getTerminal_id() {
		return terminal_id;
	}

	/**
	 * @param terminal_id the terminal_id to set
	 */
	public void setTerminal_id(Long terminal_id) {
		this.terminal_id = terminal_id;
	}

	/**
	 * @return the isUpdated
	 */
	public boolean isUpdated() {
		return isUpdated;
	}

	/**
	 * @param isUpdated the isUpdated to set
	 */
	public void setUpdated(boolean isUpdated) {
		this.isUpdated = isUpdated;
	}
	
}
