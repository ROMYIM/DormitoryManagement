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

import com.dormitory.constant.RepairStatus;
import com.dormitory.constant.RepairType;

@Entity
@Table(name = "repair")
public class RepairInformation extends Information {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Valid
	private Dormitory dormitory;
	
	@NotNull
	private RepairType type;
	
	@NotNull
	private RepairStatus status;
	private MaintenanceWorker worker;

	public RepairInformation() {
		// TODO Auto-generated constructor stub
	}
	
	public RepairInformation(Integer id, RepairType type, RepairStatus status, String content, Date sendDate) {
		setId(id);
		this.type = type;
		this.status = status;
		this.setContent(content);
		this.setSendDate(sendDate);
	}
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "dormitory_id")
	public Dormitory getDormitory() {
		return dormitory;
	}
	
	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.STRING)
	public RepairType getType() {
		return type;
	}
	
	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	public RepairStatus getStatus() {
		return status;
	}
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "worker_id")
	public MaintenanceWorker getWorker() {
		return worker;
	}
	
	public void setDormitory(Dormitory dormitory) {
		this.dormitory = dormitory;
	}
	
	public void setType(RepairType type) {
		this.type = type;
	}
	
	public void setStatus(RepairStatus status) {
		this.status = status;
	}
	
	public void setWorker(MaintenanceWorker worker) {
		this.worker = worker;
	}

}
