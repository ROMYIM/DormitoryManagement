package com.dormitory.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dormitory.constant.BillType;
import com.dormitory.constant.RepairStatus;
import com.dormitory.entity.Bill;
import com.dormitory.entity.Building;
import com.dormitory.entity.Dormitory;
import com.dormitory.entity.Notice;
import com.dormitory.entity.RepairInformation;
import com.dormitory.entity.Student;
import com.dormitory.entity.ViolationRecord;

public interface IStudentService {
	
	public void registerStudent(Student student);
	
	public Student findStudentById(String id);
	
	public Dormitory findDormitoryByStudent(String id);
	
	public Dormitory findDormitory(String dormitoryId);
	
	public Building findBuilding(String id);
	
	public List<Student> findStudents(String hql, Object... params);
	
	public List<Student> findAllStudents();
	
	public int operateStudents(String hql, Object... params);
	
	public void payBill(String dormitoryId, Float bill, BillType billType);
	
	public Map<String, String> lookBalance(String dormitoryId);
	
	public List<Bill> lookBills(String dormitoryId, BillType type, Date from, Date to);
	
	public void declareRepair(RepairInformation repairInformation);
	
	public void declareRepair(RepairInformation repairInformation, String dormitoryId);
	
	public List<RepairInformation> lookRepairs(String dormitoryId, Date from, Date to, RepairStatus status);
	
	public List<ViolationRecord> lookViolationes(String id, Date from, Date to);
	
	public List<Notice> findNoticesBySendDate(String id, Date from, Date to);
	
	public List<Notice> findNoticesByDeadline(String id, Date from, Date to);
	
	public List<Notice> findNotices(String id, Date from, Date to);
	
	public List<Notice> findNoticesBySendDate(Integer buildingNum, Date from, Date to);
	
	public List<Notice> findNoticesByDeadline(Integer buildingNum, Date from, Date to);
	
	public List<Notice> findNotices(Integer buildingNum, Date from, Date to);
}
