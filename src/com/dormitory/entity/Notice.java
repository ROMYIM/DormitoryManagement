package com.dormitory.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "notice")
public class Notice extends Information {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Building> buildings = new ArrayList<>();

	public Notice() {
		// TODO Auto-generated constructor stub
		
	}
	
	public Notice(Integer id, Date sendDate, String content) {
		super(id, sendDate, content);
	}
	
	public void setBuildings(List<Building> buildings) {
		this.buildings = buildings;
	}
	
	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
	@JoinTable(name = "not_bui", joinColumns = @JoinColumn(name = "notice_id"), inverseJoinColumns = @JoinColumn(name = "building_id"))
	public List<Building> getBuildings() {
		return buildings;
	}

}
