package com.dormitory.service;

import java.util.Date;
import java.util.List;

import com.dormitory.entity.Building;
import com.dormitory.entity.DorAdmin;
import com.dormitory.entity.Dormitory;
import com.dormitory.entity.Notice;
import com.dormitory.entity.Student;
import com.dormitory.entity.ViolationRecord;

public interface IDorAdminService {

	public void registerDorAdmin(DorAdmin dorAdmin);
	
	public DorAdmin findDorAdminById(String id);
	
	public Building findManageBuilding(String id);
	
	public List<DorAdmin> findDorAdminsByBuilding(Integer building);
	
	public List<DorAdmin> findDorAdminPartners(String id);
	
	public List<Student> findStudentsByDormitory(String dormitoryId);
	
	public Student findStudentById(String studentId);
	
	public Dormitory findDormitoryByStudent(String studentId);
	
	public List<Dormitory> findMangeDormitories(String id);
	
	public void addViolationRecord(String studentId, ViolationRecord violationRecord, DorAdmin dorAdmin);
	
	public void addViolationRecord(ViolationRecord violationRecord, DorAdmin dorAdmin);
	
	public void changeViolationRecord(ViolationRecord violationRecord);
	
	public void removeViolationRecord(ViolationRecord violationRecord);
	
	public void removeViolationRecord(Integer violationId);
	
	public List<ViolationRecord> findViolationRecord(String dorAdminId, Date from, Date to);
	
	public List<ViolationRecord> findViolationByStudent(String dorAdminId, String studentId, Date from, Date to);
	
	public void declareNotice(String id, Notice notice);
	
	public List<Notice> findNoticesBySendDate(String id, Date from, Date to);
	
	public List<Notice> findNoticesByDeadline(String id, Date from, Date to);
	
	public List<Notice> findNotices(String id, Date sendDate, Date deadline);
}
