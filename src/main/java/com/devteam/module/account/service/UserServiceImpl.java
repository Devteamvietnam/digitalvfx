package com.devteam.module.account.service;

import com.devteam.module.account.entity.Role;
import com.devteam.module.account.entity.User;
import com.devteam.module.account.repository.RoleRepository;
import com.devteam.module.account.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
   @Autowired
   private UserRepository userRepo;
   @Autowired
   private RoleRepository roleRepo;
   @Autowired
   private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username ) throws UsernameNotFoundException {
       User user = userRepo.findByUsername(username);
       if(user == null) {
           log.error("User not found");
           throw  new UsernameNotFoundException("user not found");
       } else  {
           log.info("User found", username);
       }
        Collection<SimpleGrantedAuthority>  authorities = new ArrayList<>();
       user.getRoles().forEach(role -> {
           authorities.add(new SimpleGrantedAuthority(
                   role.getName()
           ));
       });
       return new org.springframework.security.core.userdetails.User(
               user.getUsername(),
               user.getPassword(),
               authorities
       );
    }

    @Override
    public User saveUser(User user) {
        log.info("save new user {}" , user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("save new role {}", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("adding role {} to user {}", roleName, username);
        User user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        log.info("fetching user {} ", username);
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("fetching all user {} ");
        return userRepo.findAll();
    }


}
