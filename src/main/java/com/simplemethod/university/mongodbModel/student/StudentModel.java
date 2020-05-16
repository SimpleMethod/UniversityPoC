package com.simplemethod.university.mongodbModel.student;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@EqualsAndHashCode
@Resource
public class StudentModel {

    @NotNull
    int id;
    @NotEmpty
    String firstName;
    @NotEmpty
    String secondName;
    @NotNull
    int albumNumber;
    @NotEmpty
    String email;
    @NotEmpty
    String degree;
    @NotNull
    int currentSemester;
    @NotEmpty
    String course;
    @NotNull
    List<StudentSubjectsModel> studentSubjectsModelList   = new ArrayList<>();

    public StudentModel(@NotNull int id, @NotEmpty String firstName, @NotEmpty String secondName, @NotNull int albumNumber, @NotEmpty String email, @NotEmpty String degree, @NotNull int currentSemester, @NotEmpty String course, @NotNull List<StudentSubjectsModel> studentSubjectsModelList) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.albumNumber = albumNumber;
        this.email = email;
        this.degree = degree;
        this.currentSemester = currentSemester;
        this.course = course;
        this.studentSubjectsModelList = studentSubjectsModelList;
    }

}
