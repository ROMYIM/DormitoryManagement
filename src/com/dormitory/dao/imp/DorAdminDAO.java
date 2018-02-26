package com.dormitory.dao.imp;

import org.springframework.stereotype.Repository;

import com.dormitory.dao.IDorAdminDAO;
import com.dormitory.entity.DorAdmin;

@Repository("dorAdminDAO")
public class DorAdminDAO extends BaseDAO<DorAdmin, String> implements IDorAdminDAO {

	

}
