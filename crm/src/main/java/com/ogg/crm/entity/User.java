package com.ogg.crm.entity;

public class User {

    private String userId;

    /**
     * 用户名
     */
    private String logonName;

    /**
     * 真实姓名
     */
    private String roleName;

    /**
     * 登录密码
     */
    private String logonPass;

    /**
     * 全称
     */
    private String fullName;


    /**
     * 角色
     */
    private String roleType;


    /**
     * 菜单权限
     */
    private String menuRight;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLogonName() {
        return logonName;
    }

    public void setLogonName(String logonName) {
        this.logonName = logonName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getLogonPass() {
        return logonPass;
    }

    public void setLogonPass(String logonPass) {
        this.logonPass = logonPass;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getMenuRight() {
        return menuRight;
    }

    public void setMenuRight(String menuRight) {
        this.menuRight = menuRight;
    }
}
