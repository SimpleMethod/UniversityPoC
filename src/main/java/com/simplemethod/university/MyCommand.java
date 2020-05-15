package com.simplemethod.university;


import com.simplemethod.university.couchbaseDAO.CouchbaseLecturerDAO;
import com.simplemethod.university.couchbaseDAO.CouchbaseStudentDAO;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.io.*;

@Component
@CommandLine.Command(name = "HorizonLink", mixinStandardHelpOptions = true, version = "1.0 dev")
public class MyCommand implements Runnable {


    @Autowired
    CouchbaseConfig couchbaseConfig;

    @Autowired
    CouchbaseLecturerDAO couchbaseLecturerDAO;

    // @Autowired
    //  CouchbaseStudentDAO couchbase;

    @Autowired
    MainView mainView;

    @CommandLine.Option(names = {"-ip", "--ipaddress"}, description = "Server IP address.")
    private String[] ipAddress = new String[]{"localhost"};

    @CommandLine.Option(names = {"-l", "--login"}, description = "Login to database.")
    private String[] login = new String[]{"dev"};

    @CommandLine.Option(names = {"-p", "--password"}, description = "Password to database.")
    private String[] password = new String[]{"dev2137"};

    @CommandLine.Option(names = {"-b", "--bucket"}, description = "Bucket name.")
    private String[] bucket = new String[]{"university","universityLecturer"};


    @SneakyThrows
    public void run() {
        couchbaseConfig.init(ipAddress, login, password, bucket);
        couchbaseLecturerDAO.init(ipAddress,login,password,bucket);
        mainView.menu();
        // couchbase.init();

        // couchbase.findAllByCurrentSemester();
        //couchbase.setStudentEmailByAlbumNumber(85218,"m.mlodawski@simplemethod.io");
        //couchbase.setStudentGradesByAlbumNumberAndSubjectNameAndIndependentStudyType(85218,"PSR",3);
        // couchbase.findOneByAlbumNumber(85218);

        // couchbase.findAllBySubjectsNameAndLectureGrades("PSR",6);


    }


}