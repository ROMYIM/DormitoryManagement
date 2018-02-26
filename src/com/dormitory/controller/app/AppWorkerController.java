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
import org.springframework.web.bind.annotation.ResponseBody;

import com.dormitory.constant.RepairStatus;
import com.dormitory.constant.ResultType;
import com.dormitory.entity.MaintenanceWorker;
import com.dormitory.entity.RepairInformation;
import com.dormitory.entity.ResponseResult;
import com.dormitory.entity.User;
import com.dormitory.service.IWorkerService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/app/worker")
public class AppWorkerController {
	
	@Resource(name = "workerService")
	private IWorkerService workerService;

	public AppWorkerController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public String login(@ModelAttribute("gson") Gson gson, @ModelAttribute("result") ResponseResult resultUtil, 
			User user, HttpSession session) {
		MaintenanceWorker worker = workerService.findWorkerById(user.getId());
		if (worker != null) {
			if (worker.getPassword().equals(user.getPassword())) {
				session.setAttribute(user.getId(), worker);
				session.setMaxInactiveInterval(300);
				resultUtil.setResult(ResultType.SUCCESS).setMessage("请之后的请求在头部加入工号");
				return gson.toJson(resultUtil);
			}
			return gson.toJson(resultUtil.setResult(ResultType.ERR_PASSWORD));
		}
		return gson.toJson(resultUtil.setResult(ResultType.NO_USER));
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public String register(@ModelAttribute("gson") Gson gson, @ModelAttribute("result") ResponseResult resultUtil, 
			MaintenanceWorker worker) {
		if (workerService.findWorkerById(worker.getId()) != null) {
			return gson.toJson(resultUtil.setResult(ResultType.AL_REGISTER));
		}
		workerService.registerWorker(worker);
		return gson.toJson(resultUtil.setResult(ResultType.SUCCESS));
	}
	
	@RequestMapping(value = "/lookRepairs", headers = "id", method = RequestMethod.GET)
	@ResponseBody
	public String lookRepairs(@ModelAttribute("gson") Gson gson, @ModelAttribute("result") ResponseResult resultUtil, 
			@RequestHeader("id") String id, HttpSession session, Date from, Date to) {
		MaintenanceWorker worker = (MaintenanceWorker) session.getAttribute(id);
		List<RepairInformation> repairInformations = workerService.findRepairs(worker.getWorkerType(), from, to);
		if (repairInformations != null) {
			resultUtil.setResult(ResultType.SUCCESS).setResult(repairInformations);
			return gson.toJson(resultUtil);
		}
		return gson.toJson(resultUtil.setResult(ResultType.ERR_REPAIR));
	}
	
	@RequestMapping(value = "/lookRepairs/status", headers = "id", method = RequestMethod.GET)
	@ResponseBody
	public String lookRepairsByWorker(@ModelAttribute("gson") Gson gson, @ModelAttribute("result") ResponseResult resultUti, 
			@RequestHeader("id") String id, RepairStatus status, Date from, Date to) {
		List<RepairInformation> repairInformations = workerService.findRepairsByWorker(id, status, from, to);
		if (repairInformations != null) {
			resultUti.setResult(ResultType.SUCCESS).setResult(repairInformations);
			return gson.toJson(resultUti);
		}
		return gson.toJson(resultUti.setResult(ResultType.ERR_REPAIR));
	}
	
	@RequestMapping(value = "/takeRepair/{repairId}", headers = "id", method = RequestMethod.POST)
	@ResponseBody
	public String takeRepair(@ModelAttribute("gson") Gson gson, @ModelAttribute("result") ResponseResult resultUtil, 
			@RequestHeader("id") String id, @PathVariable("repairId") Integer repairId, HttpSession session) {
		MaintenanceWorker worker = (MaintenanceWorker) session.getAttribute(id);
		workerService.takeRepair(worker, repairId);
		return gson.toJson(resultUtil.setResult(ResultType.SUCCESS));
	}
	
	@RequestMapping(value = "/finishRepair/{repairId}", headers = "id", method = RequestMethod.POST)
	@ResponseBody
	public String finishRepair(@ModelAttribute("gson") Gson gson, @ModelAttribute("result") ResponseResult resultUtil, 
			@RequestHeader("id") String id, @PathVariable("repairId") Integer repairId, HttpSession session) {
		MaintenanceWorker worker = (MaintenanceWorker) session.getAttribute(id);
		workerService.finishRepair(worker, repairId);
		return gson.toJson(resultUtil.setResult(ResultType.SUCCESS));
	}

}
