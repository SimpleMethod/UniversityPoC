package com.simplemethod.university.couchbaseDAO;

import com.couchbase.client.core.error.DocumentExistsException;
import com.couchbase.client.core.error.DocumentNotFoundException;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.json.JsonArray;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.query.QueryResult;
import com.simplemethod.university.CouchbaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.List;

import static com.couchbase.client.java.query.QueryOptions.queryOptions;

@Controller
public class CouchbaseStudentDAO {

    Bucket bucket;
    Cluster cluster;
    @Autowired
    CouchbaseConfig couchbaseConfig;


    // @PostConstruct
    public void init() {
        cluster = couchbaseConfig.openClusterStudent();
        bucket = couchbaseConfig.openConnectionStudent();
    }


    /**
     * Saves student into a bucket.
     *
     * @param documentIdentifier Student album number.
     * @param object             JSON object with data.
     */
    public void saveStudent(Integer documentIdentifier, Object object) {
        try {
            bucket.defaultCollection().insert(documentIdentifier.toString(), object);
        } catch (DocumentExistsException e) {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Podany student już istnieje!|@"));
        }
    }

    /**
     * Deleting a student from bucket.
     *
     * @param albumNumber Student album number.
     */
    public void removeStudentByAlbumNumber(Integer albumNumber) {
        try {
            bucket.defaultCollection().remove(albumNumber.toString());
        } catch (DocumentNotFoundException e) {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Student o podanym numerze albumu nie istnieje!|@"));
        }
    }

    /**
     * Update of e-mail address.
     *
     * @param albumNumber Student album number.
     * @param email       New student email.
     */
    public void setStudentEmailByAlbumNumber(Integer albumNumber, String email) {
        GetResult getResult = null;
        try {
            getResult = bucket.defaultCollection().get(albumNumber.toString());
        } catch (DocumentNotFoundException e) {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Brak studenta!|@"));
            return;
        }

        JsonObject jsonObject = getResult.contentAsObject();
        jsonObject.put("email", email);
        bucket.defaultCollection().upsert(albumNumber.toString(), jsonObject);
    }

    /**
     * Update student independentStudy grade.
     *
     * @param albumNumber  Student album number.
     * @param subjectNName Subject name.
     * @param grade        New grade to update.
     */
    public void setStudentGradesByAlbumNumberAndSubjectNameAndIndependentStudyType(Integer albumNumber, String subjectNName, Float grade) {
        updateSubject(albumNumber, subjectNName, grade, "independentStudy");
    }

    /**
     * Update student discussion grade.
     *
     * @param albumNumber  Student album number.
     * @param subjectNName Subject name.
     * @param grade        New grade to update.
     */

    public void setStudentGradesByAlbumNumberAndSubjectNameAndDiscussionType(Integer albumNumber, String subjectNName, Float grade) {
        updateSubject(albumNumber, subjectNName, grade, "discussion");
    }

    /**
     * Update student laboratory grade.
     *
     * @param albumNumber  Student album number.
     * @param subjectNName Subject name.
     * @param grade        New grade to update.
     */
    public void setStudentGradesByAlbumNumberAndSubjectNameAndLaboratoryType(Integer albumNumber, String subjectNName, Float grade) {
        updateSubject(albumNumber, subjectNName, grade, "laboratory");
    }

    /**
     * Update student lecture grade.
     *
     * @param albumNumber  Student album number.
     * @param subjectNName Subject name.
     * @param grade        New grade to update.
     */
    public void setStudentGradesByAlbumNumberAndSubjectNameAndLectureType(Integer albumNumber, String subjectNName, Float grade) {
        updateSubject(albumNumber, subjectNName, grade, "lecture");
    }

    /**
     * Search bucket with IndependentStudy pattern and print student object.
     *
     * @param subjectName Subject name.
     * @param grades      Student grades.
     */
    public void findAllBySubjectsNameAndIndependentStudyGrades(String subjectName, Integer grades) {
        final QueryResult result = cluster.query("SELECT *  From `" + couchbaseConfig.getBucketName()[0] + "` UNNEST " + couchbaseConfig.getBucketName()[0] + ".studentSubjectsModelList AS relation WHERE relation.independentStudy =" + grades + " AND relation.name=\"" + subjectName + "\";", queryOptions().metrics(true));
        if( result.rowsAsObject().isEmpty())
        {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Brak danych!|@"));
            return;
        }
        for (JsonObject row : result.rowsAsObject()) {
            getStudent(row.getObject(couchbaseConfig.getBucketName()[0]).getInt("albumNumber").toString());
            System.out.print("\n");
        }
    }

    /**
     * Search bucket with Discussion pattern and print student object.
     *
     * @param subjectName Subject name.
     * @param grades      Student grades.
     */
    public void findAllBySubjectsNameAndDiscussionGrades(String subjectName, Integer grades) {
        final QueryResult result = cluster.query("SELECT *  From `" + couchbaseConfig.getBucketName()[0] + "` UNNEST " + couchbaseConfig.getBucketName()[0] + ".studentSubjectsModelList AS relation WHERE relation.discussion =" + grades + " AND relation.name=\"" + subjectName + "\";", queryOptions().metrics(true));
        if( result.rowsAsObject().isEmpty())
        {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Brak danych!|@"));
            return;
        }
        for (JsonObject row : result.rowsAsObject()) {
            getStudent(row.getObject(couchbaseConfig.getBucketName()[0]).getInt("albumNumber").toString());
            System.out.print("\n");
        }
    }

    /**
     * Search bucket with Laboratory pattern and print student object.
     *
     * @param subjectName Subject name.
     * @param grades      Student grades.
     */
    public void findAllBySubjectsNameAndLaboratoryGrades(String subjectName, Integer grades) {
        final QueryResult result = cluster.query("SELECT *  From `" + couchbaseConfig.getBucketName()[0] + "` UNNEST " + couchbaseConfig.getBucketName()[0] + ".studentSubjectsModelList AS relation WHERE relation.laboratory =" + grades + " AND relation.name=\"" + subjectName + "\";", queryOptions().metrics(true));
        if( result.rowsAsObject().isEmpty())
        {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Brak danych!|@"));
            return;
        }
        for (JsonObject row : result.rowsAsObject()) {
            getStudent(row.getObject(couchbaseConfig.getBucketName()[0]).getInt("albumNumber").toString());
            System.out.print("\n");
        }
    }

    /**
     * Search bucket with Lecture pattern and print student object.
     *
     * @param subjectName Subject name.
     * @param grades      Student grades.
     */
    public void findAllBySubjectsNameAndLectureGrades(String subjectName, Integer grades) {
        final QueryResult result = cluster.query("SELECT *  From `" + couchbaseConfig.getBucketName()[0] + "` UNNEST " + couchbaseConfig.getBucketName()[0] + ".studentSubjectsModelList AS relation WHERE relation.lecture =" + grades + " AND relation.name=\"" + subjectName + "\";", queryOptions().metrics(true));

        if( result.rowsAsObject().isEmpty())
        {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Brak danych!|@"));
            return;
        }
        for (JsonObject row : result.rowsAsObject()) {
            getStudent(row.getObject(couchbaseConfig.getBucketName()[0]).getInt("albumNumber").toString());
            System.out.print("\n");
        }
    }

    /**
     * Search bucket with group pattern and print students object.
     *
     * @param subjectName Subject name.
     * @param group      Student group.
     */
    public void findAllBySubjectsNameAndGroup(String subjectName, String group) {
        final QueryResult result = cluster.query("SELECT *  From `" + couchbaseConfig.getBucketName()[0] + "` UNNEST " + couchbaseConfig.getBucketName()[0] + ".studentSubjectsModelList AS relation WHERE relation.`group`=\"" + group + "\" AND relation.name=\"" + subjectName + "\";", queryOptions().metrics(true));
        if( result.rowsAsObject().isEmpty())
        {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Brak danych!|@"));
            return;
        }
        for (JsonObject row : result.rowsAsObject()) {
            getStudent(row.getObject(couchbaseConfig.getBucketName()[0]).getInt("albumNumber").toString());
            System.out.print("\n");
        }
    }

    /**
     * Returns all students.
     */
    public void findAll() {
        final QueryResult result = cluster.query("select * from `" + couchbaseConfig.getBucketName()[0] + "` limit 999", queryOptions().metrics(true));
        for (JsonObject row : result.rowsAsObject()) {
            getStudent(row.getObject(couchbaseConfig.getBucketName()[0]).getInt("albumNumber").toString());
            System.out.print("\n");
        }
    }

    /**
     * Returns information about student.
     *
     * @param albumNumber Student album number.
     */
    public void findOneByAlbumNumber(Integer albumNumber) {
        final QueryResult result = cluster.query("select * from `" + couchbaseConfig.getBucketName()[0] + "` WHERE  albumNumber=" + albumNumber + " limit 1", queryOptions().metrics(true));

        if (result.rowsAsObject().isEmpty()) {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Brak wyników!|@"));
            return;
        }

        for (JsonObject row : result.rowsAsObject()) {
            getStudent(row.getObject(couchbaseConfig.getBucketName()[0]).getInt("albumNumber").toString());
            System.out.print("\n");
        }
    }

    /**
     * Returns information about student.
     *
     * @param documentIdentifier Student album number.
     */
    private void getStudent(String documentIdentifier) {
        GetResult getResult = null;
        try {
            getResult = bucket.defaultCollection().get(documentIdentifier);
        } catch (DocumentNotFoundException e) {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Brak studenta!|@"));
            return;
        }

        JsonObject jsonObject = getResult.contentAsObject();
        Integer album = jsonObject.getInt("albumNumber");
        String name = jsonObject.getString("firstName") + " " + jsonObject.getString("secondName");
        String email = jsonObject.getString("email");
        String degree = jsonObject.getString("degree");
        String course = jsonObject.getString("course");
        Integer currentSemester = jsonObject.getInt("currentSemester");
        JsonArray subjects = jsonObject.getArray("studentSubjectsModelList");

        String student = "| %-14d | %-28s | %-28s | %-26s | %-18s | %-18d |%n";
        System.out.format("+----------------+------------------------------+------------------------------+----------------------------+--------------------+--------------------+%n");
        System.out.format("|  Numer albumu  |  Imię i nazwisko             |  Adres e-mail                |  Aktualny stopień naukowy  |  Kierunek          |  Aktualny semestr  |%n");
        System.out.format("+----------------+------------------------------+------------------------------+----------------------------+--------------------+--------------------+%n");
        System.out.format(student, album, name, email, degree, course, currentSemester);
        System.out.format("+----------------+------------------------------+------------------------------+----------------------------+--------------------+--------------------+%n");

        System.out.println("Oceny studenta:");


        String subject = "| %-41s | %-18s | %-18f | %-18f | %-18f | %-19f |%n";
        System.out.format("+-------------------------------------------+--------------------+--------------------+--------------------+--------------------+---------------------+%n");
        System.out.format("|  Nazwa przedmiotu                         |  Grupa             |  Wykład            |  Laboratorium      |  Ćwiczenia         |  Projekt            |%n");
        for (int i = 0; i < subjects.size(); i++) {
            JsonObject object = subjects.getObject(i);
            System.out.format("+-------------------------------------------+--------------------+--------------------+--------------------+--------------------+---------------------+%n");
            System.out.format(subject, object.getString("name"), object.getString("group"), object.getDouble("lecture"), object.getDouble("laboratory"), object.getDouble("discussion"), object.getDouble("independentStudy"));
        }
        System.out.format("+-------------------------------------------+--------------------+--------------------+--------------------+--------------------+---------------------+%n");
    }

    /**
     * Update student grade.
     *
     * @param albumNumber  Student album number.
     * @param subjectNName Subject name.
     * @param grade        New grade to update.
     * @param type         Type of activity.
     */
    private void updateSubject(Integer albumNumber, String subjectNName, Float grade, String type) {
        boolean update=false;
        GetResult getResult = null;
        try {
            getResult = bucket.defaultCollection().get(albumNumber.toString());
        } catch (DocumentNotFoundException e) {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Brak studenta!|@"));
            return;
        }
        JsonObject jsonObject = getResult.contentAsObject();
        List<JsonObject> jsonObjectList = new ArrayList<>();
        JsonArray subjects = jsonObject.getArray("studentSubjectsModelList");
        for (int i = 0; i < subjects.size(); i++) {
            JsonObject object = subjects.getObject(i);
            if (object.getString("name").equals(subjectNName)) {
                object.put(type, grade);
                update=true;
            }
            jsonObjectList.add(object);
        }
        if(!update)
        {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Brak przedmiotu przypisanego studentowi!|@"));
            return;
        }

        jsonObject.put("studentSubjectsModelList", jsonObjectList);
        bucket.defaultCollection().upsert(albumNumber.toString(), jsonObject);
    }

}
