package com.wf.hackathon.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {
    private LocalDate createdDate;
    private LocalDate updatedDate;

    @PrePersist
    public void preCreate() {
        createdDate = LocalDate.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedDate = LocalDate.now();
    }
}
