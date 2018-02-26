package com.dormitory.dao.imp;

import org.springframework.stereotype.Repository;

import com.dormitory.dao.IWorkerDAO;
import com.dormitory.entity.MaintenanceWorker;

@Repository("workerDAO")
public class WorkerDAO extends BaseDAO<MaintenanceWorker, String> implements IWorkerDAO {

}
