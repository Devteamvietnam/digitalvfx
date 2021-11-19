package com.devteam.module.account.repository;

import com.devteam.module.account.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByName (String name);
}
