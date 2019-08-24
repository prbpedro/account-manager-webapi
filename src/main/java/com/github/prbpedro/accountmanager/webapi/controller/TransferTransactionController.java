package com.github.prbpedro.accountmanager.webapi.controller;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.github.prbpedro.accountmanager.webapi.dto.TransferTransactionReturnDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Path("transfer")
public class TransferTransactionController {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(
		summary = "Execute a transfer",
		tags = {"pets"},
		description = "Execute a transfer when when the given accounts belogs to revoluts",
		responses = {
			@ApiResponse(
					description = "The  transaction's confirmation data", 
					content = @Content(schema = @Schema(implementation = TransferTransactionReturnDto.class))),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND")
		}
	)
	public TransferTransactionReturnDto executeTransfer(
			@NotNull
		    @Size(min = 1, max = 500)
			@QueryParam("sender-account-id")
			@Parameter(description = "Sender's account ID", required = true, schema = @Schema(maxLength = 500))
			String idAccountSender, 
			
			@NotNull
		    @Size(min = 1, max = 500)
			@QueryParam("beneficiary-account-id")
			@Parameter(description = "Beneficiary's account ID", required = true, schema = @Schema(maxLength = 500))
			String idAccountBeneficiary, 
			
			@NotNull
		    @Size(min = 1, max = 100)
			@QueryParam("beneficiary-bank-code")
			@Parameter(description = "Beneficiary's bank CODE", required = true, schema = @Schema(maxLength = 100))
			String codeBankBeneficiary, 
			
			@NotNull
		    @Size(min = 1, max = 100)
			@QueryParam("currency-code")
			@Parameter(description = "Currency CODE", required = true, schema = @Schema(maxLength = 100))
			String codeCurrency, 
			
			@NotNull
			@QueryParam("ammount")
			@Pattern(regexp = "^\\d{1,19}\\.?\\d{0,2}$")
			@Parameter(description = "Ammount to transfer", required = true, schema = @Schema(format = "type: BigDecimal, format: (19,2)"))
			String ammount) {
		TransferTransactionReturnDto ret = new TransferTransactionReturnDto();

		ret.setMessage("OUAAAAAAAAAAAAAAA");
		ret.getErros().add("EITAAAAAAAAAAA");
		ret.getErros().add("UOUOOOOOOOOOOOOO");
		return ret;
	}	 
}
