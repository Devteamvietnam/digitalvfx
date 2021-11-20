package com.devteam.module.account.entity;

import com.devteam.module.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account extends BaseEntity {

    private String username;
    private String email;
    private String password;
    private String fullname;
    private String firstname;
    private String lastname;
    private String phonenumber;
    private String gender;
    private String address;
    private String city;
    private String job;
    private String birthdate;

    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "account_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> accountRoles = new HashSet<>();

}
