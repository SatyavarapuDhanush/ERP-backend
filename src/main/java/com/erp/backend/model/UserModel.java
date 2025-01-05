package com.erp.backend.model;


import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="users")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userid", nullable = false)
    private Long userId;
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="branch")
    private String branch;
    @Column(name="email", nullable = false)
    private String email;
    
    @Column(name="password", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name="courseId",nullable = true)
    private CoursesModel course;

    private Integer marks;

    @ManyToMany(mappedBy = "assignedUsers")
    private List<AssignmentModel> assignments;

}
