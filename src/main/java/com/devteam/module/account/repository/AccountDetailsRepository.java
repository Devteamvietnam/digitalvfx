package com.devteam.module.account.repository;

import com.devteam.module.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDetailsRepository extends JpaRepository<Account, String> {

    Account findByUserName(String userName);

}
