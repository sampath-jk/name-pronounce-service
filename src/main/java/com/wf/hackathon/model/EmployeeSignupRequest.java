package com.wf.hackathon.model;

import com.wf.hackathon.entity.ERole;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@NoArgsConstructor
public class EmployeeSignupRequest {

    @NotBlank
    private String employeeId;

    @NotBlank
    private String firstName;

    private String lastName;

    @NotBlank
    private String preferredName;

    private String address;

    private String city;

    @NotBlank
    private String country;

    @NotBlank
    private String telephone;

    @NotBlank
    @Email
    private String email;

    private String password;

    private Set<ERole> roles;

}
