package com.dormitory.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.dormitory.constant.RepairType;

@Entity
@Table(name = "maintenance_worker")
public class MaintenanceWorker extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<RepairInformation> repairInformations;
	@NotNull
	private RepairType workerType;

	public MaintenanceWorker() {
		// TODO Auto-generated constructor stub
		this.repairInformations = new ArrayList<>();
	}
	
	public void setRepairInformations(List<RepairInformation> repairInformations) {
		this.repairInformations = repairInformations;
	}
	
	@OneToMany(mappedBy = "worker")
	public List<RepairInformation> getRepairInformations() {
		return repairInformations;
	}
	
	public void setWorkerType(RepairType workerType) {
		this.workerType = workerType;
	}
	
	@Column(name = "work_type", nullable = false)
	@Enumerated(EnumType.STRING)
	public RepairType getWorkerType() {
		return workerType;
	}

}
