package com.dormitory.dao.imp;

import org.springframework.stereotype.Repository;

import com.dormitory.dao.IAdministratorDAO;
import com.dormitory.entity.Administrator;

@Repository("adminDAO")
public class AdministratorDAO extends BaseDAO<Administrator, String> implements IAdministratorDAO {

	
}
