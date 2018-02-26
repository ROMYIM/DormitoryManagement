package com.dormitory.service.imp;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dormitory.dao.IAdministratorDAO;
import com.dormitory.dao.IBuildingDAO;
import com.dormitory.dao.IDorAdminDAO;
import com.dormitory.dao.IDormitoryDAO;
import com.dormitory.dao.IStudentDAO;
import com.dormitory.entity.Administrator;
import com.dormitory.entity.Building;
import com.dormitory.entity.DorAdmin;
import com.dormitory.entity.Dormitory;
import com.dormitory.entity.Student;
import com.dormitory.service.IAdministratorService;

@Service("adminService")
@Transactional
public class AdministratorService implements IAdministratorService {

	@Resource(name = "adminDAO")
	private IAdministratorDAO administratorDAO;
	
	@Resource(name = "studentDAO")
	private IStudentDAO studentDAO;

	@Resource(name = "dorAdminDAO")
	private IDorAdminDAO dorAdminDAO;
	
	@Resource(name = "dormitoryDAO")
	private IDormitoryDAO dormitoryDAO;
	
	@Resource(name = "buildingDAO")
	private IBuildingDAO buildingDAO;
	
	public AdministratorService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void registerAdministrator(Administrator administrator) {
		// TODO Auto-generated method stub
		administratorDAO.save(administrator);
	}

	@Override
	public void changeAdministratorInfo(Administrator administrator) {
		// TODO Auto-generated method stub
		administratorDAO.update(administrator);
	}

	@Override
	public void changeAdminInfo(String hql, Object... params) {
		// TODO Auto-generated method stub
		administratorDAO.operateByHQL(hql, params);
	}

	@Override
	public void removeAdminInfo(Administrator administrator) {
		// TODO Auto-generated method stub
		administratorDAO.delete(administrator);
	}

	@Override
	public void removeAdminInfoById(String id) {
		// TODO Auto-generated method stub
		administratorDAO.operateByHQL("delete Administrator a where a.id = ?", id);
	}

	@Override
	public Administrator findAdminById(String id) {
		// TODO Auto-generated method stub
		return administratorDAO.queryById(Administrator.class, id);
	}

	@Override
	public Student findStudentInfo(String studentId) {
		// TODO Auto-generated method stub
		return studentDAO.queryById(Student.class, studentId);
	}

	@Override
	public List<Student> findAllStudents() {
		// TODO Auto-generated method stub
		return studentDAO.queryAll(Student.class);
	}

	@Override
	public void updateStudentInfo(Student student) {
		// TODO Auto-generated method stub
		studentDAO.update(student);
	}

	@Override
	public void changeStudentInfo(String hql, Object... params) {
		// TODO Auto-generated method stub
		studentDAO.operateByHQL(hql, params);
	}

	@Override
	public void removeStudentInfo(Student student) {
		// TODO Auto-generated method stub
		studentDAO.delete(student);
	}

	@Override
	public void removeStudentInfoById(String studentId) {
		// TODO Auto-generated method stub
		studentDAO.operateByHQL("delete Student s where s.id = ?", studentId);
	}

	@Override
	public void studentEnterDormitory(Student student, String dormitoryId) {
		// TODO Auto-generated method stub
		Dormitory dormitory = dormitoryDAO.queryById(Dormitory.class, dormitoryId);
		if (dormitory != null && student.getDormitory() == null) {
			student.setDormitory(dormitory);
			student.setCheckInDate(new Date());
			studentDAO.saveOrUpdate(student);
		}
	}

	@Override
	public void studentLeaveDormitory(Student student) {
		// TODO Auto-generated method stub
		student.setMoveDate(new Date());
		student.setDormitory(null);
		studentDAO.update(student);
	}

	@Override
	public void updateDorAdmin(DorAdmin dorAdmin) {
		// TODO Auto-generated method stub
		dorAdminDAO.update(dorAdmin);
	}

	@Override
	public void changeDorAdminInfo(String hql, Object... params) {
		// TODO Auto-generated method stub
		dorAdminDAO.operateByHQL(hql, params);
	}

	@Override
	public void removeDorAdminInfo(DorAdmin dorAdmin) {
		// TODO Auto-generated method stub
		dorAdminDAO.delete(dorAdmin);
	}

	@Override
	public void removeDorAdminById(String dorAdminId) {
		// TODO Auto-generated method stub
		dorAdminDAO.operateByHQL("delete DorAdmin da where da.id = ?", dorAdminId);
	}

	@Override
	public DorAdmin findDorAdminById(String dorAdminId) {
		// TODO Auto-generated method stub
		return dorAdminDAO.queryById(DorAdmin.class, dorAdminId);
	}

	@Override
	public List<DorAdmin> findAllDorAdmins() {
		// TODO Auto-generated method stub
		return dorAdminDAO.queryAll(DorAdmin.class);
	}

	@Override
	public void manageNewBuilding(Integer buildingNum, DorAdmin dorAdmin, Date manageDate) {
		// TODO Auto-generated method stub
		Building building = buildingDAO.queryById(Building.class, buildingNum);
		if (building != null && (dorAdmin.getManageBuilding() == null || dorAdmin.getManageBuilding().getBuildingNum() != buildingNum)) {
			dorAdmin.setManageBuilding(building);
			dorAdmin.setManageDate(manageDate);
			dorAdminDAO.saveOrUpdate(dorAdmin);
		}
	}

	@Override
	public void manageNewBuilding(Integer buildingNum, DorAdmin dorAdmin) {
		// TODO Auto-generated method stub
		Building building = buildingDAO.queryById(Building.class, buildingNum);
		if (building != null && (dorAdmin.getManageBuilding() == null || dorAdmin.getManageBuilding().getBuildingNum() != buildingNum)) {
			dorAdmin.setManageBuilding(building);
			dorAdmin.setManageDate(new Date());
			dorAdminDAO.saveOrUpdate(dorAdmin);
		}
	}

}
