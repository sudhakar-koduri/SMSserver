package com.sudhakar.web.smsServer.DAOS;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sudhakar.web.smsServer.DAOS.model.Account;

public interface AccountDAORepository extends CrudRepository<Account, Integer> {

	@Query("select a from Account a where a.userName=?1 and a.authID=?2")
	public Account findByUsernameAndAuthId(String userName, String AuthID);
}
