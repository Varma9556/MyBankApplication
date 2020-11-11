package com.mybank.app.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mybank.app.dto.FundTransferRequestDto;
import com.mybank.app.dto.FundTransferResponseDto;
import com.mybank.app.entity.Account;
import com.mybank.app.entity.FundTransfer;
import com.mybank.app.exception.AccountInfoNotFoundException;
import com.mybank.app.exception.RecordNotFoundException;
import com.mybank.app.repository.AccountInfoRepository;
import com.mybank.app.repository.FundTransferRepository;
import com.mybank.app.service.BankService;

@Service
public class BankServiceImpl implements BankService {

	@Autowired
	FundTransferRepository fundTransferRepository;

	@Autowired
	AccountInfoRepository accountInfoRepository;
	

	public String fundTransfer(FundTransferRequestDto fundTransferRequestDto) {
		FundTransfer fundTransfer = new FundTransfer();
		fundTransfer.setFromAccountNumber(fundTransferRequestDto.getFromAccountNumber());
		fundTransfer.setToAccount(fundTransferRequestDto.getToAccount());
		fundTransfer.setAmount(fundTransferRequestDto.getAmount());
		fundTransfer.setTransfer_type("Debit");
		long fromAccount = fundTransferRequestDto.getFromAccountNumber();
		long toAccount = fundTransferRequestDto.getToAccount();
		double transferFund = fundTransferRequestDto.getAmount();
		this.debitTransferFundFromAccount(fromAccount, transferFund);
		this.creditTransferFundtoAccount(toAccount, transferFund);
		fundTransfer.setRemarks(fundTransferRequestDto.getRemarks());
		fundTransfer.setTransferDate("02-11-2020 13:40");
		fundTransferRepository.save(fundTransfer);

		return "Funds Transfer Successfully";

	}

	private void debitTransferFundFromAccount(Long fromAccountNumber, double transferFund)throws AccountInfoNotFoundException {
		Account fromAccount = new Account();
		fromAccount = accountInfoRepository.findByaccount_number(fromAccountNumber);
		double beforeCurrentBalance;
		double presentCurrentBalance;
		beforeCurrentBalance = fromAccount.getCurrentBalance();
		if (transferFund != 0) {
			presentCurrentBalance = (beforeCurrentBalance - transferFund);
			fromAccount.setCurrentBalance(presentCurrentBalance);

		}

		accountInfoRepository.save(fromAccount);

	}

	private void creditTransferFundtoAccount(Long toAccount, double transferFund) {
		Account creditAccount = new Account();
		System.out.println("to account:"+toAccount);
		creditAccount = accountInfoRepository.findByaccount_number(toAccount);
		double beforeCurrentBalance;
		double presentCurrentBalance;
		beforeCurrentBalance = creditAccount.getCurrentBalance();
		if (transferFund != 0) {
			presentCurrentBalance = (beforeCurrentBalance + transferFund);
			creditAccount.setCurrentBalance(presentCurrentBalance);

		}

		accountInfoRepository.save(creditAccount);

	}

	public List<FundTransferResponseDto> getTransactionStatement(Long fromAccountNumber, Integer pageNo, Integer pageSize)
	
					throws RecordNotFoundException {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("transferId").descending());
		List<FundTransfer> pagesResult = fundTransferRepository.findByfromAccountNumber(fromAccountNumber, paging);
	    if(pagesResult.isEmpty()) {
	    	throw new RecordNotFoundException("There is no Transactions for this Account");
	    }
		
		List<FundTransferResponseDto> fundTransferResponseDtoList = new ArrayList ();
	    //for (FundTransfer fundTransfer: pagesResult   )
		pagesResult.stream().forEach(fundTransfer->
	    {
	    	FundTransferResponseDto fundTransferResponseDto = new FundTransferResponseDto ();
	    	BeanUtils.copyProperties(fundTransfer, fundTransferResponseDto);
	    	fundTransferResponseDtoList.add(fundTransferResponseDto);
	    	
	    });
		return fundTransferResponseDtoList;

	}
}
