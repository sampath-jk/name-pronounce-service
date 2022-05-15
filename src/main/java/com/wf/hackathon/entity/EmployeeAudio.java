package com.wf.hackathon.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "employee_audio")
public class EmployeeAudio extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
//    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "default_audio")
    private byte[] defaultAudio;

    @Lob
    @Column(name = "preferred_audio")
    private byte[] preferredAudio;
}
