package com.simplemethod.university.mongodbDAO;

import com.couchbase.client.core.error.DocumentExistsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import com.simplemethod.university.MongodbConfig;
import com.simplemethod.university.mongodbModel.lecturer.LecturerModel;
import com.simplemethod.university.mongodbModel.lecturer.LecturerSubjectModel;
import com.simplemethod.university.mongodbModel.student.StudentModel;
import com.simplemethod.university.mongodbModel.student.StudentSubjectsModel;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import picocli.CommandLine;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


import static com.mongodb.client.model.Filters.*;

@Controller
public class MongodbDAO {

    @Autowired
    MongodbConfig mongodbConfig;

    private MongoCollection<Document> mongoStudentCollection;
    private MongoCollection<Document> mongoLecturerCollection;

    public void init() {
        mongoStudentCollection = mongodbConfig.openCollection("universityStudent");
        mongoLecturerCollection = mongodbConfig.openCollection("universityLecturer");
    }

    /**
     * Deleting a student from bucket.
     *
     * @param albumNumber Student album number.
     */
    public void removeStudentByAlbumNumber(Integer albumNumber) {
        mongoStudentCollection.findOneAndDelete(Filters.eq("albumNumber", albumNumber));
    }


    /**
     * Update student independentStudy grade.
     *
     * @param albumNumber  Student album number.
     * @param subjectNName Subject name.
     * @param grade        New grade to update.
     */
    public void setStudentGradesByAlbumNumberAndSubjectNameAndIndependentStudyType(Integer albumNumber, String subjectNName, Float grade) {
        Bson filter= Filters.and(Filters.eq("albumNumber",albumNumber), Filters.eq("studentSubjectsModelList.name",subjectNName));
        UpdateResult result = null;
        Bson set = Updates.set("studentSubjectsModelList.$.independentStudy", grade);
        result = mongoStudentCollection.updateOne(filter, set);
        if(result.getMatchedCount()==0)
        {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Brak danych!|@"));
        }
    }

    /**
     * Update student discussion grade.
     *
     * @param albumNumber  Student album number.
     * @param subjectNName Subject name.
     * @param grade        New grade to update.
     */
    public void setStudentGradesByAlbumNumberAndSubjectNameAndDiscussionType(Integer albumNumber, String subjectNName, Float grade) {
        Bson filter= Filters.and(Filters.eq("albumNumber",albumNumber), Filters.eq("studentSubjectsModelList.name",subjectNName));
        UpdateResult result = null;
        Bson set = Updates.set("studentSubjectsModelList.$.discussion", grade);
        result = mongoStudentCollection.updateOne(filter, set);
        if(result.getMatchedCount()==0)
        {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Brak danych!|@"));
        }
    }

    /**
     * Update student laboratory grade.
     *
     * @param albumNumber  Student album number.
     * @param subjectNName Subject name.
     * @param grade        New grade to update.
     */
    public void setStudentGradesByAlbumNumberAndSubjectNameAndLaboratoryType(Integer albumNumber, String subjectNName, Float grade) {
        Bson filter= Filters.and(Filters.eq("albumNumber",albumNumber), Filters.eq("studentSubjectsModelList.name",subjectNName));
        UpdateResult result = null;
        Bson set = Updates.set("studentSubjectsModelList.$.laboratory", grade);
        result = mongoStudentCollection.updateOne(filter, set);

        if(result.getMatchedCount()==0)
        {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Brak danych!|@"));
        }
    }

    /**
     * Update student lecture grade.
     *
     * @param albumNumber  Student album number.
     * @param subjectNName Subject name.
     * @param grade        New grade to update.
     */
    public void setStudentGradesByAlbumNumberAndSubjectNameAndLectureType(Integer albumNumber, String subjectNName, Float grade) {
        Bson filter= Filters.and(Filters.eq("albumNumber",albumNumber), Filters.eq("studentSubjectsModelList.name",subjectNName));
        UpdateResult result = null;
        Bson set = Updates.set("studentSubjectsModelList.$.lecture", grade);
        result = mongoStudentCollection.updateOne(filter, set);

        if(result.getMatchedCount()==0)
        {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Brak danych!|@"));
        }
    }

    /**
     * Search bucket with IndependentStudy pattern and print student object.
     *
     * @param subjectName Subject name.
     * @param grades      Student grades.
     */
    public void findAllBySubjectsNameAndIndependentStudyGrades(String subjectName, Integer grades) throws JsonProcessingException {
        Bson filter= Filters.and(  Filters.eq("studentSubjectsModelList.name",subjectName), Filters.eq("studentSubjectsModelList.independentStudy",grades));
        FindIterable<Document> documents = mongoStudentCollection.find(filter);
        for(Document doc: documents)
        {
            doc.remove("_id");
            getStudent(doc);
        }
    }

    /**
     * Search bucket with Discussion pattern and print student object.
     *
     * @param subjectName Subject name.
     * @param grades      Student grades.
     */
    public void findAllBySubjectsNameAndDiscussionGrades(String subjectName, Integer grades) throws JsonProcessingException {
        Bson filter= Filters.and(  Filters.eq("studentSubjectsModelList.name",subjectName), Filters.eq("studentSubjectsModelList.discussion",grades));
        FindIterable<Document> documents = mongoStudentCollection.find(filter);
        for(Document doc: documents)
        {
            doc.remove("_id");
            getStudent(doc);
        }
    }

    /**
     * Search bucket with Laboratory pattern and print student object.
     *
     * @param subjectName Subject name.
     * @param grades      Student grades.
     */
    public void findAllBySubjectsNameAndLaboratoryGrades(String subjectName, Integer grades) throws JsonProcessingException {
        Bson filter= Filters.and(  Filters.eq("studentSubjectsModelList.name",subjectName), Filters.eq("studentSubjectsModelList.laboratory",grades));
        FindIterable<Document> documents = mongoStudentCollection.find(filter);
        for(Document doc: documents)
        {
            doc.remove("_id");
            getStudent(doc);
        }
    }

    /**
     * Search bucket with Lecture pattern and print student object.
     *
     * @param subjectName Subject name.
     * @param grades      Student grades.
     */
    public void findAllBySubjectsNameAndLectureGrades(String subjectName, Integer grades) throws JsonProcessingException {
        Bson filter= Filters.and(  Filters.eq("studentSubjectsModelList.name",subjectName), Filters.eq("studentSubjectsModelList.lecture",grades));
        FindIterable<Document> documents = mongoStudentCollection.find(filter);
        for(Document doc: documents)
        {
            doc.remove("_id");
            getStudent(doc);
        }
    }

    /**
     * Search bucket with group pattern and print students object.
     *
     * @param subjectName Subject name.
     * @param group      Student group.
     */
    public void findAllBySubjectsNameAndGroup(String subjectName, String group) throws JsonProcessingException {
        //{ $and :[ {'studentSubjectsModelList.lecture': { $eq: 6}}, {'studentSubjectsModelList.name':{$eq: "PSR"}}] }
        Bson filter= Filters.and(  Filters.eq("studentSubjectsModelList.group",group), Filters.eq("studentSubjectsModelList.name",subjectName));
        FindIterable<Document> documents = mongoStudentCollection.find(filter);
        for(Document doc: documents)
        {
            doc.remove("_id");
            getStudent(doc);
        }
    }

    /**
     * Update of e-mail address.
     *
     * @param albumNumber Student album number.
     * @param email       New student email.
     */
    public void setStudentEmailByAlbumNumber(Integer albumNumber, String email) {
        UpdateResult result = null;
        result = mongoStudentCollection.updateOne(Filters.eq("albumNumber", albumNumber), new Document("$set", new Document("email", email)));
        if(result.getMatchedCount()==0)
        {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Brak danych!|@"));
        }
    }

    /**
     * Returns all students.
     */
    public void findAll() throws JsonProcessingException {
       FindIterable<Document> documents =  mongoStudentCollection.find();
        for(Document doc: documents)
        {
            doc.remove("_id");
            getStudent(doc);
        }
    }

    /**
     * Saves student into a table.
     *
     * @param object JSON object with data.
     */
    public void saveStudent(StudentModel object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Document dbObject = Document.parse(mapper.writeValueAsString(object));
            mongoStudentCollection.insertOne(dbObject);
        } catch (JsonProcessingException | MongoWriteException e) {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Podany student już istnieje lub blad danych!|@"));
        }
    }

    /**
     * Returns information about student.
     *
     * @param albumNumber Student album number.
     */
    public void findOneByAlbumNumber(Integer albumNumber) throws JsonProcessingException {

        Document myDoc = mongoStudentCollection.find(eq("albumNumber", albumNumber)).first();
        if(myDoc==null)
        {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Brak studenta!|@"));
            return;
        }
        myDoc.remove("_id");
        getStudent(myDoc);
    }

    /**
     * Returns information about student.
     * @param doc  Student object.
     * @throws JsonProcessingException
     */
    private void getStudent(Document doc) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        StudentModel jsonObject = objectMapper.readValue(doc.toJson(), StudentModel.class);
        Integer album = jsonObject.getAlbumNumber();
        String name = jsonObject.getFirstName() + " " + jsonObject.getSecondName();
        String email = jsonObject.getEmail();
        String degree = jsonObject.getDegree();
        String course = jsonObject.getCourse();
        Integer currentSemester = jsonObject.getCurrentSemester();
        List<StudentSubjectsModel> subjects = jsonObject.getStudentSubjectsModelList();

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
        for (StudentSubjectsModel studentSubjectsModel : subjects) {
            System.out.format("+-------------------------------------------+--------------------+--------------------+--------------------+--------------------+---------------------+%n");
            System.out.format(subject, studentSubjectsModel.getName(), studentSubjectsModel.getGroup(), studentSubjectsModel.getLecture(), studentSubjectsModel.getLaboratory(), studentSubjectsModel.getDiscussion(), studentSubjectsModel.getIndependentStudy());
        }
        System.out.format("+-------------------------------------------+--------------------+--------------------+--------------------+--------------------+---------------------+%n");
    }

    /**
     * Returns information about lecturer.
     *
     * @param emailID Lecturer e-mail.
     */
    public void getLecturer(String emailID) throws JsonProcessingException {
        Document myDoc = mongoLecturerCollection.find(eq("email", emailID)).first();
        if(myDoc==null)
        {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Brak studenta!|@"));
            return;
        }
        myDoc.remove("_id");

        ObjectMapper objectMapper = new ObjectMapper();
        LecturerModel jsonObject = objectMapper.readValue(myDoc.toJson(), LecturerModel.class);
        String  id = jsonObject.getId();
        String name = jsonObject.getFirstName() + " " + jsonObject.getSecondName();
        String email = jsonObject.getEmail();
        String degree = jsonObject.getDegree();
        List<LecturerSubjectModel> subjects = jsonObject.getLecturerSubjectModelList();
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
        for (LecturerSubjectModel lecturerSubjectModel : subjects) {
            System.out.format("+-------------------------------------------+--------------------+--------------------+%n");
            System.out.format(subject, lecturerSubjectModel.getName(), lecturerSubjectModel.getGroup(), lecturerSubjectModel.getType());
        }
        System.out.format("+-------------------------------------------+--------------------+--------------------+%n");
    }

    /**
     * Deleting a lecturer from bucket.
     *
     * @param id lecturer email.
     */
    public void removeLecturerByID(String id) {
        mongoLecturerCollection.findOneAndDelete(Filters.eq("email", id));
    }


    /**
     * Saves lecturer into a bucket.
     *
     * @param object             JSON object with data.
     */
    public void saveLecturer(Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Document dbObject = Document.parse(mapper.writeValueAsString(object));
            mongoLecturerCollection.insertOne(dbObject);
        } catch (JsonProcessingException | MongoWriteException e) {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Podany student już istnieje lub blad danych!|@"));
        }
    }

}
