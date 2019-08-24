package com.github.prbpedro.accountmanager.webapi.dto;

import java.util.List;

import com.github.prbpedro.accountmanager.domain.enums.TransferTransactionStatusEnum;
import com.github.prbpedro.accountmanager.domain.services.dto.TransferTransactionReturnDto;

public class TransferTransactioReturnModel {

	private TransferTransactionStatusEnum status;
	
	private List<String> messages;
	
	private TransferTransactionConfirmationModel transferTransactionConfirmation;
	
	public TransferTransactioReturnModel() {
	}
	
	public TransferTransactioReturnModel(TransferTransactionReturnDto dto, String currencyCode) {
		setStatus(dto.getStatus());
		setMessages(dto.getMessages());
		
		if(dto.getTransferInformation()!=null) {
			setTransferTransactionConfirmation(new TransferTransactionConfirmationModel());
			getTransferTransactionConfirmation().setSenderId(dto.getTransferInformation().getSenderId());
			getTransferTransactionConfirmation().setBeneficiaryId(dto.getTransferInformation().getBeneficiaryId());
			getTransferTransactionConfirmation().setCurrencyCode(currencyCode);
			getTransferTransactionConfirmation().setValue(dto.getTransferInformation().getValue());
			getTransferTransactionConfirmation().setStatus(dto.getTransferInformation().getStatus());
		}
	}

	public TransferTransactionStatusEnum getStatus() {
		return status;
	}

	public void setStatus(TransferTransactionStatusEnum status) {
		this.status = status;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public TransferTransactionConfirmationModel getTransferTransactionConfirmation() {
		return transferTransactionConfirmation;
	}

	public void setTransferTransactionConfirmation(TransferTransactionConfirmationModel transferTransactionConfirmation) {
		this.transferTransactionConfirmation = transferTransactionConfirmation;
	}
}
