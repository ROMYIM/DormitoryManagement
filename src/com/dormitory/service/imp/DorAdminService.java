package com.dormitory.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dormitory.dao.IBuildingDAO;
import com.dormitory.dao.IDorAdminDAO;
import com.dormitory.dao.IDormitoryDAO;
import com.dormitory.dao.INoticeDAO;
import com.dormitory.dao.IStudentDAO;
import com.dormitory.dao.IViolationDAO;
import com.dormitory.entity.Building;
import com.dormitory.entity.DorAdmin;
import com.dormitory.entity.Dormitory;
import com.dormitory.entity.Notice;
import com.dormitory.entity.Student;
import com.dormitory.entity.ViolationRecord;
import com.dormitory.service.IDorAdminService;
import com.dormitory.util.DateUtil;

@Service("dorAdminService")
@Transactional
public class DorAdminService implements IDorAdminService {

	@Resource(name = "dorAdminDAO")
	private IDorAdminDAO dorAdminDAO;
	
	@Resource(name = "buildingDAO")
	private IBuildingDAO buildingDAO;
	
	@Resource(name = "studentDAO")
	private IStudentDAO studentDAO;
	
	@Resource(name = "dormitoryDAO")
	private IDormitoryDAO dormitoryDAO;
	
	@Resource(name = "violationDAO")
	private IViolationDAO violationDAO;
	
	@Resource(name = "noticeDAO")
	private INoticeDAO noticeDAO;
	
	public DorAdminService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void registerDorAdmin(DorAdmin dorAdmin) {
		// TODO Auto-generated method stub
		dorAdminDAO.save(dorAdmin);
	}

	@Override
	public DorAdmin findDorAdminById(String id) {
		// TODO Auto-generated method stub
		return dorAdminDAO.queryById(DorAdmin.class, id);
	}

	@Override
	public Building findManageBuilding(String id) {
		// TODO Auto-generated method stub
		DorAdmin dorAdmin = findDorAdminById(id);
		if (dorAdmin != null) {
			return dorAdmin.getManageBuilding();
		}
		return null;
	}

	@Override
	public List<Dormitory> findMangeDormitories(String id) {
		// TODO Auto-generated method stub
		DorAdmin dorAdmin = findDorAdminById(id);
		if (dorAdmin != null) {
			return dorAdmin.getManageBuilding().getDormitories();
		}
		return null;
	}

	@Override
	public List<DorAdmin> findDorAdminsByBuilding(Integer building) {
		// TODO Auto-generated method stub
		return buildingDAO.queryById(Building.class, building).getDorAdmins();
	}

	@Override
	public List<DorAdmin> findDorAdminPartners(String id) {
		// TODO Auto-generated method stub
		DorAdmin dorAdmin = findDorAdminById(id);
		if (dorAdmin != null) {
			return dorAdmin.getManageBuilding().getDorAdmins();
		}
		return null;
	}

	@Override
	public List<Student> findStudentsByDormitory(String dormitoryId) {
		// TODO Auto-generated method stub
		return (List<Student>) studentDAO.queryByHQL("select new Student(id, name, major, grade, checkInDate)"
				+ " from Student s where s.dormitory.dormitoryId = ?", dormitoryId);
	}

	@Override
	public Student findStudentById(String studentId) {
		// TODO Auto-generated method stub
		return studentDAO.queryById(Student.class, studentId);
	}

	@Override
	public Dormitory findDormitoryByStudent(String studentId) {
		// TODO Auto-generated method stub
		return findStudentById(studentId).getDormitory();
	}

	@Override
	public void addViolationRecord(String studentId, ViolationRecord violationRecord, DorAdmin dorAdmin) {
		// TODO Auto-generated method stub
		Student student = findStudentById(studentId);
		if (student != null) {
			violationRecord.setSendDate(new Date());
			violationRecord.setStudent(student);
			violationRecord.setDorAdmin(dorAdmin);
			violationDAO.saveOrUpdate(violationRecord);
		}
	}

	@Override
	public void addViolationRecord(ViolationRecord violationRecord, DorAdmin dorAdmin) {
		// TODO Auto-generated method stub
		violationRecord.setSendDate(new Date());
		violationRecord.setDorAdmin(dorAdmin);
		violationDAO.save(violationRecord);
	}

	@Override
	public void changeViolationRecord(ViolationRecord violationRecord) {
		// TODO Auto-generated method stub
		violationDAO.update(violationRecord);
	}

	@Override
	public void removeViolationRecord(ViolationRecord violationRecord) {
		// TODO Auto-generated method stub
		violationDAO.delete(violationRecord);
	}

	@Override
	public void removeViolationRecord(Integer violationId) {
		// TODO Auto-generated method stub
		violationDAO.operateByHQL("delete ViolationRecord v where v.id = ?", violationId);
	}

	@Override
	public List<ViolationRecord> findViolationRecord(String dorAdminId, Date from, Date to) {
		// TODO Auto-generated method stub
		if (from == null && to == null) {
			return (List<ViolationRecord>) violationDAO.queryByHQL("select new ViolationRecord(content, sendDate, type) from ViolationRecord v where v.dorAdmin.id = ?", dorAdminId);
		} else if (from == null && to != null) {
			return (List<ViolationRecord>) violationDAO.queryByHQL("select new ViolationRecord(content, sendDate, type) from Violation v where v.dorAdmin.id = ? and v.sendDate <= ?", dorAdminId, to);
		} else if (from != null && to == null) {
			return (List<ViolationRecord>) violationDAO.queryByHQL("select new ViolationRecord(content, sendDate, type) from Violation v where v.dorAdmin.id = ? and v.sendDate >= ?", dorAdminId, from);
		} else if (from != null && to != null) {
			return (List<ViolationRecord>) violationDAO.queryByHQL("select new ViolationRecord(content, sendDate, type) from Violation v where v.dorAdmin.id = ? and v.sendDate between ? and ?", dorAdminId, from, to);
		}
		return null;
	}

	@Override
	public List<ViolationRecord> findViolationByStudent(String dorAdminId, String studentId, Date from, Date to) {
		// TODO Auto-generated method stub
		if (from == null && to == null) {
			return (List<ViolationRecord>) violationDAO.queryByHQL("select new ViolationRecord(content, sendDate, type) from ViolationRecord v where v.dorAdmin.id = ? and v.student.id = ?", dorAdminId, studentId);
		} else if (from == null && to != null) {
			return (List<ViolationRecord>) violationDAO.queryByHQL("select new ViolationRecord(content, sendDate, type) from Violation v where v.dorAdmin.id = ? and v.student.id = ? and v.sendDate <= ?", dorAdminId, studentId, to);
		} else if (from != null && to == null) {
			return (List<ViolationRecord>) violationDAO.queryByHQL("select new ViolationRecord(content, sendDate, type) from Violation v where v.dorAdmin.id = ? and v.student.id = ? and v.sendDate >= ?", dorAdminId, studentId, from);
		} else if (from != null && to != null) {
			return (List<ViolationRecord>) violationDAO.queryByHQL("select new ViolationRecord(content, sendDate, type) from Violation v where v.dorAdmin.id = ? and v.student.id = ? and v.sendDate between ? and ?", dorAdminId, studentId, from, to);
		}
		return null;
	}

	@Override
	public void declareNotice(String id, Notice notice) {
		// TODO Auto-generated method stub
		Building building = findManageBuilding(id);
		List<Building> buildings = notice.getBuildings();
		if (buildings == null) {
			buildings = new ArrayList<>();
		}
		buildings.add(building);
		notice.setBuildings(buildings);
		noticeDAO.saveOrUpdate(notice);
	}

	@Override
	public List<Notice> findNoticesBySendDate(String id, Date from, Date to) {
		// TODO Auto-generated method stub
		Integer buildingNum = findManageBuilding(id).getBuildingNum();
		String format = "yyyy-MM-dd";
		StringBuffer hqlBuffer = new StringBuffer("select new Notice(sendDate, content) from Notice n join n.buildings b where b.buildingNum = ");
		hqlBuffer.append(buildingNum);
		if (from == null && to == null) {
		} else if (from == null && to != null) {
			hqlBuffer.append(" and n.sendDate <= to_date('").append(DateUtil.dateToString(to, format)).append("', '").append(format).append("')");
		} else if (from != null && to == null) {
			hqlBuffer.append(" and n.sendDate >= to_date('").append(DateUtil.dateToString(from, format)).append("', '").append(format).append("')");
		} else if (from != null && to != null) {
			hqlBuffer.append(" and n.sendDate between to_date('").append(DateUtil.dateToString(from, format)).append("', '")
			.append(format).append("') and to_date('").append(DateUtil.dateToString(to, format)).append("', '").append(format).append("')");
		}
		return (List<Notice>) noticeDAO.queryByHQL(hqlBuffer.toString());
	}

	@Override
	public List<Notice> findNoticesByDeadline(String id, Date from, Date to) {
		// TODO Auto-generated method stub
		Integer buildingNum = findManageBuilding(id).getBuildingNum();
		String format = "yyyy-MM-dd";
		StringBuffer hqlBuffer = new StringBuffer("select new Notice(sendDate, content) from Notice n join n.buildings b where b.building = ");
		hqlBuffer.append(buildingNum);
		if (from == null && to == null) {
		} else if (from == null && to != null) {
			hqlBuffer.append(" and n.deadline <= to_date('").append(DateUtil.dateToString(to, format)).append("', '").append(format).append("')");
		} else if (from != null && to == null) {
			hqlBuffer.append(" and n.deadline >= to_date('").append(DateUtil.dateToString(from, format)).append("', '").append(format).append("')");
		} else if (from != null && to != null) {
			hqlBuffer.append(" and n.deadline between to_date('").append(DateUtil.dateToString(from, format)).append("', '")
			.append(format).append("') and to_date('").append(DateUtil.dateToString(to, format)).append("', '").append(format).append("')");
		}
		return (List<Notice>) noticeDAO.queryByHQL(hqlBuffer.toString());
	}

	@Override
	public List<Notice> findNotices(String id, Date sendDate, Date deadline) {
		// TODO Auto-generated method stub
		Integer buildingNum = findManageBuilding(id).getBuildingNum();
		return (List<Notice>) noticeDAO.queryByHQL("select new Notice(sendDate, content) from Notice n join n.buildings b where n.sendDate = ? and n.deadline = ? and b.building = ?", sendDate, deadline, buildingNum);
	}


}
