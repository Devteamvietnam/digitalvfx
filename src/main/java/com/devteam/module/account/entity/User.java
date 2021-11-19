package com.devteam.module.account.entity;

import com.devteam.module.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table( name = User.TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(
                        name = User.TABLE_NAME + "_username",
                        columnNames = {"username"}),
                @UniqueConstraint(
                        name = User.TABLE_NAME + "_email",
                        columnNames = {"email"})
        })
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Getter
@Setter
public class User extends BaseEntity {
    public static final String TABLE_NAME = "account";

    private String name;
    private String username;
    private String password;
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    public User(String name, String username, String password, String email) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
