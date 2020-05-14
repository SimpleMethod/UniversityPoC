package com.simplemethod.university.couchbaseModels.student;

import lombok.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
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
}
