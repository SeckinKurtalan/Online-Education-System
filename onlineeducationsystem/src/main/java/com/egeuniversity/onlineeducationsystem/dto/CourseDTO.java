package com.egeuniversity.onlineeducationsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {
    private String title;
    private String description;
    private String attachment;
    private String category;
    private Double price;
    private String creatorId; // Assuming this holds the ID of the User who is the creator

}

