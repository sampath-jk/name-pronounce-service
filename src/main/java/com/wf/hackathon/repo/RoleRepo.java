package com.wf.hackathon.repo;

import com.wf.hackathon.entity.ERole;
import com.wf.hackathon.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);

}
