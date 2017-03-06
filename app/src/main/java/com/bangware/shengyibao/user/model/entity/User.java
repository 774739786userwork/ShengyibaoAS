package com.bangware.shengyibao.user.model.entity;

/**
 * 用户信息模型
 * @author luming.tang
 *
 */
public class User implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2140348541648884704L;
	public static final String SHARED_NAME = "user";
	
	
	private String login_token;//登陆令牌
	private String user_id;//用户ID
	private String user_name;//账号
	private String password;//密码
	private String user_realname;//真实姓名
	private String org_name;//所属机构
	private String org_id;//所属机构ID
	private String roles;//角色
	private String employee_id; //业务员id
	private String mobile_number; //业务员手机号码
	
	public String getLogin_token() {
		return login_token;
	}

	public void setLogin_token(String login_token) {
		this.login_token = login_token;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUser_realname() {
		return user_realname;
	}
	public void setUser_realname(String user_realname) {
		this.user_realname = user_realname;
	}
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	public String getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public String getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}
}
