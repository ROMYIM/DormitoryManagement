package com.dormitory.service.imp;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dormitory.constant.RepairStatus;
import com.dormitory.constant.RepairType;
import com.dormitory.dao.IRepairDAO;
import com.dormitory.dao.IWorkerDAO;
import com.dormitory.entity.MaintenanceWorker;
import com.dormitory.entity.RepairInformation;
import com.dormitory.service.IWorkerService;
import com.dormitory.util.DateUtil;

@Service("workerService")
@Transactional
public class WorkerService implements IWorkerService {

	@Resource(name = "workerDAO")
	private IWorkerDAO workerDAO;
	
	@Resource(name = "repairDAO")
	private IRepairDAO repairDAO;
	
	public WorkerService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void registerWorker(MaintenanceWorker worker) {
		// TODO Auto-generated method stub
		workerDAO.save(worker);
	}

	@Override
	public MaintenanceWorker findWorkerById(String Id) {
		// TODO Auto-generated method stub
		return workerDAO.queryById(MaintenanceWorker.class, Id);
	}

	@Override
	public void changeWorkerInfo(MaintenanceWorker worker) {
		// TODO Auto-generated method stub
		workerDAO.update(worker);
	}

	@Override
	public void takeRepair(MaintenanceWorker worker, Integer repairId) {
		// TODO Auto-generated method stub
		RepairInformation repairInformation = repairDAO.queryById(RepairInformation.class, repairId);
		if (repairInformation != null && repairInformation.getWorker() == null) {
			repairInformation.setWorker(worker);
			repairDAO.update(repairInformation);
		}
	}

	@Override
	public void finishRepair(MaintenanceWorker worker, Integer repairId) {
		// TODO Auto-generated method stub
		RepairInformation repairInformation = repairDAO.queryById(RepairInformation.class, repairId);
		if (repairInformation.getWorker().getId() == worker.getId() && repairInformation.getStatus() == RepairStatus.BROKEN) {
			repairInformation.setStatus(RepairStatus.REPAIRED);
			repairDAO.update(repairInformation);
		}
	}

	@Override
	public List<RepairInformation> findRepairsByWorker(String id, RepairStatus status, Date from, Date to) {
		// TODO Auto-generated method stub
		String format = "yyyy-MM-dd";
		StringBuffer hqlBuffer = new StringBuffer(300);
		hqlBuffer.append("select new RepairInformation(type, status, content, sendDate) from RepairInformation r where r.worker.id = ")
		.append(id);
		if (status != null) {
			hqlBuffer.append(" and r.status = ").append(status);
		}
		if (from == null && to == null) {
		} else if (from == null && to != null) {
			hqlBuffer.append(" and r.sendDate <= to_date('").append(DateUtil.dateToString(to, format)).append("', '").append(format).append("')");
		} else if (from != null && to == null) {
			hqlBuffer.append(" and r.sendDate >= to_date('").append(DateUtil.dateToString(from, format)).append("', '").append(format).append("')");
		} else if (from != null && to != null) {
			hqlBuffer.append(" and r.sendDate between to_date('").append(DateUtil.dateToString(from, format)).append("', '")
			.append(format).append("') and to_date('").append(DateUtil.dateToString(to, format)).append("', '").append(format).append("')");
		}
		return (List<RepairInformation>) repairDAO.queryByHQL(hqlBuffer.toString());
	}

	@Override
	public List<RepairInformation> findRepairs(RepairType type, Date from, Date to) {
		// TODO Auto-generated method stub
		StringBuffer hqlBuffer = new StringBuffer("select new RepairInformation() from RepairInformation r where r.type = ? and r.worker = null");
		if (from == null && to == null) {
			return (List<RepairInformation>) repairDAO.queryByHQL(hqlBuffer.toString(), type);
		} else if (from != null && to == null) {
			hqlBuffer.append(" and r.sendDate >= ?");
			return (List<RepairInformation>) repairDAO.queryByHQL(hqlBuffer.toString(), type, from);
		} else if (from == null && to != null) {
			hqlBuffer.append(" and r.sendDate <= ?");
			return (List<RepairInformation>) repairDAO.queryByHQL(hqlBuffer.toString(), type, to);
		} else if (from != null && to != null) {
			hqlBuffer.append(" and r.sendDate between ? and ?");
			return (List<RepairInformation>) repairDAO.queryByHQL(hqlBuffer.toString(), type, from, to);
		}
		return null;
	}

}
