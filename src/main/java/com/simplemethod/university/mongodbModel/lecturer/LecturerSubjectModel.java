package com.simplemethod.university.mongodbModel.lecturer;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;

@Data
@Resource
@AllArgsConstructor
public class LecturerSubjectModel {
    @NotEmpty
    String name;
    @NotEmpty
    String group;
    @NotEmpty
    String type;
}
