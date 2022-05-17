package com.wf.hackathon.model;

import com.wf.hackathon.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {
    private String employeeId;

    private String firstName;

    private String lastName;

    private String preferredName;

    private String address;

    private String city;

    private String country;

    private String telephone;

    private String email;

    private Set<Role> roles;


}
