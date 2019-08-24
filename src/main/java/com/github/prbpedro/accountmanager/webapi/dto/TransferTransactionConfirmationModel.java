package com.github.prbpedro.accountmanager.webapi.dto;

import java.math.BigDecimal;

public class TransferTransactionConfirmationModel {
	
	private String senderId;
	
	private String beneficiaryId;
	
	private String currencyCode;
	
	private BigDecimal value;
	
	private String status;

	public TransferTransactionConfirmationModel() {
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(String beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
