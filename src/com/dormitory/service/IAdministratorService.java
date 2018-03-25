package com.dormitory.service;

import java.util.Date;
import java.util.List;

import com.dormitory.entity.Administrator;
import com.dormitory.entity.DorAdmin;
import com.dormitory.entity.Student;

public interface IAdministratorService {

	public void registerAdministrator(Administrator administrator);
	
	public void changeAdministratorInfo(Administrator administrator);
	
	public void changeAdminInfo(String hql, Object... params);
	
	public void removeAdminInfo(Administrator administrator);
	
	public void removeAdminInfoById(String id);
	
	public Administrator findAdminById(String id);
	
	public Student findStudentInfo(String studentId);
	
	public List<Student> findAllStudents();
	
	public void updateStudentInfo(Student student);
	
	public void changeStudentInfo(String hql, Object... params);
	
	public void removeStudentInfo(Student student);
	
	public void removeStudentInfoById(String studentId);
	
	public boolean studentEnterDormitory(Student student, String dormitoryId);
	
	public void studentLeaveDormitory(Student student);
	
	public void updateDorAdmin(DorAdmin dorAdmin);
	
	public void changeDorAdminInfo(String hql, Object... params);
	
	public void removeDorAdminInfo(DorAdmin dorAdmin);
	
	public void removeDorAdminById(String dorAdminId);
	
	public DorAdmin findDorAdminById(String dorAdminId);
	
	public List<DorAdmin> findAllDorAdmins();
	
	public void manageNewBuilding(Integer buildingNum, DorAdmin dorAdmin, Date manageDate);
	
	public void manageNewBuilding(Integer buildingNum, DorAdmin dorAdmin);
}
