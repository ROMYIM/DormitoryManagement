package com.dormitory.controller.app;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dormitory.constant.ResultType;
import com.dormitory.entity.Administrator;
import com.dormitory.entity.DorAdmin;
import com.dormitory.entity.ResponseResult;
import com.dormitory.entity.Student;
import com.dormitory.entity.User;
import com.dormitory.service.IAdministratorService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/app/admin")
public class AppAdminController {
	
	@Resource(name = "adminService")
	private IAdministratorService administratorService;

	public AppAdminController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public String login(@ModelAttribute("gson") Gson gson, @ModelAttribute("result") ResponseResult resultUtil, 
			HttpSession session, User user) {
		Administrator administrator = administratorService.findAdminById(user.getId());
		if (administrator != null) {
			if (administrator.getPassword().equals(user.getPassword())) {
				session.setAttribute(user.getId(), administrator);
				session.setMaxInactiveInterval(300);
				resultUtil.setResult(ResultType.SUCCESS).setMessage("请之后的请求把id加入到头部");
				return gson.toJson(resultUtil);
			}
			return gson.toJson(resultUtil.setResult(ResultType.ERR_PASSWORD));
		}
		return gson.toJson(resultUtil.setResult(ResultType.NO_USER));
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	@ResponseBody
	public String register(@ModelAttribute("gson") Gson gson, @ModelAttribute("result") ResponseResult resultUtil, 
			Administrator administrator) {
		if (administratorService.findAdminById(administrator.getId()) != null) {
			return gson.toJson(resultUtil.setResult(ResultType.AL_REGISTER));
		}
		administratorService.registerAdministrator(administrator);
		return gson.toJson(resultUtil.setResult(ResultType.SUCCESS));
	}
	
	@RequestMapping(value = "/lookStudent", method = RequestMethod.GET)
	@ResponseBody
	public String lookAllStudents(@ModelAttribute("gson") Gson gson, @ModelAttribute("result") ResponseResult resultUtil) {
		List<Student> students = administratorService.findAllStudents();
		if (students == null) {
			return gson.toJson(resultUtil.setResult(ResultType.ERR_STUDENT));
		}
		resultUtil.setResult(ResultType.SUCCESS).setResult(students);
		return gson.toJson(resultUtil);
	}
	
	@RequestMapping(value = "/lookStudent/{studentId}", method = RequestMethod.GET)
	@ResponseBody
	public String lookStudentInfo(@ModelAttribute("gson") Gson gson, @ModelAttribute("result") ResponseResult resultUtil, 
			@PathVariable("studentId") String studentId) {
		Student student = administratorService.findStudentInfo(studentId);
		if (student == null) {
			return gson.toJson(resultUtil.setResult(ResultType.ERR_STUDENT));
		}
		resultUtil.setResult(ResultType.SUCCESS).setResult(student);
		return gson.toJson(resultUtil);
	}
	
	@RequestMapping(value = "/changeStudent", method = RequestMethod.POST)
	@ResponseBody
	public String changeStudentInfo(@ModelAttribute("gson") Gson gson, @ModelAttribute("result") ResponseResult resultUtil, 
			Student student) {
		administratorService.updateStudentInfo(student);
		return gson.toJson(resultUtil.setResult(ResultType.SUCCESS));
	}
	
	@RequestMapping(value = "/enterDormitory", params = {"studentId", "dormitoryId"}, method = RequestMethod.POST)
	@ResponseBody
	public String enterDormitory(@ModelAttribute("gson") Gson gson, @ModelAttribute("result") ResponseResult resultUtil, 
			@RequestParam("studentId") String studentId, @RequestParam("dormitoryId") String dormitoryId) {
		Student student = administratorService.findStudentInfo(studentId);
		if (student != null) {
			administratorService.studentEnterDormitory(student, dormitoryId);
			return gson.toJson(resultUtil.setResult(ResultType.SUCCESS));
		}
		return gson.toJson(resultUtil.setResult(ResultType.ERR_STUDENT));
	}
	
	@RequestMapping(value = "/leaveDormitory", params = "studentId", method = RequestMethod.POST)
	@ResponseBody
	public String leaveDormitory(@ModelAttribute("gson") Gson gson, @ModelAttribute("result") ResponseResult resultUtil, 
			@RequestParam("studentId") String studentId) {
		Student student = administratorService.findStudentInfo(studentId);
		if (student == null) {
			return gson.toJson(resultUtil.setResult(ResultType.ERR_STUDENT));
		}
		administratorService.studentLeaveDormitory(student);
		return gson.toJson(resultUtil.setResult(ResultType.SUCCESS));
	}
	
	@RequestMapping(value = "/lookDorAdmin", method = RequestMethod.GET)
	@ResponseBody
	public String lookAllDorAdmins(@ModelAttribute("gson") Gson gson, @ModelAttribute("result") ResponseResult resultUtil) {
		List<DorAdmin> dorAdmins = administratorService.findAllDorAdmins();
		if (dorAdmins == null) {
			return gson.toJson(resultUtil.setResult(ResultType.ERR_DORADMIN));
		}
		resultUtil.setResult(ResultType.SUCCESS).setResult(dorAdmins);
		return gson.toJson(resultUtil);
	}
	
	@RequestMapping(value = "/lookDorAdmin/{dorAdminId}", method = RequestMethod.GET)
	@ResponseBody
	public String lookDorAdmin(@ModelAttribute("gson") Gson gson, @ModelAttribute("result") ResponseResult resultUtil, 
			@PathVariable("dorAdminId") String dorAdminId) {
		DorAdmin dorAdmin = administratorService.findDorAdminById(dorAdminId);
		if (dorAdmin == null) {
			return gson.toJson(resultUtil.setResult(ResultType.ERR_DORADMIN));
		}
		resultUtil.setResult(ResultType.SUCCESS).setResult(dorAdmin);
		return gson.toJson(resultUtil);
	}
	
	@RequestMapping(value = "/changeDorAdmin", method = RequestMethod.POST)
	@ResponseBody
	public String changeDorAdminInfo(@ModelAttribute("gson") Gson gson, @ModelAttribute("result") ResponseResult resultUtil, 
			DorAdmin dorAdmin) {
		administratorService.updateDorAdmin(dorAdmin);
		return gson.toJson(resultUtil.setResult(ResultType.SUCCESS));
	}
	
	@RequestMapping(value = "/manageBuilding", params = {"dorAdminId", "buildingNum"}, method = RequestMethod.POST)
	@ResponseBody
	public String manageNewBuilding(@ModelAttribute("gson") Gson gson, @ModelAttribute("result") ResponseResult resultUtil, 
			@RequestParam("dorAdminId") String dorAdminId, @RequestParam("buildingNum") Integer buildingNum, Date manageDate) {
		DorAdmin dorAdmin = administratorService.findDorAdminById(dorAdminId);
		if (dorAdmin == null) {
			return gson.toJson(resultUtil.setResult(ResultType.ERR_DORADMIN));
		}
		if (manageDate == null) {
			administratorService.manageNewBuilding(buildingNum, dorAdmin);
		} else {
			administratorService.manageNewBuilding(buildingNum, dorAdmin, manageDate);
		}
		return gson.toJson(resultUtil.setResult(ResultType.SUCCESS));
	}

}
