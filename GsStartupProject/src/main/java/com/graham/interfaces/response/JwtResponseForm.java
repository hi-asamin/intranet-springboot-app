package com.graham.interfaces.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class JwtResponseForm {

	/** トークン */
	@JsonProperty("token")
	private String token;
	
	/** 社員ID */
	@JsonProperty("staffId")
	private int staffId;
	
	/** ログインID */
	@JsonProperty("loginId")
	private String loginId;
	
	/** 権限 */
	@JsonProperty("role")
	private List<String> role;
	
	public JwtResponseForm(String token, int staffId, String loginId, List<String> roles) {
		this.token = token;
		this.staffId = staffId;;
		this.loginId = loginId;
		this.role = roles;
	}
}
