package com.devteam.security.jwt;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.devteam.module.account.dto.AccountDto;
import com.devteam.module.account.entity.Account;
import com.devteam.module.base.convert.AccountConverter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;


    private AccountDto user;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(AccountDto user,
                           Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
        this.user = user;
    }

    public static UserDetailsImpl build(Account user) {
        List<GrantedAuthority> authorities = user.getAccountRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(AccountConverter.getInstance().entityToDto(user), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public AccountDto getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl u = (UserDetailsImpl) o;
        return Objects.equals(u.getId(), getUser().getId());
    }

    @Override
    public String getPassword() {
        return getUser().getPassword();
    }

    @Override
    public String getUsername() {
        return getUser().getUsername();
    }

    public String getId() {
        return getUser().getId();
    }


}

