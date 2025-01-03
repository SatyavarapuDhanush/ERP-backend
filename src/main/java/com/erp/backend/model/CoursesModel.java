package com.erp.backend.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
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
     @Column(nullable = false,unique = true)
    private String coursecode;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String branch;
    @Lob
    private byte[] image;  

    @OneToMany(mappedBy="course",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserModel> users;
}
