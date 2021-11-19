package com.devteam.module.account.entity;

import com.devteam.module.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table( name = Authority.TABLE_NAME)
@NoArgsConstructor
@Getter
@Setter
public class Authority extends BaseEntity implements GrantedAuthority {
    public static final String TABLE_NAME = "authority";

    @Column(name = "ROLE_CODE")
    private String roleCode;

    @Column(name = "ROLE_DESCRIPTION")
    private String roleDescription;


    @Override
    public String getAuthority() {
        return roleCode;
    }
}
