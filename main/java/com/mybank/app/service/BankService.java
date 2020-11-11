package com.mybank.app.service;

import java.util.List;

import com.mybank.app.dto.FundTransferRequestDto;
import com.mybank.app.dto.FundTransferResponseDto;

public interface BankService {

	String fundTransfer(FundTransferRequestDto fundTransferRequestDto);

	List<FundTransferResponseDto> getTransactionStatement(Long accountNumber, Integer pageNo, Integer pageSize);

}
