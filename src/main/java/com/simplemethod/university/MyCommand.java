package com.simplemethod.university;


1
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "university", mixinStandardHelpOptions = true, version = "1.0 dev")
public class MyCommand implements Runnable {

    @Autowired
    MongodbConfig mongodbConfig;

    @Autowired
    MainViewMongodb mainViewMongodb;


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
        mongodbConfig.openConnection(ipAddress,bucket);
        mainViewMongodb.menu();
    }
}