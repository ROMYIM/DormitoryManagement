package com.dormitory.controller.app;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.dormitory.constant.BillType;
import com.dormitory.constant.InformationQueryType;
import com.dormitory.constant.RepairStatus;
import com.dormitory.constant.ResultType;
import com.dormitory.entity.Bill;
import com.dormitory.entity.Building;
import com.dormitory.entity.Dormitory;
import com.dormitory.entity.Notice;
import com.dormitory.entity.RepairInformation;
import com.dormitory.entity.ResponseResult;
import com.dormitory.entity.Student;
import com.dormitory.entity.User;
import com.dormitory.entity.ViolationRecord;
import com.dormitory.service.IStudentService;
import com.dormitory.util.CookieUtil;

@Controller
@RequestMapping("/app/student")
@SessionAttributes(value = {"user"}, types = {Student.class})
@ResponseBody
public class AppStudentController {

	@Resource(name = "studentService")
	private IStudentService studentService;
	
	public AppStudentController() {
		// TODO Auto-generated constructor stub
	}
	
	@ModelAttribute("user")
	public Student initStudent(@CookieValue(value = "id", required = false) String id, 
			@RequestParam(value = "id", required = false) String studentId) {
		if (studentId != null && studentId.length() > 0) {
			return studentService.findStudentById(studentId);
		} else if (id != null && id.length() > 0) {
			return studentService.findStudentById(id);
		}  
		return null;
	}
	
	@RequestMapping(value = "/login", headers = "isFirst", method = RequestMethod.POST)
	public ResponseResult login(@ModelAttribute ResponseResult result, @ModelAttribute("user") Student student,
			@RequestHeader(value = "isFirst", defaultValue = "true") Boolean isFirst, @Valid User user, Errors errors, 
			HttpSession session, HttpServletResponse response) {
		if (errors.hasErrors()) {
			return result.setResult(ResultType.ERR_LOGIN);
		}
		if (student != null) {
			if (student.getPassword().equals(user.getPassword())) {
				if (isFirst) {
					Cookie idCookie = CookieUtil.setCookie("id", student.getId(), 7 * 24 * 60 * 60);
					Cookie passwordCookie = CookieUtil.setCookie("password", student.getPassword(), 7 * 24 * 60 * 60);
					Cookie authCookie = CookieUtil.setCookie("authentication", student.getAuthentication().name(), 7 * 24 * 60 * 60);
					response.addCookie(idCookie);
					response.addCookie(passwordCookie);
					response.addCookie(authCookie);
				}
				session.setAttribute("user", student);
				session.setMaxInactiveInterval(30 * 60);
				return result.setResult(ResultType.SUCCESS);
			}
			return result.setResult(ResultType.ERR_PASSWORD);
		}
		return result.setResult(ResultType.NO_USER);
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseResult register(@ModelAttribute ResponseResult result, Student student) {
		if (studentService.findStudentById(student.getId()) != null) {
			return result.setResult(ResultType.AL_REGISTER);
		}
		studentService.registerStudent(student);
		return result.setResult(ResultType.SUCCESS);
	}
	
	@RequestMapping(value = "/payBills/{billType}", params = "bills", method = RequestMethod.POST)
	public ResponseResult payBills(@PathVariable BillType billType, @ModelAttribute("user") Student student,
			@ModelAttribute ResponseResult result, @RequestParam(defaultValue = "0") Float bills) {
		Dormitory dormitory = student.getDormitory();
		if (dormitory == null) {
			return result.setResult(ResultType.ERR_DORMITORY);
		}
		studentService.payBill(dormitory.getDormitoryId(), bills, billType);
		return result.setResult(ResultType.SUCCESS);
	}
	
	@RequestMapping(value = "/lookBill", method = RequestMethod.GET)
	public ResponseResult lookBill(@ModelAttribute ResponseResult result, @ModelAttribute("user") Student student, 
			Model model) {
		if (student.getDormitory() == null) {
			return result.setResult(ResultType.ERR_DORMITORY);
		}
		String dormitoryId = student.getDormitory().getDormitoryId();
		Map<String, String> billMap = studentService.lookBalance(dormitoryId);
		model.addAttribute("Àﬁ…·∫≈", dormitoryId);
		model.addAllAttributes(billMap);
		result.setResult(billMap);
		return result;
	}
	
	@RequestMapping(value = "/lookBills", method = RequestMethod.GET)
	public ResponseResult lookBills(@ModelAttribute ResponseResult result, @ModelAttribute("user") Student student, 
			BillType type, Date from, Date to) {
		if (student.getDormitory() == null) {
			return result.setResult(ResultType.ERR_DORMITORY);
		}
		String dormitoryId = student.getDormitory().getDormitoryId();
		List<Bill> bills = studentService.lookBills(dormitoryId, type, from, to);
		result.setResult(ResultType.SUCCESS).setResult(bills);
		return result;
	}
	
	@RequestMapping(value = "/lookViolations", method = RequestMethod.GET)
	public ResponseResult lookViolation(@ModelAttribute ResponseResult result, 
			@ModelAttribute("user") Student student, Date from, Date to) {
		List<ViolationRecord> violationRecords = studentService.lookViolationes(student.getId(), from, to);
		if (violationRecords != null) {
			result.setResult(ResultType.SUCCESS).setResult(violationRecords);
			return result;
		}
		return result.setResult(ResultType.ERR_VIOLATION);
	}
	
	@RequestMapping(value = "/declareRepair", method = RequestMethod.POST)
	public ResponseResult declareRepair(@ModelAttribute ResponseResult result, 
			@ModelAttribute("user") Student student, RepairInformation repairInformation) {
		if (student.getDormitory() == null) {
			return result.setResult(ResultType.ERR_DORMITORY);
		}
		String dormitoryId = student.getDormitory().getDormitoryId();
		studentService.declareRepair(repairInformation, dormitoryId);
		return result.setResult(ResultType.SUCCESS);
	}
	
	@RequestMapping(value = "/lookRepairs", method = RequestMethod.GET)
	public ResponseResult lookRepairs(@ModelAttribute ResponseResult result, 
			@ModelAttribute("user") Student student, Date from, Date to, RepairStatus status) {
		if (student.getDormitory() == null) {
			return result.setResult(ResultType.ERR_DORMITORY);
		}
		String dormitoryId = student.getDormitory().getDormitoryId();
		List<RepairInformation> repairInformations = studentService.lookRepairs(dormitoryId, from, to, status);
		if (repairInformations == null) {
			return result.setResult(ResultType.ERR_REPAIR);
		}
		result.setResult(ResultType.SUCCESS).setResult(repairInformations);
		return result;
	}
	
	@RequestMapping(value = "/lookNotices", method = RequestMethod.GET)
	public ResponseResult lookNotices(@ModelAttribute ResponseResult result,
			@ModelAttribute("user") Student student, InformationQueryType queryType, Date from, Date to) {
		Integer buildingNum = student.getDormitory().getBuilding().getBuildingNum();
		List<Notice> notices = studentService.findNoticesBySendDate(buildingNum, from, to);
		if (notices == null || notices.size() == 0) {
			result.setResult(ResultType.ERR_NOTICE);
		}
		result.setResult(ResultType.SUCCESS).setResult(notices);
		return result;
	}
	
	@RequestMapping(value = "/lookInfo", method = RequestMethod.GET)
	public ResponseResult lookInfo(@ModelAttribute("user") Student student, @ModelAttribute ResponseResult result) {
		student.setViolationRecords(studentService.lookViolationes(student.getId(), null, null));
		Building building = student.getDormitory().getBuilding();
		building.setDorAdmins(null);
		building.setDormitories(null);
		building.setNotices(null);
		Dormitory dormitory = student.getDormitory();
		dormitory.setRepairInformations(null);
		dormitory.setStudents(null);
		dormitory.setBills(null);
		result.setResult(ResultType.SUCCESS).setResult(student);
		return result;
	}
}
