package com.wf.hackathon.security;


import com.wf.hackathon.entity.Employee;
import com.wf.hackathon.entity.Role;
import com.wf.hackathon.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private EmployeeRepo employeeRepo;

  public UserDetailsServiceImpl(EmployeeRepo employeeRepo) {
    this.employeeRepo = employeeRepo;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Employee employee = employeeRepo.findById(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    List<GrantedAuthority> authorities = employee.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName().name()))
            .collect(Collectors.toList());
    return new User(employee.getEmployeeId() , employee.getPassword(),authorities);
  }

}
