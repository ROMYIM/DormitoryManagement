package com.dormitory.controller.app;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
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
import org.springframework.web.bind.support.SessionStatus;

import com.dormitory.constant.ResultType;
import com.dormitory.entity.Administrator;
import com.dormitory.entity.DorAdmin;
import com.dormitory.entity.ResponseResult;
import com.dormitory.entity.Student;
import com.dormitory.entity.User;
import com.dormitory.service.IAdministratorService;
import com.dormitory.util.CookieUtil;

@Controller
@RequestMapping("/app/admin")
@ResponseBody
@SessionAttributes(value = "user", types = Administrator.class)
public class AppAdminController {
	
	@Resource(name = "adminService")
	private IAdministratorService administratorService;

	public AppAdminController() {
		// TODO Auto-generated constructor stub
	}
	
	@ModelAttribute("user")
	public Administrator initAdmin(@CookieValue(value = "id", required = false) String id, 
			@RequestParam(value = "id", required = false) String adminId) {
		if (adminId != null && adminId.length() > 0) {
			return administratorService.findAdminById(adminId);
		} else if (id != null && id.length() > 0) {
			return administratorService.findAdminById(id);
		}
		return null;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseResult login(@ModelAttribute("user") Administrator administrator, @ModelAttribute("result") ResponseResult resultUtil, 
			HttpSession session, @Valid User user, Errors errors, @RequestHeader(value = "isFirst", defaultValue = "true") Boolean isFirst, 
			HttpServletResponse response) {
		if (errors.hasErrors()) {
			return resultUtil.setResult(ResultType.ERR_LOGIN);
		}
		if (administrator != null) {
			if (administrator.getPassword().equals(user.getPassword())) {
				if (isFirst) {
					Cookie idCookie = CookieUtil.setCookie("id", administrator.getId(), 7 * 24 * 60 * 60);
					Cookie passwordCookie = CookieUtil.setCookie("password", administrator.getPassword(), 7 * 24 * 60 * 60);
					Cookie authCookie = CookieUtil.setCookie("authentication", administrator.getAuthentication().name(), 7 * 24 * 60 * 60);
					response.addCookie(idCookie);
					response.addCookie(passwordCookie);
					response.addCookie(authCookie);
				}
				session.setAttribute("user", administrator);
				session.setMaxInactiveInterval(30 * 60);
				resultUtil.setResult(ResultType.SUCCESS);
				return resultUtil;
			}
			return resultUtil.setResult(ResultType.ERR_PASSWORD);
		}
		return resultUtil.setResult(ResultType.NO_USER);
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseResult register(@ModelAttribute("result") ResponseResult resultUtil, 
			Administrator administrator) {
		if (administratorService.findAdminById(administrator.getId()) != null) {
			return resultUtil.setResult(ResultType.AL_REGISTER);
		}
		administratorService.registerAdministrator(administrator);
		return resultUtil.setResult(ResultType.SUCCESS);
	}
	
	@RequestMapping(value = "/lookStudents", method = RequestMethod.GET)
	public ResponseResult lookAllStudents(@ModelAttribute("result") ResponseResult resultUtil) {
		List<Student> students = administratorService.findAllStudents();
		resultUtil.setResult(ResultType.SUCCESS).setResult(students);
		return resultUtil;
	}
	
	@RequestMapping(value = "/lookStudent/{studentId}", method = RequestMethod.GET)
	public ResponseResult lookStudentInfo(@ModelAttribute("result") ResponseResult resultUtil, 
			@PathVariable("studentId") String studentId) {
		Student student = administratorService.findStudentInfo(studentId);
		if (student == null) {
			return resultUtil.setResult(ResultType.ERR_STUDENT);
		}
		resultUtil.setResult(ResultType.SUCCESS).setResult(student);
		return resultUtil;
	}
	
	@RequestMapping(value = "/changeStudent", method = RequestMethod.POST)
	public ResponseResult changeStudentInfo(@ModelAttribute("result") ResponseResult resultUtil, 
			Student student) {
		administratorService.updateStudentInfo(student);
		return resultUtil.setResult(ResultType.SUCCESS);
	}
	
	@RequestMapping(value = "/enterDormitory", method = RequestMethod.POST)
	public ResponseResult enterDormitory(@ModelAttribute("result") ResponseResult resultUtil, 
			@RequestParam("studentId") String studentId, @RequestParam("dormitoryId") String dormitoryId) {
		Student student = administratorService.findStudentInfo(studentId);
		if (student != null) {
			boolean flag = administratorService.studentEnterDormitory(student, dormitoryId);
			if (flag) {
				return resultUtil.setResult(ResultType.SUCCESS);
			}
			return resultUtil.setResult(ResultType.ERR_DORMITORY);
		}
		return resultUtil.setResult(ResultType.ERR_STUDENT);
	}
	
	@RequestMapping(value = "/leaveDormitory", method = RequestMethod.POST)
	public ResponseResult leaveDormitory(@ModelAttribute("result") ResponseResult resultUtil, @RequestParam("studentId") String studentId) {
		Student student = administratorService.findStudentInfo(studentId);
		if (student == null) {
			return resultUtil.setResult(ResultType.ERR_STUDENT);
		}
		administratorService.studentLeaveDormitory(student);
		return resultUtil.setResult(ResultType.SUCCESS);
	}
	
	@RequestMapping(value = "/lookDorAdmins", method = RequestMethod.GET)
	public ResponseResult lookAllDorAdmins(@ModelAttribute("result") ResponseResult resultUtil) {
		List<DorAdmin> dorAdmins = administratorService.findAllDorAdmins();
		if (dorAdmins == null) {
			return resultUtil.setResult(ResultType.ERR_DORADMIN);
		}
		resultUtil.setResult(ResultType.SUCCESS).setResult(dorAdmins);
		return resultUtil;
	}
	
	@RequestMapping(value = "/lookDorAdmin/{dorAdminId}", method = RequestMethod.GET)
	public ResponseResult lookDorAdmin( @ModelAttribute("result") ResponseResult resultUtil, @PathVariable("dorAdminId") String dorAdminId) {
		DorAdmin dorAdmin = administratorService.findDorAdminById(dorAdminId);
		if (dorAdmin == null) {
			return resultUtil.setResult(ResultType.ERR_DORADMIN);
		}
		resultUtil.setResult(ResultType.SUCCESS).setResult(dorAdmin);
		return resultUtil;
	}
	
	@RequestMapping(value = "/changeDorAdmin", method = RequestMethod.POST)
	public ResponseResult changeDorAdminInfo(@ModelAttribute("result") ResponseResult resultUtil, DorAdmin dorAdmin) {
		administratorService.updateDorAdmin(dorAdmin);
		return resultUtil.setResult(ResultType.SUCCESS);
	}
	
	@RequestMapping(value = "/manageBuilding", method = RequestMethod.POST)
	public ResponseResult manageNewBuilding(@ModelAttribute("result") ResponseResult resultUtil, 
			@RequestParam("dorAdminId") String dorAdminId, @RequestParam("buildingNum") Integer buildingNum, Date manageDate) {
		DorAdmin dorAdmin = administratorService.findDorAdminById(dorAdminId);
		if (dorAdmin == null) {
			return resultUtil.setResult(ResultType.ERR_DORADMIN);
		}
		if (manageDate == null) {
			administratorService.manageNewBuilding(buildingNum, dorAdmin);
		} else {
			administratorService.manageNewBuilding(buildingNum, dorAdmin, manageDate);
		}
		return resultUtil.setResult(ResultType.SUCCESS);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseResult logout(@ModelAttribute("result") ResponseResult result, SessionStatus status, HttpServletResponse response) {
		status.setComplete();
		if (status.isComplete()) {
			Cookie idCookie = CookieUtil.setCookie("id", null, 7 * 24 * 60 * 60);
			Cookie passwordCookie = CookieUtil.setCookie("password", null, 7 * 24 * 60 * 60);
			Cookie authCookie = CookieUtil.setCookie("authentication", null, 7 * 24 * 60 * 60);
			response.addCookie(idCookie);
			response.addCookie(passwordCookie);
			response.addCookie(authCookie);
			return result.setResult(ResultType.SUCCESS);
		} else {
			return result.setResult(ResultType.ERR_UNKONOWN);
		}
	}
}
