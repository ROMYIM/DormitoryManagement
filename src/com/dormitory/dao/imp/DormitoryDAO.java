package com.dormitory.dao.imp;

import org.springframework.stereotype.Repository;

import com.dormitory.dao.IDormitoryDAO;
import com.dormitory.entity.Dormitory;

@Repository("dormitoryDAO")
public class DormitoryDAO extends BaseDAO<Dormitory, String> implements IDormitoryDAO {

	

}
