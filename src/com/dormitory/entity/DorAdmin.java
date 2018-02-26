package com.dormitory.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "dormitory_adminstrator")
public class DorAdmin extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Building manageBuilding;
	private Date manageDate;

	public DorAdmin() {
		// TODO Auto-generated constructor stub
	}

	public void setManageDate(Date manageDate) {
		this.manageDate = manageDate;
	}
	
	@Column(name = "mange_date", columnDefinition = "date")
	@Temporal(TemporalType.DATE)
	public Date getManageDate() {
		return manageDate;
	}
	
	public void setManageBuilding(Building mangeBuilding) {
		this.manageBuilding = mangeBuilding;
	}
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
	@JoinColumn(name = "manage_building", unique = true)
	public Building getManageBuilding() {
		return manageBuilding;
	}
	
}
