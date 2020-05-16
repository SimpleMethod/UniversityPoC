package com.simplemethod.university;


import com.simplemethod.university.couchbaseDAO.CouchbaseLecturerDAO;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "university", mixinStandardHelpOptions = true, version = "1.0 dev")
public class MyCommand implements Runnable {


    @Autowired
    CouchbaseConfig couchbaseConfig;

    @Autowired
    CouchbaseLecturerDAO couchbaseLecturerDAO;

    @Autowired
    MongodbConfig mongodbConfig;

    @Autowired
    MainViewMongodb mainViewMongodb;

    @Autowired
    MainViewCouchbase mainViewCouchbase;

    @CommandLine.Option(names = {"-ip", "--ipaddress"}, description = "Server IP address.")
    private String[] ipAddress = new String[]{"localhost"};

    @CommandLine.Option(names = {"-l", "--login"}, description = "Login to database.")
    private String[] login = new String[]{"dev"};

    @CommandLine.Option(names = {"-p", "--password"}, description = "Password to database.")
    private String[] password = new String[]{"dev2137"};

    @CommandLine.Option(names = {"-b", "--bucket"}, description = "Bucket name.")
    private String[] bucket = new String[]{"university","universityLecturer"};

    @CommandLine.Option(names = {"-t", "--type"}, description = "Type of database M=MongoDB C=Couchbase")
    private String[] type = new String[]{"C"};

    @SneakyThrows
    public void run() {

        if(type[0].equals("C") || type[0].equals("c"))
        {
            if(bucket.length!=2)
            {
                System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Brak wymaganych danych!|@"));
                System.exit(1);
            }

            couchbaseConfig.init(ipAddress, login, password, bucket);
            couchbaseLecturerDAO.init(ipAddress,login,password,bucket);
            mainViewCouchbase.menu();
        }
        else if(type[0].equals("M") || type[0].equals("m"))
        {
            mongodbConfig.openConnection(ipAddress,bucket);
            mainViewMongodb.menu();
        }
        else
        {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Nie wybrano żadnego skłądu!|@"));
            System.exit(1);
        }
    }


}