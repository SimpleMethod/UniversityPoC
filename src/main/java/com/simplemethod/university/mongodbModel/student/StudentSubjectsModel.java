package com.simplemethod.university.mongodbModel.student;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Resource
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

    public StudentSubjectsModel() {
    }

    public StudentSubjectsModel(@NotEmpty String name, @NotEmpty String group, @NotNull float lecture, @NotNull float laboratory, @NotNull float discussion, @NotNull float independentStudy) {
        this.name = name;
        this.group = group;
        this.lecture = lecture;
        this.laboratory = laboratory;
        this.discussion = discussion;
        this.independentStudy = independentStudy;
    }
}
