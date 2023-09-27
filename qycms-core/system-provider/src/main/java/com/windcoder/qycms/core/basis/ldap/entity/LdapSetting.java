package com.windcoder.qycms.core.basis.ldap.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;


@Entity
@Table(name = "sys_ldap_setting")
public class LdapSetting {
	
	private static final long serialVersionUID = -2975956389282728711L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	/**
	 * Ldap服务的名称
	 */
	private String name;
	
	
	/**
	 * Ldap的域名或ip地址
	 */
	private String host;
	
	
	/**
	 * Ldap的端口号
	 */
	private Integer port;
	
	
	/**
	 * Ldap的管理员的username
	 */
	private String username;
	
	
	/**
	 * Ldap的管理员的密码
	 */
	private String password;
	
	/**
	 * 密码salt
	 */
	private String salt;
	
	/**
	 * Ldap的数据列表所在活动目录的位置
	 * （e.g : cn = Users,dc=qimooc,dc=net）
	 */
	@Column(name = "base_dn")
	private String baseDN;
	
	
	/**
	 * 是否启用LDAPS
	 */
	@Column(name = "is_ldaps")
	@ColumnDefault("0")
	private Boolean isLDAPS;
	


	
	private String email;
	private String loginName;
	private String firstName;
	private String lastName;
	
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBaseDN() {
		return baseDN;
	}
	public void setBaseDN(String baseDN) {
		this.baseDN = baseDN;
	}
	public Boolean getIsLDAPS() {
		return isLDAPS;
	}
	public void setIsLDAPS(Boolean isLDAPS) {
		this.isLDAPS = isLDAPS;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}

	
	
	
	
}
