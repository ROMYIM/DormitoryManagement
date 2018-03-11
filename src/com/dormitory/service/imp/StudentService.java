package com.dormitory.service.imp;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dormitory.constant.BillType;
import com.dormitory.constant.RepairStatus;
import com.dormitory.dao.IBillDAO;
import com.dormitory.dao.IDormitoryDAO;
import com.dormitory.dao.INoticeDAO;
import com.dormitory.dao.IRepairDAO;
import com.dormitory.dao.IStudentDAO;
import com.dormitory.dao.IViolationDAO;
import com.dormitory.entity.Bill;
import com.dormitory.entity.Building;
import com.dormitory.entity.Dormitory;
import com.dormitory.entity.Notice;
import com.dormitory.entity.RepairInformation;
import com.dormitory.entity.Student;
import com.dormitory.entity.ViolationRecord;
import com.dormitory.service.IStudentService;

@Service("studentService")
@Transactional
public class StudentService implements IStudentService {
	
	@Resource(name = "studentDAO")
	private IStudentDAO studentDAO;
	
	@Resource(name = "dormitoryDAO")
	private IDormitoryDAO dormitoryDAO;
	
	@Resource(name = "repairDAO")
	private IRepairDAO repairDAO;
	
	@Resource(name = "violationDAO")
	private IViolationDAO violationDAO;
	
	@Resource(name = "noticeDAO")
	private INoticeDAO noticeDAO;
	
	@Resource(name = "billDAO")
	private IBillDAO billDAO;
	
	public StudentService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void registerStudent(Student student) {
		// TODO Auto-generated method stub
		studentDAO.save(student);
	}

	@Override
	public Student findStudentById(String id) {
		// TODO Auto-generated method stub
		return studentDAO.queryById(Student.class, id);
	}

	@Override
	public Dormitory findDormitoryByStudent(String id) {
		// TODO Auto-generated method stub
		Student tStudent = findStudentById(id);
		if (tStudent != null) {
			return tStudent.getDormitory();
		}
		return null;
	}

	@Override
	public List<Student> findStudents(String hql, Object... params) {
		// TODO Auto-generated method stub
		return (List<Student>) studentDAO.queryByHQL(hql, params);
	}

	@Override
	public List<Student> findAllStudents() {
		// TODO Auto-generated method stub
		return studentDAO.queryAll(Student.class);
	}

	@Override
	public int operateStudents(String hql, Object... params) {
		// TODO Auto-generated method stub
		return studentDAO.operateByHQL(hql, params);
	}

	@Override
	public void payBill(String dormitoryId, Float bill, BillType billType) {
		// TODO Auto-generated method stub
		Dormitory dormitory = dormitoryDAO.queryById(Dormitory.class, dormitoryId);
		if (dormitory == null) {
			return;
		}
		if (billType == BillType.ELECTRIC) {
			Float eBill = dormitory.getEbills();
			if (eBill == null) {
				eBill = bill;
			} else {
				eBill += bill;
			}
			dormitory.setEbills(eBill);
		} else if (billType == BillType.WATER) {
			Float wBill = dormitory.getWbills();
			if (wBill == null) {
				wBill = bill;
			} else {
				wBill += bill;
			}
			dormitory.setWbills(wBill);
		}
		Bill billRecord = new Bill(new Date(), billType, bill, dormitory);
		billDAO.save(billRecord);
		dormitoryDAO.update(dormitory);
	}

	@Override
	public Map<String, String> lookBalance(String dormitoryId) {
		// TODO Auto-generated method stub
		Dormitory dormitory = dormitoryDAO.queryById(Dormitory.class, dormitoryId);
		if (dormitory != null) {
			Float eBills = dormitory.getEbills();
			Float wBills = dormitory.getWbills();
			Map<String, String> billsMap = new ConcurrentHashMap<>();
			billsMap.put("电费", String.valueOf(eBills));
			billsMap.put("水费", String.valueOf(wBills));
			return billsMap;
		}
		return null;
	}

	@Override
	public void declareRepair(RepairInformation repairInformation) {
		// TODO Auto-generated method stub
		repairDAO.save(repairInformation);
	}

	@Override
	public void declareRepair(RepairInformation repairInformation, String dormitoryId) {
		// TODO Auto-generated method stub
		Dormitory dormitory = dormitoryDAO.queryById(Dormitory.class, dormitoryId);
		if (dormitory != null) {
			repairInformation.setDormitory(dormitory);
			repairDAO.saveOrUpdate(repairInformation);
		}
	}

	@Override
	public List<RepairInformation> lookRepairs(String dormitoryId, Date from, Date to, RepairStatus status) {
		// TODO Auto-generated method stub
		StringBuffer hqlBuffer = new StringBuffer(300);
		hqlBuffer.append("select new RepairInformation(r.id, r.type, r.status, r.content, r.sendDate) from RepairInformation r where r.dormitory.dormitoryId = ?");
		if (status != null) {
			hqlBuffer.append(" and r.status = '").append(status.name()).append("'");
		}
		if (from == null && to == null) {
			return (List<RepairInformation>) repairDAO.queryByHQL(hqlBuffer.toString(), dormitoryId);
		} else if (from == null && to != null) {
			hqlBuffer.append(" and r.sendDate <= ?");
			return (List<RepairInformation>) repairDAO.queryByHQL(hqlBuffer.toString(), dormitoryId, to);
		} else if (from != null && to == null) {
			hqlBuffer.append(" and r.sendDate >= ?");
			return (List<RepairInformation>) repairDAO.queryByHQL(hqlBuffer.toString(), dormitoryId, from);
		} else if (from != null && to != null) {
			hqlBuffer.append(" and r.sendDate between ? and ?");
			return (List<RepairInformation>) repairDAO.queryByHQL(hqlBuffer.toString(), dormitoryId, from, to);
		}
		return null;
	}

	@Override
	public List<ViolationRecord> lookViolationes(String id, Date from, Date to) {
		// TODO Auto-generated method stub
		if (from == null && to == null) {
			return (List<ViolationRecord>) violationDAO.queryByHQL("select new ViolationRecord(v.id, v.content, v.sendDate, v.type) from ViolationRecord v where v.student.id = ?", id);
		} else if (from == null && to != null) {
			return (List<ViolationRecord>) violationDAO.queryByHQL("select new ViolationRecord(v.id, v.content, v.sendDate, v.type) from Violation v where v.student.id = ? and v.sendDate <= ?", id, to);
		} else if (from != null && to == null) {
			return (List<ViolationRecord>) violationDAO.queryByHQL("select new ViolationRecord(v.id, v.content, v.sendDate, v.type) from Violation v where v.student.id = ? and v.sendDate >= ?", id, from);
		} else if (from != null && to != null) {
			return (List<ViolationRecord>) violationDAO.queryByHQL("select new ViolationRecord(v.id, v.content, v.sendDate, v.type) from Violation v where v.student.id = ? and v.sendDate between ? and ?", id, from, to);
		}
		return null;
	}

	@Override
	public Building findBuilding(String id) {
		// TODO Auto-generated method stub
		return findDormitoryByStudent(id).getBuilding();
	}

	@Override
	public List<Notice> findNoticesBySendDate(String id, Date from, Date to) {
		// TODO Auto-generated method stub
		Building building = findBuilding(id);
		Integer buildingNum = building.getBuildingNum();
		return findNotices(buildingNum, from, to);
	}

	@Override
	public List<Notice> findNoticesByDeadline(String id, Date from, Date to) {
		// TODO Auto-generated method stub
		Integer buildingNum = findBuilding(id).getBuildingNum();
		return findNoticesByDeadline(buildingNum, from, to);
	}

	@Override
	public List<Notice> findNotices(String id, Date from, Date to) {
		// TODO Auto-generated method stub
		Integer buildingNum = findBuilding(id).getBuildingNum();
		return findNotices(buildingNum, from, to);
	}

	@Override
	public Dormitory findDormitory(String dormitoryId) {
		// TODO Auto-generated method stub
		return dormitoryDAO.queryById(Dormitory.class, dormitoryId);
	}

	@Override
	public List<Notice> findNoticesBySendDate(Integer buildingNum, Date from, Date to) {
		// TODO Auto-generated method stub
		StringBuffer hqlBuffer = new StringBuffer("select new Notice(n.id, n.sendDate, n.content) from Notice n join n.buildings b where b.buildingNum = ?");
		if (from == null && to == null) {
			return (List<Notice>) noticeDAO.queryByHQL(hqlBuffer.toString(), buildingNum);
		} else if (from == null && to != null) {
			hqlBuffer.append(" and n.sendDate <= ?");
			return (List<Notice>) noticeDAO.queryByHQL(hqlBuffer.toString(), buildingNum, to);
		} else if (from != null && to == null) {
			hqlBuffer.append(" and n.sendDate >= ?");
			return (List<Notice>) noticeDAO.queryByHQL(hqlBuffer.toString(), buildingNum, from);
		} else if (from != null && to != null) {
			hqlBuffer.append(" and n.sendDate between ? and ?");
			return (List<Notice>) noticeDAO.queryByHQL(hqlBuffer.toString(), buildingNum, from, to);
		}
		return null;
	}

	@Override
	public List<Notice> findNoticesByDeadline(Integer buildingNum, Date from, Date to) {
		// TODO Auto-generated method stub
		StringBuffer hqlBuffer = new StringBuffer("select new Notice(n.id n.sendDate, n.content) from Notice n join n.buildings b where b.buildingNum = ?");
		if (from == null && to == null) {
			return (List<Notice>) noticeDAO.queryByHQL(hqlBuffer.toString(), buildingNum);
		} else if (from == null && to != null) {
			hqlBuffer.append(" and n.deadline <= ?");
			return (List<Notice>) noticeDAO.queryByHQL(hqlBuffer.toString(), buildingNum, to);
		} else if (from != null && to == null) {
			hqlBuffer.append(" and n.deadline >= ?");
			return (List<Notice>) noticeDAO.queryByHQL(hqlBuffer.toString(), buildingNum, from);
		} else if (from != null && to != null) {
			hqlBuffer.append(" and n.deadline between ? and ?");
			return (List<Notice>) noticeDAO.queryByHQL(hqlBuffer.toString(), buildingNum, from, to);
		}
		return null;
	}

	@Override
	public List<Notice> findNotices(Integer buildingNum, Date from, Date to) {
		// TODO Auto-generated method stub
		if (buildingNum != null) {
			return (List<Notice>) noticeDAO.queryByHQL("select new Notice(n.id, n.sendDate, n.content) from Notice n join n.buildings b where n.sendDate = ? and n.deadline = ? and b.buildingNum = ?", from, to, buildingNum);
		}
		return null;
	}

	@Override
	public List<Bill> lookBills(String dormitoryId, BillType type, Date from, Date to) {
		// TODO Auto-generated method stub
		StringBuffer hqlBuffer = new StringBuffer("select new Bill(b.id, b.payDate, b.type, b.payMoney) from Bill b where b.dormitory.dormitoryId = ?");
		if (type != null) {
			hqlBuffer.append(" and b.type = '").append(type.name()).append("'");
		}
		if (from == null && to == null) {
			return (List<Bill>) billDAO.queryByHQL(hqlBuffer.toString(), dormitoryId);
		} else if (from == null && to != null) {
			hqlBuffer.append(" and b.payDate <= ?");
			return (List<Bill>) billDAO.queryByHQL(hqlBuffer.toString(), dormitoryId, to);
		} else if (from != null && to == null) {
			hqlBuffer.append(" and b.payDate >= ?");
			return (List<Bill>) billDAO.queryByHQL(hqlBuffer.toString(), dormitoryId, from);
		} else if (from != null && to != null) {
			hqlBuffer.append(" and b.payDate between ? and ?");
			return (List<Bill>) billDAO.queryByHQL(hqlBuffer.toString(), dormitoryId, from, to);
		}
		return null;
	}
	
}
