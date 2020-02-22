package com.graham.controllers;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.graham.common.GrahamHttpStatus;
import com.graham.exception.GrahamError;
import com.graham.exception.GrahamException;
import com.graham.interfaces.request.AttendanceRequestForm;
import com.graham.interfaces.response.AttendanceResponseForm;
import com.graham.services.AttendanceService;
import com.graham.services.StaffService;

/**
 * 勤怠情報管理コントローラ
 * 
 */
@RestController
@RequestMapping(value = "/api/v1/attendances")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public class AttendanceController {

	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	private StaffService staffService;
	@Autowired
    private MessageSource messageSource;
	private static final Logger LOGGER=LoggerFactory.getLogger(AttendanceController.class);
	
	/**
	 * 勤怠情報一覧取得（社員の対象月1ヶ月分）
	 * 
	 * @param authentication 認証情報
	 * @param yearMonth 対象年月
	 * @return attendances 勤怠情報
	 */
	@PostMapping
	@ResponseBody
	public AttendanceResponseForm index(
			Authentication authentication,
			@RequestBody AttendanceRequestForm request,
			@RequestParam("yearMonth") String yearMonth) {
		
		LOGGER.info("BEGIN AttendanceController index");
		int staffId = request.getStaffId();
		// 別社員の情報だった場合
		if (!staffService.isCorrectStaff(authentication.getName(), staffId)) {
			String message = messageSource.getMessage(
					"error.E_GSOL0006", new String[]{authentication.getName()}, Locale.JAPANESE);
			LOGGER.error(message);
			GrahamError err = new GrahamError(GrahamHttpStatus.NOT_FOUND, "GSOL0001", message);
			throw new GrahamException(err);
		}
		AttendanceResponseForm attendances = attendanceService.attendanceIndex(staffId, yearMonth);
		LOGGER.info("SUCCESS get attendance list");
		return attendances;
	}
	
	@PutMapping
	@ResponseBody
	public void update(
			@RequestParam("yearMonth") String yearMonth,
			@RequestBody List<AttendanceRequestForm> requests) {
		
		LOGGER.info("CALLED AttendanceController update");
		attendanceService.updateAttendance(requests.get(0).getStaffId(), yearMonth, requests);
		LOGGER.info("SUCCESS update attendance");
	}
}
