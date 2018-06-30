package com.amazonaws.lambda.tdtech.authentication;

public class AuthenticateUserResponse {
	protected Integer userId;
	protected String openIdToken;
	protected String status;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getOpenIdToken() {
		return openIdToken;
	}

	public void setOpenIdToken(String openIdToken) {
		this.openIdToken = openIdToken;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
