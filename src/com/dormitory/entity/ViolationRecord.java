package com.dormitory.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.dormitory.constant.ViolationType;

@Entity
@Table(name = "violation")
public class ViolationRecord extends Information {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	private ViolationType type;
	
	@NotNull
	@Valid
	private Student student;
	private DorAdmin dorAdmin;

	public ViolationRecord() {
		// TODO Auto-generated constructor stub
	}
	
	public ViolationRecord(Integer id, String content, Date sendDate, ViolationType type) {
		setId(id);
		setContent(content);
		setSendDate(sendDate);
		setType(type);
	}
	
	public void setType(ViolationType type) {
		this.type = type;
	}
	
	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.STRING)
	public ViolationType getType() {
		return type;
	}
	
	public void setStudent(Student student) {
		this.student = student;
	}
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id")
	public Student getStudent() {
		return student;
	}
	
	public void setDorAdmin(DorAdmin dorAdmin) {
		this.dorAdmin = dorAdmin;
	}
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "da_id")
	public DorAdmin getDorAdmin() {
		return dorAdmin;
	}

}
