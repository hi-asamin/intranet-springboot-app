package com.graham.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.graham.interfaces.request.StaffBasicInfoRequestForm;
import com.graham.interfaces.request.StaffRequestForm;
import com.graham.interfaces.response.StaffBasicInfoResponseForm;
import com.graham.interfaces.response.StaffResponseForm;
import com.graham.services.StaffService;

/**
 * 社員アカウント用コントローラ
 * 
 */
@RestController
@RequestMapping(value = "/staffs")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public class StaffController {
	
	@Autowired
	private StaffService staffService;
	
	/**
	 * 社員一覧取得（管理者専用）
	 * 
	 * @return staffs 社員一覧
	 */
	@GetMapping
	@ResponseBody
	public StaffResponseForm index() {
		StaffResponseForm staffs = staffService.index();
		return staffs;
	}

	/**
	 * 社員登録（管理者専用）
	 * 
	 * @param request 社員の登録情報
	 */
	@PostMapping
	@ResponseBody
	public void createStaff(@RequestBody StaffRequestForm request) {
		staffService.create(request);
	}
	
	/**
	 * 社員取得
	 * フロントで呼び出す場面がないので基本的に呼ばれることはない
	 * 
	 * @param staff_id 社員ID
	 * @return staff 社員情報
	 */
	@GetMapping("/{staffId}")
	@ResponseBody
	public StaffResponseForm showStaff(@PathVariable("staffId") int staffId) {
		StaffResponseForm staff = staffService.show(staffId);
		return staff;
	}

	/**
	 * 社員情報の更新（管理者専用）
	 * フロントで呼び出す場面がないので基本的に呼ばれることはない
	 * 
	 * @param staffId 社員ID
	 * @param request 更新情報
	 */
	@PostMapping("/{staffId}")
	@ResponseBody
	public void updateStaff(@RequestBody @Valid StaffRequestForm request,
			@PathVariable("staffId") int staffId) {
		staffService.update(staffId, request);
	}
	
	/**
	 * 社員削除（管理者専用）
	 * 
	 * @param id 退職社員ID
	 */
	@DeleteMapping("/{staffId}")
	@ResponseBody
	public void deleteStaff(@PathVariable("staffId") int staffId) {
		staffService.delete(staffId);
	}
	
	/**
	 * 社員基本情報 取得
	 * 
	 * @param staffId 社員ID
	 * @return 社員詳細情報
	 */
	@ResponseBody
	@GetMapping("/{staffId}/basic_info")
	public StaffBasicInfoResponseForm findStaffBasicInfo(@PathVariable("staffId") int staffId) {
		StaffBasicInfoResponseForm basicInfo = staffService.findStaffBasicInfo(staffId);
		return basicInfo;
	}

	/**
	 * 社員詳細情報 更新
	 * 
	 * @param staffId 社員ID
	 * @param staffDetailRequestForm 社員詳細情報フォーム
	 */
	@ResponseBody
	@PostMapping("/{staffId}/basic_info")
	public void updateStaffDetailInfo(
			@PathVariable("staffId") int staffId, 
			@Validated @RequestBody StaffBasicInfoRequestForm request, 
			BindingResult result) {
		
		// エラーがあるか確認
		for (ObjectError error : result.getAllErrors()) {
			System.out.println(error.getDefaultMessage());
		}
		
		staffService.updateStaffBasicInfo(staffId, request);
	}
}
