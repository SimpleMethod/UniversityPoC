package com.simplemethod.university.mongodbModel.lecturer;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@EqualsAndHashCode
@Resource
public class LecturerModel {
    @NotEmpty
    String  id;
    @NotEmpty
    String firstName;
    @NotEmpty
    String secondName;
    @NotEmpty
    String email;
    @NotEmpty
    String degree;
    @NotNull
    List<LecturerSubjectModel> lecturerSubjectModelList = new ArrayList<>();

    public LecturerModel(@NotNull String id, @NotEmpty String firstName, @NotEmpty String secondName, @NotEmpty String email, @NotEmpty String degree, @NotNull List<LecturerSubjectModel> lecturerSubjectModelList) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.degree = degree;
        this.lecturerSubjectModelList = lecturerSubjectModelList;
    }

}
