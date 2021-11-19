package com.devteam.module.account.entity;

import com.devteam.module.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = Role.TABLE_NAME)
@NoArgsConstructor
@Getter
@Setter
public class Role extends BaseEntity {
    public static final String TABLE_NAME = "role";

    private String name;

    public Role(String name) {
        this.name = name;
    }
}
