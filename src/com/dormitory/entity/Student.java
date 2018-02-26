package com.dormitory.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "student")
public class Student extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String major;
	private Integer classNum;
	private Integer grade;
	private Date checkInDate;
	private Date moveDate;
	private Dormitory dormitory;
	private List<ViolationRecord> violationRecords;
	
	public Student() {
		// TODO Auto-generated constructor stub
		this.violationRecords = new ArrayList<>();
	}
		
	public void setMajor(String major) {
		this.major = major;
	}
	
	@Column(name = "major", length = 10, nullable = false)
	public String getMajor() {
		return major;
	}
	
	public void setClassNum(Integer classNum) {
		this.classNum = classNum;
	}
	
	@Column(name = "class_num", nullable = false)
	public Integer getClassNum() {
		return classNum;
	}
	
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	
	@Column(name = "grade", nullable = false, updatable = false)
	public Integer getGrade() {
		return grade;
	}
	
	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}
	
	@Column(name = "in_date", columnDefinition = "date")
	@Temporal(TemporalType.DATE)
	public Date getCheckInDate() {
		return checkInDate;
	}
	
	public void setMoveDate(Date moveDate) {
		this.moveDate = moveDate;
	}
	
	@Column(name = "out_date", columnDefinition = "date")
	@Temporal(TemporalType.DATE)
	public Date getMoveDate() {
		return moveDate;
	}
	
	public void setDormitory(Dormitory dormitory) {
		this.dormitory = dormitory;
	}
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "dormitory_id")
	public Dormitory getDormitory() {
		return dormitory;
	}
	
	public void setViolationRecords(List<ViolationRecord> violationRecords) {
		this.violationRecords = violationRecords;
	}
	
	@OneToMany(mappedBy = "student")
	public List<ViolationRecord> getViolationRecords() {
		return violationRecords;
	}

}
