package com.devteam.module.account.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private String id;
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
    private String email;
    @NotBlank
    @Size(max = 120)
    private String password;
    @NotBlank
    private String fullname;
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    private String phonenumber;
    private String gender;
    private String address;
    private String city;
    private String birthdate;
    private String job;
    private Set<String> roles = new HashSet<String>();

}
