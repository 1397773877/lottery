package com.newwing.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 平台表
 */
@Entity
@Table(name="t_platform")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_platform_sequence")
public class PlatformBO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;// 主键ID
	private String platform; // 平台
	private String testUrl;// 免费试玩地址
	private String loginUrl;// 免费试玩地址
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sequenceGenerator")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	public String getTestUrl() {
		return testUrl;
	}

	public void setTestUrl(String testUrl) {
		this.testUrl = testUrl;
	}
	
	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
}