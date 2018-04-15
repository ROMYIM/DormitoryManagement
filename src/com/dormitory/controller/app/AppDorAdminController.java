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

import com.dormitory.constant.InformationQueryType;
import com.dormitory.constant.ResultType;
import com.dormitory.entity.DorAdmin;
import com.dormitory.entity.Dormitory;
import com.dormitory.entity.Message;
import com.dormitory.entity.Notice;
import com.dormitory.entity.ResponseResult;
import com.dormitory.entity.Student;
import com.dormitory.entity.User;
import com.dormitory.entity.ViolationRecord;
import com.dormitory.service.IDorAdminService;
import com.dormitory.service.IMessageService;
import com.dormitory.util.CookieUtil;

@Controller
@RequestMapping("/app/dorAdmin")
@ResponseBody
@SessionAttributes(value = "user", types = DorAdmin.class)
public class AppDorAdminController {

	@Resource(name = "dorAdminService")
	private IDorAdminService dorAdminService;
	
	@Resource(name = "messageService")
	private IMessageService messageService;
	
	public AppDorAdminController() {
		// TODO Auto-generated constructor stub
	}
	
	@ModelAttribute("user")
	public DorAdmin initDorAdmin(@CookieValue(value = "id", required = false) String id,
			@RequestParam(value = "id", required = false) String dorAdminId) {
		if (dorAdminId != null && dorAdminId.length() > 0) {
			return dorAdminService.findDorAdminById(dorAdminId);
		} else if (id != null && id.length() > 0) {
			return dorAdminService.findDorAdminById(id);
		}
		return null;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseResult login(@ModelAttribute("user") DorAdmin dorAdmin, @ModelAttribute("result") ResponseResult result, 
			@RequestHeader(value = "isFirst", defaultValue = "true") Boolean isFrist, @Valid User user, Errors errors, 
			HttpSession session, HttpServletResponse response) {
		if (errors.hasErrors()) {
			return result.setResult(ResultType.ERR_LOGIN);
		}
		if (dorAdmin != null) {
			if (dorAdmin.getPassword().equals(user.getPassword())) {
				if (isFrist) {
					Cookie idCookie = CookieUtil.setCookie("id", dorAdmin.getId(), 7 * 24 * 60 * 60);
					Cookie passwordCookie = CookieUtil.setCookie("password", dorAdmin.getPassword(), 7 * 24 * 60 * 60);
					Cookie authCookie = CookieUtil.setCookie("authentication", dorAdmin.getAuthentication().name(), 7 * 24 * 60 * 60);
					response.addCookie(idCookie);
					response.addCookie(passwordCookie);
					response.addCookie(authCookie);
				}
				session.setAttribute("user", dorAdmin);
				session.setMaxInactiveInterval(30 * 60);
				return result.setResult(ResultType.SUCCESS);
			}
			return result.setResult(ResultType.ERR_PASSWORD);
		}
		return result.setResult(ResultType.NO_USER);
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseResult register(DorAdmin dorAdmin, @ModelAttribute("result") ResponseResult result) {
		if (dorAdminService.findDorAdminById(dorAdmin.getId()) != null) {
			return result.setResult(ResultType.AL_REGISTER);
		}
		dorAdminService.registerDorAdmin(dorAdmin);
		return result.setResult(ResultType.SUCCESS);
	}
	
	@RequestMapping(value = "/lookStudents/{dormitoryId}", method = RequestMethod.GET)
	public ResponseResult lookStudentsByDormitory(@PathVariable("dormitoryId") String dormitoryId, 
			@ModelAttribute("result") ResponseResult resultUtil) {
		List<Student> students = dorAdminService.findStudentsByDormitory(dormitoryId);
		if (students != null && students.size() > 0) {
			resultUtil.setResult(ResultType.SUCCESS).setResult(students);
			return resultUtil;
		} else {
			return resultUtil.setResult(ResultType.ERR_STUDENT);
		}
	}
	
	@RequestMapping(value = "/lookStudent/{studentId}", method = RequestMethod.GET)
	public ResponseResult lookStudentById(@PathVariable("studentId") String studentId, 
			@ModelAttribute("result") ResponseResult resultUtil) {
		Student student = dorAdminService.findStudentById(studentId);
		if (student != null) {
			student.setViolationRecords(null);
			student.setPassword(null);
			resultUtil.setResult(ResultType.SUCCESS).setResult(student);
		} else {
			resultUtil.setResult(ResultType.ERR_STUDENT);
		}
		return resultUtil;
	}

	@RequestMapping(value = "/lookDormitory/{studentId}", method = RequestMethod.GET)
	public ResponseResult lookDormitoryByStudent(@PathVariable("studentId") String studentId, 
			@ModelAttribute("result") ResponseResult resultUtil) {
		
		Dormitory dormitory = dorAdminService.findDormitoryByStudent(studentId);
		if (dormitory != null) {
			resultUtil.setResult(ResultType.SUCCESS).setResult(dormitory);
		} else {
			resultUtil.setResult(ResultType.ERR_DORMITORY);
		}
		return resultUtil;
	}
	
	@RequestMapping(value = "/addViolation/{studentId}", method = RequestMethod.POST)
	public ResponseResult addViolation(@ModelAttribute("user") DorAdmin dorAdmin, @ModelAttribute("result") ResponseResult resultUtil, 
			@PathVariable("studentId") String studentId, ViolationRecord violationRecord) {
		dorAdminService.addViolationRecord(studentId, violationRecord, dorAdmin);
		return resultUtil.setResult(ResultType.SUCCESS);
	}
	
	@RequestMapping(value = "/lookViolation", method = RequestMethod.GET)
	public ResponseResult lookViolation(@ModelAttribute("user") DorAdmin dorAdmin, @ModelAttribute("result") ResponseResult resultUtil,
			@RequestParam(value = "studentId", required = false) String studentId, Date from, Date to) {
		List<ViolationRecord> violationRecords = null;
		if (studentId != null) {
			violationRecords = dorAdminService.findViolationByStudent(dorAdmin.getId(), studentId, from, to);
		} else {
			violationRecords = dorAdminService.findViolationRecord(dorAdmin.getId(), from, to);
		}
		if (violationRecords != null && violationRecords.size() > 0) {
			resultUtil.setResult(ResultType.SUCCESS).setResult(violationRecords);
			return resultUtil;
		}
		return resultUtil.setResult(ResultType.ERR_VIOLATION);
	}
	
	@RequestMapping(value = "/removeViolation/{violationId}", method = RequestMethod.POST)
	public ResponseResult removeViolation(@ModelAttribute("result") ResponseResult resultUtil, 
			@PathVariable("violationId") Integer violationId) {
		if (violationId == null) {
			return resultUtil.setResult(ResultType.ERR_VIOLATION);
		}
		dorAdminService.removeViolationRecord(violationId);
		return resultUtil.setResult(ResultType.SUCCESS);
	}
	
	@RequestMapping(value = "/addNotice", method = RequestMethod.POST)	
	public ResponseResult addNotice(@ModelAttribute("user") DorAdmin dorAdmin, @ModelAttribute("result") ResponseResult resultUtil, Notice notice) {
		if (dorAdmin.getManageBuilding() == null) {
			return resultUtil.setResult(ResultType.ERR_QUERY);
		}
		dorAdminService.declareNotice(dorAdmin.getId(), notice);
		return resultUtil.setResult(ResultType.SUCCESS);
		
	}
	
	@RequestMapping(value = "/lookNotices/{queryType}", method = RequestMethod.GET)
	public ResponseResult lookNotices(@ModelAttribute("user") DorAdmin dorAdmin, @ModelAttribute("result") ResponseResult resultUtil, 
			@PathVariable("queryType") InformationQueryType type, Date from, Date to) {
		if (dorAdmin.getManageBuilding() == null) {
			return resultUtil.setResult(ResultType.ERR_QUERY);
		}
		List<Notice> notices = null;
		switch (type) {
		case SEND:
			notices = dorAdminService.findNoticesBySendDate(dorAdmin.getId(), from, to);
			break;
		case DEADLINE:
			notices = dorAdminService.findNoticesByDeadline(dorAdmin.getId(), from, to);
			break;
		case BETWEEN_SD:
			notices = dorAdminService.findNotices(dorAdmin.getId(), from, to);
		default:
			notices = dorAdminService.findNoticesBySendDate(dorAdmin.getId(), null, null);
			break;
		}
		resultUtil.setResult(ResultType.SUCCESS).setResult(notices);
		return resultUtil;
	}
	
	@RequestMapping(value = "getMessageSender", method = RequestMethod.GET)
	public ResponseResult getMessageSenders(@ModelAttribute("user") DorAdmin dorAdmin, @ModelAttribute("result") ResponseResult result) {
		List<Message> messages =  messageService.findUnSendMessagesGroupBySender(dorAdmin.getId());
		result.setResult(ResultType.SUCCESS).setResult(messages);
		return result;
	}
	
	@RequestMapping(value = "/getMessage", method = RequestMethod.GET)
	public ResponseResult getMessage(@ModelAttribute("user") DorAdmin dorAdmin, @ModelAttribute("result") ResponseResult result, 
			String receiverId, String sendDate) {
		List<Message> messages = messageService.findMessagsBySendDate(dorAdmin.getId(), receiverId, sendDate);
		result.setResult(ResultType.SUCCESS).setResult(messages);
		return result;
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
