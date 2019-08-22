package com.sudhakar.web.smsServer.DAOS;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sudhakar.web.smsServer.DAOS.model.PhoneNumber;

public interface PhoneNumberDAORepository extends CrudRepository<PhoneNumber, Integer> {
	@Query("select p from PhoneNumber p where p.number=?1 AND p.account.id=?2")
	public PhoneNumber findByNumberAndAccountID(String number, int acct_id);
}
