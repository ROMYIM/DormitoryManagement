package com.dormitory.dao.imp;

import org.springframework.stereotype.Repository;

import com.dormitory.dao.IRepairDAO;
import com.dormitory.entity.RepairInformation;

@Repository("repairDAO")
public class RepairDAO extends BaseDAO<RepairInformation, Integer> implements IRepairDAO {

}
