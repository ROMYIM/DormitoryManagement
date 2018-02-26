package com.dormitory.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.dormitory.constant.Authentication;
import com.dormitory.constant.Gender;

@MappedSuperclass
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String password;
	private Gender gender;
	private Authentication authentication;
	
	@Id
	@Column(name = "id", length = 15, unique = true, nullable = false, updatable = false)
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "name", length = 6, nullable = false)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "password", length = 50, nullable = false)
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name = "gender", nullable = false, updatable = false)
	public Gender getGender() {
		return gender;
	}
	
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "auth", nullable = false, updatable = false)
	public Authentication getAuthentication() {
		return authentication;
	}
	
	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}
	
	public User() {
		// TODO Auto-generated constructor stub
	}

}
