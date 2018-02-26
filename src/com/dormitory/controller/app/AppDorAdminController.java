package com.dormitory.controller.app;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dormitory.constant.InformationQueryType;
import com.dormitory.constant.ResultType;
import com.dormitory.entity.DorAdmin;
import com.dormitory.entity.Dormitory;
import com.dormitory.entity.Notice;
import com.dormitory.entity.ResponseResult;
import com.dormitory.entity.Student;
import com.dormitory.entity.User;
import com.dormitory.entity.ViolationRecord;
import com.dormitory.service.IDorAdminService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/app/dorAdmin")
public class AppDorAdminController {

	@Resource(name = "dorAdminService")
	private IDorAdminService dorAdminService;
	
	public AppDorAdminController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public String login(User user, HttpSession session, @ModelAttribute("gson") Gson gson, 
			@ModelAttribute("result") ResponseResult resultUtil) {
		DorAdmin dorAdmin = dorAdminService.findDorAdminById(user.getId());
		if (dorAdmin != null) {
			if (dorAdmin.getPassword().equals(user.getPassword())) {
				session.setAttribute(user.getId(), dorAdmin);
				session.setMaxInactiveInterval(300);
				resultUtil.setCode(ResultType.SUCCESS.getCode());
				resultUtil.setResult(user.getId());
				resultUtil.setMessage("登录成功。请之后的操作都加入头");
				return gson.toJson(resultUtil);
			}
			return gson.toJson(resultUtil.setResult(ResultType.ERR_PASSWORD));
		}
		return gson.toJson(resultUtil.setResult(ResultType.NO_USER));
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public String register(@ModelAttribute("gson") Gson gson, DorAdmin dorAdmin, @ModelAttribute("result") ResponseResult resultUtil) {
		if (dorAdminService.findDorAdminById(dorAdmin.getId()) != null) {
			return gson.toJson(resultUtil.setResult(ResultType.AL_REGISTER));
		}
		dorAdminService.registerDorAdmin(dorAdmin);
		return gson.toJson(resultUtil.setResult(ResultType.SUCCESS));
	}
	
	@RequestMapping(value = "/lookStudents/{dormitoryId}", method = RequestMethod.GET)
	@ResponseBody
	public String lookStudentsByDormitory(@ModelAttribute("gson") Gson gson, @PathVariable("dormitoryId") String dormitoryId, 
			@ModelAttribute("result") ResponseResult resultUtil) {
		List<Student> students = dorAdminService.findStudentsByDormitory(dormitoryId);
		if (students != null && students.size() > 0) {
			resultUtil.setCode(ResultType.SUCCESS.getCode());
			resultUtil.setResult(students);
			resultUtil.setMessage(ResultType.SUCCESS.getMessage());
			return gson.toJson(resultUtil);
		}
		return gson.toJson(resultUtil.setResult(ResultType.ERR_STUDENT));
	}
	
	@RequestMapping(value = "/lookStudent/{studentId}", method = RequestMethod.GET)
	@ResponseBody
	public String lookStudentById(@ModelAttribute("gson") Gson gson, @PathVariable("studenId") String studentId, @ModelAttribute("resutlt") ResponseResult resultUtil) {
		Student student = dorAdminService.findStudentById(studentId);
		if (student != null) {
			resultUtil.setCode(ResultType.SUCCESS.getCode());
			resultUtil.setResult(student);
			resultUtil.setMessage(ResultType.SUCCESS.getMessage());
			return gson.toJson(resultUtil);
		}
		return gson.toJson(resultUtil.setResult(ResultType.ERR_STUDENT));
	}

	@RequestMapping(value = "/lookDormitory/{studentId}", method = RequestMethod.GET)
	@ResponseBody
	public String lookDormitoryByStudent(@ModelAttribute("gson") Gson gson, @PathVariable("studentId") String studentId, 
			@ModelAttribute("result") ResponseResult resultUtil) {
		Dormitory dormitory = dorAdminService.findDormitoryByStudent(studentId);
		if (dormitory != null) {
			resultUtil.setCode(ResultType.SUCCESS.getCode());
			resultUtil.setResult(dormitory);
			resultUtil.setMessage(ResultType.SUCCESS.getMessage());
			return gson.toJson(resultUtil);
		}
		return gson.toJson(resultUtil.setResult(ResultType.ERR_DORMITORY));
	}
	
	@RequestMapping(value = "/addViolation/{studentId}", headers = "id", method = RequestMethod.POST)
	@ResponseBody
	public String addViolation(@ModelAttribute("gson") Gson gson, @ModelAttribute("result") ResponseResult resultUtil, 
			@PathVariable("studentId") String studentId, @RequestHeader("id") String id, HttpSession session, 
			ViolationRecord violationRecord) {
		DorAdmin dorAdmin = (DorAdmin) session.getAttribute(id);
		dorAdminService.addViolationRecord(studentId, violationRecord, dorAdmin);
		return gson.toJson(resultUtil.setResult(ResultType.SUCCESS));
	}
	
	@RequestMapping(value = "/lookVioaltion", params = "studentId", headers = "id", method = RequestMethod.GET)
	@ResponseBody
	public String lookViolation(@ModelAttribute("gson") Gson gson, @ModelAttribute("result") ResponseResult resultUtil,
			@RequestHeader("id") String id, @RequestParam("studentId") String studentId, Date from, Date to) {
		List<ViolationRecord> violationRecords = null;
		if (studentId == null) {
			violationRecords = dorAdminService.findViolationByStudent(id, studentId, from, to);
		} else {
			violationRecords = dorAdminService.findViolationRecord(id, from, to);
		}
		if (violationRecords != null && violationRecords.size() > 0) {
			resultUtil.setCode(ResultType.SUCCESS.getCode());
			resultUtil.setResult(violationRecords);
			resultUtil.setMessage(ResultType.SUCCESS.getMessage());
			return gson.toJson(resultUtil);
		}
		return gson.toJson(resultUtil.setResult(ResultType.ERR_VIOLATION));
	}
	
	@RequestMapping(value = "/removeViolation/{violationId}", method = RequestMethod.POST)
	@ResponseBody
	public String removeViolation(@ModelAttribute("gson") Gson gson, @ModelAttribute("result") ResponseResult resultUtil, 
			@PathVariable("violationId") Integer violationId) {
		dorAdminService.removeViolationRecord(violationId);
		return gson.toJson(resultUtil.setResult(ResultType.SUCCESS));
	}
	
	@RequestMapping(value = "/addNotice", headers = "id", method = RequestMethod.POST)	
	@ResponseBody
	public String addNotice(@ModelAttribute("gson") Gson gson, @ModelAttribute("result") ResponseResult resultUtil, 
			@RequestHeader("id") String dorAdminId, Notice notice) {
		dorAdminService.declareNotice(dorAdminId, notice);
		return gson.toJson(resultUtil.setResult(ResultType.SUCCESS));
		
	}
	
	@RequestMapping(value = "/lookNotice/{queryType}", headers = "id", method = RequestMethod.GET)
	@ResponseBody
	public String lookNotices(@ModelAttribute("gson") Gson gson, @ModelAttribute("result") ResponseResult resultUtil, 
			@RequestHeader("id") String dorAdminId, @PathVariable("queryType") InformationQueryType type, Date from, Date to) {
		List<Notice> notices = null;
		switch (type) {
		case SEND:
			notices = dorAdminService.findNoticesBySendDate(dorAdminId, from, to);
			break;
		case DEADLINE:
			notices = dorAdminService.findNoticesByDeadline(dorAdminId, from, to);
			break;
		case BETWEEN_SD:
			notices = dorAdminService.findNotices(dorAdminId, from, to);
		default:
			break;
		}
		resultUtil.setCode(ResultType.SUCCESS.getCode());
		resultUtil.setResult(notices);
		resultUtil.setMessage(ResultType.SUCCESS.getMessage());
		return gson.toJson(resultUtil);
	}
}
