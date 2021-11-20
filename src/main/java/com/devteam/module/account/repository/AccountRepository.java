package com.devteam.module.account.repository;

import com.devteam.module.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    boolean existsByUsername(String username);

    Account findByUsernameAndPassword(String username, String password);

    Account findByUsername(String username);

}
