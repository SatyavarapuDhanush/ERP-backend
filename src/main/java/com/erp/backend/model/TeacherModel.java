package com.erp.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "teachers")
public class TeacherModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate IDs if needed
    @Column(name = "teacherid")
    private Long teacherId;

    
    @Column(name = "name", nullable = false)
    private String name;

    
    @Column(name = "dept", nullable = false)
    private String dept;

    
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    
    @Column(name = "password", nullable = false)
    private String password;
}
