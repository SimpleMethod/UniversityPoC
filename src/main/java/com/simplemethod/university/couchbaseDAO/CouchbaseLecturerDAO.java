package com.simplemethod.university.couchbaseDAO;

import com.couchbase.client.core.error.DocumentExistsException;
import com.couchbase.client.core.error.DocumentNotFoundException;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.json.JsonArray;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetResult;
import org.springframework.stereotype.Controller;
import picocli.CommandLine;

@Controller
public class CouchbaseLecturerDAO {

    Bucket bucket;
    Cluster cluster;

    private String[] connection;
    private String[] username;
    private String[] password;
    public String[] bucketName;


    public void openConnection()
    {
        Cluster cluster = Cluster.connect(connection[0], username[0], password[0]);
        bucket= cluster.bucket(bucketName[1]);
    }
    public void openCluster()
    {
        cluster= Cluster.connect(connection[0], username[0], password[0]);
    }


    public void init(String[] connection, String[] username, String[] password, String[] bucketName) {
        this.connection = connection;
        this.username = username;
        this.password = password;
        this.bucketName = bucketName;
    }

    public void initConnection()
    {
        openConnection();
        openCluster();
    }

    /**
     * Saves lecturer into a bucket.
     *
     * @param documentIdentifier Lecturer ID.
     * @param object             JSON object with data.
     */
    public void saveLecturer(String documentIdentifier, Object object) {
        try {
            bucket.defaultCollection().insert(documentIdentifier, object);
        } catch (DocumentExistsException e) {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Podany student już istnieje!|@"));
        }
    }

    /**
     * Deleting a lecturer from bucket.
     *
     * @param id lecturer.
     */
    public void removeLecturerByID(String id) {
        try {
            bucket.defaultCollection().remove(id);
        } catch (DocumentNotFoundException e) {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Student o podanym numerze albumu nie istnieje!|@"));
        }
    }


    /**
     * Returns information about lecturer.
     *
     * @param documentIdentifier Lecturer e-mail.
     */
    public void getLecturer(String documentIdentifier) {
        GetResult getResult = null;
        try {
            getResult = bucket.defaultCollection().get(documentIdentifier);
        } catch (DocumentNotFoundException e) {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Brak wykładowcy!|@"));
            return;
        }

        JsonObject jsonObject = getResult.contentAsObject();
        String  id = jsonObject.getString("id");
        String name = jsonObject.getString("firstName") + " " + jsonObject.getString("secondName");
        String email = jsonObject.getString("email");
        String degree = jsonObject.getString("degree");
        JsonArray subjects = jsonObject.getArray("lecturerSubjectModelList");
        String student = "| %-44s | %-28s | %-44s | %-26s |%n";
        System.out.format("+----------------------------------------------+------------------------------+----------------------------------------------+----------------------------+%n");
        System.out.format("|  Numer ident                                 |  Imię i nazwisko             |  Adres e-mail                                |  Aktualny stopień naukowy  |%n");
        System.out.format("+----------------------------------------------+------------------------------+----------------------------------------------+----------------------------+%n");
        System.out.format(student, id, name, email, degree);
        System.out.format("+----------------------------------------------+------------------------------+----------------------------------------------+----------------------------+%n");
        System.out.println("Prowadzone przedmioty:");
        String subject = "| %-41s | %-18s | %-18s |%n";
        System.out.format("+-------------------------------------------+--------------------+--------------------+%n");
        System.out.format("|  Nazwa przedmiotu                         |  Grupa             |  Typ               |%n");
        for (int i = 0; i < subjects.size(); i++) {
            JsonObject object = subjects.getObject(i);
            System.out.format("+-------------------------------------------+--------------------+--------------------+%n");
            System.out.format(subject, object.getString("name"), object.getString("group"), object.getString("type"));
        }
        System.out.format("+-------------------------------------------+--------------------+--------------------+%n");
    }
}
