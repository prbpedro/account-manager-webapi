package com.github.prbpedro.accountmanager.webapi.dto;

import java.util.ArrayList;
import java.util.List;

public class TransferTransactionReturnDto {

	public TransferTransactionReturnDto() {
		erros = new ArrayList<String>();
	}
	
	private String message;
	
	public List<String> erros;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErros() {
		return erros;
	}

	public void setErros(List<String> erros) {
		this.erros = erros;
	}
}
