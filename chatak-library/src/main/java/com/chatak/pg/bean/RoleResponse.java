package com.chatak.pg.bean;

public class RoleResponse extends Response {

  private static final long serialVersionUID = 1L;

  private Long roleId;

  private String roleName;

  /**
   * @return the roleId
   */
  public Long getRoleId() {
    return roleId;
  }

  /**
   * @param roleId
   *          the roleId to set
   */
  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }

  /**
   * @return the roleName
   */
  public String getRoleName() {
    return roleName;
  }

  /**
   * @param roleName
   *          the roleName to set
   */
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

}
