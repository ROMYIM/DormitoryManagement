package com.dormitory.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "building")
public class Building implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotNull
	@Range(min = 1, max = 99)
	@JsonManagedReference
	private Integer buildingNum;
	
	@JsonBackReference
	private List<Notice> notices;
	
	@JsonBackReference
	private List<DorAdmin> dorAdmins;
	
	@JsonBackReference
	private List<Dormitory> dormitories;

	public Building() {
		// TODO Auto-generated constructor stub
		this.notices = new ArrayList<>();
		this.dorAdmins = new ArrayList<>();
		this.dormitories = new ArrayList<>();
	}
	
	public Building(Integer buildingNum) {
		setBuildingNum(buildingNum);
	}
	
	public void setBuildingNum(Integer buildingNum) {
		this.buildingNum = buildingNum;
	}
	
	@Id
	@Column(name = "building_num", updatable = false)
	public Integer getBuildingNum() {
		return buildingNum;
	}
	
	public void setNotices(List<Notice> notices) {
		this.notices = notices;
	}
	
	@ManyToMany(mappedBy = "buildings")
	public List<Notice> getNotices() {
		return notices;
	}
	
	public void setDorAdmins(List<DorAdmin> dorAdmins) {
		this.dorAdmins = dorAdmins;
	}
	
	@OneToMany(mappedBy = "manageBuilding")
	public List<DorAdmin> getDorAdmins() {
		return dorAdmins;
	}
	
	public void setDormitories(List<Dormitory> dormitories) {
		this.dormitories = dormitories;
	}
	
	@OneToMany(mappedBy = "building")
	public List<Dormitory> getDormitories() {
		return dormitories;
	}

}
