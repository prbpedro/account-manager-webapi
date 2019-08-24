package com.github.prbpedro.accountmanager.webapi.controller;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.LoggerFactory;

import com.github.prbpedro.accountmanager.domain.enums.TransferTransactionStatusEnum;
import com.github.prbpedro.accountmanager.domain.services.TransferTransactionService;
import com.github.prbpedro.accountmanager.domain.services.dto.TransferTransactionReturnDto;
import com.github.prbpedro.accountmanager.domain.util.Startup;
import com.github.prbpedro.accountmanager.webapi.dto.TransferTransactioReturnModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Path("transfer")
public class TransferTransactionController {

	@POST
	@Path("do")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(
		summary = "Execute a transfer",
		tags = {"pets"},
		description = "Execute a transfer when when the given accounts belogs to revoluts",
		responses = {
			@ApiResponse(
					description = "The  transaction's confirmation data", 
					content = @Content(schema = @Schema(implementation = TransferTransactioReturnModel.class))),
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "422", description = "UNPROCESSABLE ENTITY"),
			@ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR"),
		}
	)
	public Response executeTransfer(
			@NotNull
		    @Size(min = 1, max = 500)
			@FormParam("sender-account-id")
			@Parameter(description = "Sender's account ID", required = true, schema = @Schema(maxLength = 500))
			String senderAccountId, 
			
			@NotNull
		    @Size(min = 1, max = 500)
			@FormParam("beneficiary-account-id")
			@Parameter(description = "Beneficiary's account ID", required = true, schema = @Schema(maxLength = 500))
			String beneficiaryAccountId, 
			
			@NotNull
		    @Size(min = 1, max = 100)
			@FormParam("beneficiary-bank-code")
			@Parameter(description = "Beneficiary's bank CODE", required = true, schema = @Schema(maxLength = 100))
			String beneficiaryBankCode, 
			
			@NotNull
		    @Size(min = 1, max = 100)
			@FormParam("currency-code")
			@Parameter(description = "Currency CODE", required = true, schema = @Schema(maxLength = 100))
			String currencyCode, 
			
			@NotNull
			@FormParam("ammount")
			@Pattern(regexp = "^\\d{1,19}\\.?\\d{0,2}$")
			@Parameter(description = "Ammount to transfer", required = true, schema = @Schema(format = "type: BigDecimal, format: (19,2)"))
			String ammount) {
		try {
			TransferTransactionReturnDto transferResponse = Startup.getInjector().getInstance(TransferTransactionService.class)
					.doTransferTransaction(senderAccountId, beneficiaryAccountId, beneficiaryBankCode, currencyCode, new BigDecimal(ammount));
			TransferTransactioReturnModel returnModel = new TransferTransactioReturnModel(transferResponse, currencyCode);
			
			if(TransferTransactionStatusEnum.NOT_PROCESSED.equals(transferResponse.getStatus())) {
				  return Response.status(422).entity(returnModel).build();
				  
			}else if(TransferTransactionStatusEnum.NOT_FOUND.equals(transferResponse.getStatus())){
				return Response.status(Response.Status.NOT_FOUND).entity(returnModel).build();
				
			}else if(TransferTransactionStatusEnum.ERROR_PROCESSING.equals(transferResponse.getStatus())){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(returnModel).build();
				
			}else {
				return Response.status(Response.Status.OK).entity(returnModel).build();
			}
			
		}catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
			
			TransferTransactioReturnModel transferModel = new TransferTransactioReturnModel();
			transferModel.setStatus(TransferTransactionStatusEnum.ERROR_PROCESSING);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(transferModel).build();
		}
	}	 
}
