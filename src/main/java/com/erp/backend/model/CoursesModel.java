package com.erp.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="courses")
public class CoursesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long courseId;
     @Column(nullable = false)
    private String coursecode;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String branch;
    @Lob
    private byte[] image;  
}
