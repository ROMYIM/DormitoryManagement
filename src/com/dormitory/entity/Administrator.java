package com.dormitory.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "admin")
public class Administrator extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Administrator() {
		// TODO Auto-generated constructor stub
	}

}
