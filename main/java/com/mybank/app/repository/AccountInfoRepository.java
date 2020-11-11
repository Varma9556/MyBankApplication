package com.mybank.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mybank.app.entity.Account;

@Repository
public interface AccountInfoRepository extends JpaRepository<Account, Long>{
	

	  @Query("From Account account where account.accountNumber =:fromaccount")
	    public Account findByaccount_number(long fromaccount);

}
