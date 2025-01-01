package com.erp.backend.dto;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CourseDto {
    private Long courseId;
    private String coursecode;
    private String name;
    private String branch;
    //private MultipartFile image;
    private byte[] image;
    
}
