package com.dormitory.service;

import java.util.Date;
import java.util.List;

import com.dormitory.constant.RepairStatus;
import com.dormitory.constant.RepairType;
import com.dormitory.entity.MaintenanceWorker;
import com.dormitory.entity.RepairInformation;

public interface IWorkerService {
	
	public void registerWorker(MaintenanceWorker worker);
	
	public void changeWorkerInfo(MaintenanceWorker worker);

	public MaintenanceWorker findWorkerById(String Id);
	
	public void takeRepair(MaintenanceWorker worker, Integer repairId);
	
	public void finishRepair(MaintenanceWorker worker, Integer repairId);
	
	public List<RepairInformation> findRepairsByWorker(String id, RepairStatus status, Date from, Date to); 
	
	public List<RepairInformation> findRepairs(RepairType type, Date from, Date to);
	
}
