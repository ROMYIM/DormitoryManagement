package com.dormitory.dao.imp;

import org.springframework.stereotype.Repository;

import com.dormitory.dao.IViolationDAO;
import com.dormitory.entity.ViolationRecord;

@Repository("violationDAO")
public class ViolationDAO extends BaseDAO<ViolationRecord, Integer> implements IViolationDAO {


}
