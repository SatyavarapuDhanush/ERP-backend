package com.erp.backend.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="assignments")
public class AssignmentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="assId")
    private Long assId;
    @Column(nullable = false)
    private String assName;
    @Column(nullable = false)
    private String assCourse;
    @Column(nullable = false)
    private String branch;
    @Column(nullable = false)
    private String assdoc;


    @ManyToMany
    @JoinTable(
        name = "assignment_user",
        joinColumns = @JoinColumn(name="assignment_id"),
        inverseJoinColumns = @JoinColumn(name="user_id")
    )
    private List<UserModel> assignedUsers;

    @OneToMany(mappedBy = "assignment",cascade = CascadeType.ALL)
    private List<SubmissionModel> submissions;
    

    
}
