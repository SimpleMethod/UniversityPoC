package com.simplemethod.university.couchbaseModels.student;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Resource
@AllArgsConstructor
public class StudentSubjectsModel {
    @NotEmpty
    String name;
    @NotEmpty
    String group;
    @NotNull
    float lecture;
    @NotNull
    float laboratory;
    @NotNull
    float discussion;
    @NotNull
    float independentStudy;
}
