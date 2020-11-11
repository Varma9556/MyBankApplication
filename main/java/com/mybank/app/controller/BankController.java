package com.mybank.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mybank.app.dto.FundTransferRequestDto;
import com.mybank.app.dto.FundTransferResponseDto;
import com.mybank.app.service.BankService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="FundTransaction", description="REST Api for Fund Transfer",tags={"FundTransfer"})
@RequestMapping("/SBI")
public class BankController {

	@Autowired
	BankService bankService;

	@Autowired
	Environment enviroment;
	
	
	@ApiOperation(value="FundTranfer", tags = {"FundTransfer"})
	@PostMapping("/transfer")
	public ResponseEntity<String> fundTransfer(@RequestBody FundTransferRequestDto fundTransferRequestDto) {
		bankService.fundTransfer(fundTransferRequestDto);
		return new ResponseEntity<String>("Fund Transfer Successfully", HttpStatus.ACCEPTED);
	}

	@ApiOperation(value="Get Transaction Statement", tags = {"FundTransfer"})
	@GetMapping(value = "/transactionStatement")
	public ResponseEntity<List<FundTransferResponseDto>> getTransactions(
			@RequestParam("fromaccountNumber") Long fromaccountNumber, @RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize) {
		List<FundTransferResponseDto> fundTransfers = bankService.getTransactionStatement(fromaccountNumber, pageNo,
				pageSize);
		return new ResponseEntity<List<FundTransferResponseDto>>(fundTransfers, HttpStatus.OK);
	}

	@GetMapping("/port")
	public String getInfo() {
		String port = enviroment.getProperty("local.server.port");
		return "From Server : " + port;

	}
}
