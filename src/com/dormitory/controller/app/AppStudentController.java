package com.dormitory.controller.app;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/app/student")
@SessionAttributes(value = {"student"}, types = {Student.class})
public class AppStudentController {

	@Resource(name = "studentService")
	private IStudentService studentService;
	
	public AppStudentController() {
		// TODO Auto-generated constructor stub
	}
	
	@ModelAttribute("student")
	public Student initStudent(@CookieValue(value = "id", required = false) String id, @RequestParam(value = "id", required = false) String studentId) {
		if (id != null && id.length() > 0) {
			return studentService.findStudentById(id);
		} else if (studentId != null && studentId.length() > 0) {
			return studentService.findStudentById(studentId);
		}
		return null;
	}
	
	@RequestMapping(value = "/login", headers = "isFirst", method = RequestMethod.POST)
	@ResponseBody
	public String login(@ModelAttribute Gson gson, @ModelAttribute ResponseResult result, @ModelAttribute Student student,
			@RequestHeader("isFirst") Boolean isFirst, HttpServletResponse response, User user, HttpSession session) {
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
				session.setAttribute("student", student);
				session.setMaxInactiveInterval(30 * 60);
				return gson.toJson(result.setResult(ResultType.SUCCESS));
			}
			return gson.toJson(result.setResult(ResultType.ERR_PASSWORD));
		}
		return gson.toJson(result.setResult(ResultType.NO_USER));
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public String register(@ModelAttribute Gson gson, @ModelAttribute ResponseResult result, Student student) {
		if (studentService.findStudentById(student.getId()) != null) {
			return gson.toJson(result.setResult(ResultType.AL_REGISTER));
		}
		studentService.registerStudent(student);
		return gson.toJson(result.setResult(ResultType.SUCCESS));
	}
	
	@RequestMapping(value = "/payBills/{billType}", params = "bills", method = RequestMethod.POST)
	@ResponseBody
	public String payBills(@ModelAttribute Gson gson, @PathVariable BillType billType, @ModelAttribute Student student,
			@ModelAttribute ResponseResult result, @RequestParam(defaultValue = "0") Float bills) {
		if (student == null) {
			return gson.toJson(result.setResult(ResultType.ERR_LOGIN));
		}
		Dormitory dormitory = student.getDormitory();
		if (dormitory == null) {
			return gson.toJson(result.setResult(ResultType.ERR_DORMITORY));
		}
		studentService.payBill(dormitory.getDormitoryId(), bills, billType);
		return gson.toJson(result.setResult(ResultType.SUCCESS));
	}
	
	@RequestMapping(value = "/lookBill", method = RequestMethod.GET)
	@ResponseBody
	public String lookBill(@ModelAttribute Gson gson, @ModelAttribute ResponseResult result, @ModelAttribute Student student, 
			Model model) {
		if (student == null) {
			return gson.toJson(result.setResult(ResultType.ERR_LOGIN));
		}
		if (student.getDormitory() == null) {
			return gson.toJson(result.setResult(ResultType.ERR_DORMITORY));
		}
		String dormitoryId = student.getDormitory().getDormitoryId();
		Map<String, String> billMap = studentService.lookBalance(dormitoryId);
		model.addAttribute("�����", dormitoryId);
		model.addAllAttributes(billMap);
		return gson.toJson(billMap);
	}
	
	@RequestMapping(value = "/lookBills", method = RequestMethod.GET)
	@ResponseBody
	public String lookBills(@ModelAttribute ResponseResult result, @ModelAttribute Student student, 
			BillType type, Date from, Date to) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").setPrettyPrinting().create();
		if (student == null) {
			return gson.toJson(result.setResult(ResultType.ERR_LOGIN));
		}
		if (student.getDormitory() == null) {
			return gson.toJson(result.setResult(ResultType.ERR_DORMITORY));
		}
		String dormitoryId = student.getDormitory().getDormitoryId();
		List<Bill> bills = studentService.lookBills(dormitoryId, type, from, to);
		result.setResult(ResultType.SUCCESS).setResult(bills);
		return gson.toJson(result);
	}
	
	@RequestMapping(value = "/lookViolations", method = RequestMethod.GET)
	@ResponseBody
	public String lookViolation(@ModelAttribute Gson gson, @ModelAttribute ResponseResult result, 
			@ModelAttribute Student student, Date from, Date to) {
		if (student != null) {
			List<ViolationRecord> violationRecords = studentService.lookViolationes(student.getId(), from, to);
			if (violationRecords != null) {
				result.setResult(ResultType.SUCCESS).setResult(violationRecords);
				return gson.toJson(result);
			}
			return gson.toJson(result.setResult(ResultType.ERR_VIOLATION));
		}
		return gson.toJson(result.setResult(ResultType.ERR_LOGIN));
	}
	
	@RequestMapping(value = "/declareRepair", method = RequestMethod.POST)
	@ResponseBody
	public String declareRepair(@ModelAttribute Gson gson, @ModelAttribute ResponseResult result, 
			@ModelAttribute Student student, RepairInformation repairInformation) {
		if (student == null) {
			return gson.toJson(result.setResult(ResultType.ERR_LOGIN));
		}
		if (student.getDormitory() == null) {
			return gson.toJson(result.setResult(ResultType.ERR_DORMITORY));
		}
		String dormitoryId = student.getDormitory().getDormitoryId();
		studentService.declareRepair(repairInformation, dormitoryId);
		return gson.toJson(result.setResult(ResultType.SUCCESS));
	}
	
	@RequestMapping(value = "/lookRepairs", method = RequestMethod.GET)
	@ResponseBody
	public String lookRepairs(@ModelAttribute Gson gson, @ModelAttribute ResponseResult result, 
			@ModelAttribute Student student, Date from, Date to, RepairStatus status) {
		if (student == null) {
			return gson.toJson(result.setResult(ResultType.ERR_LOGIN));
		}
		if (student.getDormitory() == null) {
			return gson.toJson(result.setResult(ResultType.ERR_DORMITORY));
		}
		String dormitoryId = student.getDormitory().getDormitoryId();
		List<RepairInformation> repairInformations = studentService.lookRepairs(dormitoryId, from, to, status);
		if (repairInformations == null) {
			return gson.toJson(result.setResult(ResultType.ERR_REPAIR));
		}
		result.setResult(ResultType.SUCCESS).setResult(repairInformations);
		return gson.toJson(result);
	}
	
	@RequestMapping(value = "/lookNotices", method = RequestMethod.GET)
	@ResponseBody
	public String lookNotices(@ModelAttribute Gson gson, @ModelAttribute ResponseResult resultUtil,
			@ModelAttribute Student student, InformationQueryType queryType, Date from, Date to) {
		Integer buildingNum = student.getDormitory().getBuilding().getBuildingNum();
		List<Notice> notices = studentService.findNoticesBySendDate(buildingNum, from, to);
		if (notices == null || notices.size() == 0) {
			return gson.toJson(resultUtil.setResult(ResultType.ERR_NOTICE));
		}
		resultUtil.setResult(ResultType.SUCCESS).setResult(notices);
		return gson.toJson(resultUtil);
	}
	
	@RequestMapping(value = "/lookInfo", method = RequestMethod.GET)
	@ResponseBody
	public String lookInfo(@ModelAttribute Student student, @ModelAttribute Gson gson, 
			@ModelAttribute ResponseResult result) {
		if (student == null) {
			return gson.toJson(result.setResult(ResultType.ERR_LOGIN));
		}
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
		return gson.toJson(result);
	}
}