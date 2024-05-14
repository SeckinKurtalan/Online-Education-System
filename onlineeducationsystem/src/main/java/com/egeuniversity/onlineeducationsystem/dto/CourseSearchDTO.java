package com.egeuniversity.onlineeducationsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSearchDTO {
    private String title;
    private String description;
    private String category;
    private Double price;
    private String creatorId;
    private int page;
    private int size;

}