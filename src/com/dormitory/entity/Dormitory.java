package com.dormitory.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "dormitory")
public class Dormitory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotNull
	@Length(min = 5, max = 5)
	private String dormitoryId;
	
	@NotNull
	@Range(min = 0, max = 99)
	private Integer id;
	
	@NotNull
	@Range(min = 1, max = 99)
	private Integer floor;
	
	@NotNull
	@Valid
	private Building building;
	
	@Digits(integer = 3, fraction = 2)
	@NumberFormat(style = Style.CURRENCY)
	private Float ebills;
	
	@Digits(integer = 3, fraction = 2)
	@NumberFormat(style = Style.CURRENCY)
	private Float wbills;
	
	@JsonBackReference
	private List<Student> students = new ArrayList<>();
	
	@JsonBackReference
	private List<RepairInformation> repairInformations = new ArrayList<>();
	
	@JsonBackReference
	private List<Bill> bills = new ArrayList<>();

	public Dormitory() {
		// TODO Auto-generated constructor stub
		 
	}
	
	public void setDormitoryId(String dormitoryId) {
		this.dormitoryId = dormitoryId;
	}
	
	public void setDormitoryId(Integer id, Integer floor, Integer builiding) {
		StringBuffer dormitoryIdBuffer = new StringBuffer(5);
		dormitoryIdBuffer.append(builiding).append(floor).append(id);
		dormitoryId = dormitoryIdBuffer.toString();
	}
	
	@Id
	@Column(name = "dormitory_id", unique = true, nullable = false, length = 5)
	public String getDormitoryId() {
		return dormitoryId;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "id", nullable = false, updatable = false)
	public Integer getId() {
		return id;
	}
	
	public void setFloor(Integer floor) {
		this.floor = floor;
	}
	
	@Column(name = "floor", nullable = false, updatable = false)
	public Integer getFloor() {
		return floor;
	}
	
	public void setBuilding(Building building) {
		this.building = building;
	}
	

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "building", updatable = false, nullable = false)
	public Building getBuilding() {
		return building;
	}
	
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
	@OneToMany(mappedBy = "dormitory")
	public List<Student> getStudents() {
		return students;
	}
	
	public void setEbills(Float ebills) {
		this.ebills = ebills;
	}
	
	@Column(name = "ebills", columnDefinition = "float default 0.00")
	@Basic(fetch = FetchType.LAZY)
	public Float getEbills() {
		return ebills;
	}
	
	public void setWbills(Float wbills) {
		this.wbills = wbills;
	}
	
	@Column(name = "wbills", columnDefinition = "float default 0.00")
	@Basic(fetch = FetchType.LAZY)
	public Float getWbills() {
		return wbills;
	}
	
	public void setRepairInformations(List<RepairInformation> repairInformations) {
		this.repairInformations = repairInformations;
	}
	
	@OneToMany(mappedBy = "dormitory")
	public List<RepairInformation> getRepairInformations() {
		return repairInformations;
	}
	
	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}
	
	@OneToMany(mappedBy = "dormitory")
	public List<Bill> getBills() {
		return bills;
	}

}
