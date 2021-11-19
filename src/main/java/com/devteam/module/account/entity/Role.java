package com.devteam.module.account.entity;

import com.devteam.module.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table( name = User.TABLE_NAME)
@NoArgsConstructor
@Getter
@Setter
public class Role extends BaseEntity {
    public static final String TABLE_NAME = "role";

    private String name;
}
