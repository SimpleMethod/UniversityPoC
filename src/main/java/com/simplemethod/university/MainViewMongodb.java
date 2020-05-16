package com.simplemethod.university;

import com.simplemethod.university.mongodbModel.lecturer.LecturerModel;
import com.simplemethod.university.mongodbModel.lecturer.LecturerSubjectModel;
import com.simplemethod.university.mongodbModel.student.StudentModel;
import com.simplemethod.university.mongodbModel.student.StudentSubjectsModel;
import com.simplemethod.university.mongodbDAO.MongodbDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Controller
public class MainViewMongodb {

    @Autowired
    MongodbDAO mongodbStudentDAO;

    public void menu() throws IOException {
        mongodbStudentDAO.init();
        for (; ; ) searchMenu();
    }

    /**
     * Display menu.
     *
     * @throws IOException Terminal does not support buffer.
     */
    public void searchMenu() throws IOException {
        System.out.print("\n");
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(green) Wybierz pozycję z menu:|@"));
        System.out.print("\n");
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [1]|@ Wyszukiwanie wszystkich studentów"));
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [2]|@ Wyszukiwanie studentów po numerze albumu"));
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [3]|@ Wyszukiwanie studentów po przedmiotach"));
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [4]|@ Dodanie nowego studenta"));
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [5]|@ Zmiana adresu e-mail studenta"));
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [6]|@ Zmiana oceny z przedmiotu danego studenta"));
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [7]|@ Usunięcie studenta"));
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [8]|@ Dodanie wykładowcy"));
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [9]|@ Usunięcie wykładowcy"));
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [10]|@ Wyszukiwanie wykładowcy po adresie e-mail"));
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [11]|@ Lista studentów w grupie"));
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red)  [12]|@ Zamknięcie programu"));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        if (isNumeric(input)) {
            int parseInt = Integer.parseInt(input);
            switch (parseInt) {
                case 1:
                    findAllStudentsMenu();
                    break;
                case 2:
                    findStudentsByAlbumNumber();
                    break;
                case 3:
                    searchMenuBySubjectHelper();
                    break;
                case 4:
                    addNewStudent();
                    break;
                case 5:
                    setStudentByEmail();
                    break;
                case 6:
                    updateMenuBySubjectHelper();
                    break;
                case 7:
                    removeStudent();
                    break;
                case 8:
                    addNewLecturer();
                    break;
                case 9:
                    removeLecturer();
                    break;
                case 10:
                    findLecturerByEmail();
                    break;
                case 11:
                    findAllBySubjectsNameAndGroup();
                    break;
                case 12:
                    Runtime.getRuntime().exit(1);
                    break;
                default:
                    break;
            }
        } else {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Menu przyjmuje tylko liczby!|@"));
        }
    }

    /**
     * Search all students.
     */
    public void findAllStudentsMenu() throws IOException {
        mongodbStudentDAO.findAll();
        waitForEnter();
    }

    /**
     * Search lecturer by email.
     *
     * @throws IOException Terminal does not support buffer.
     */
    public void findLecturerByEmail() throws IOException {
        System.out.print("\n");
        System.out.println("Podaj adres email:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        mongodbStudentDAO.getLecturer(input);
    }

    /**
     * Update student e-mail address.
     *
     * @throws IOException Terminal does not support buffer.
     */
    public void setStudentByEmail() throws IOException {
        System.out.print("\n");
        System.out.println("Podaj numer albumu:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String albumNumber = br.readLine();
        System.out.print("\n");
        System.out.println("Podaj nowy adres e-mail:");
        String email = br.readLine();
        if (isNumeric(albumNumber) && !email.isEmpty()) {
            mongodbStudentDAO.setStudentEmailByAlbumNumber(Integer.parseInt(albumNumber), email);
        } else {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Menu przyjmuje tylko liczby albo brak danych!|@"));
        }
    }

    /**
     * Adding a new student.
     *
     * @throws IOException Terminal does not support buffer.
     */
    public void addNewStudent() throws IOException {
        StudentModel studentModel = new StudentModel();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(white) Menu dodawania nowego studenta|@"));
        System.out.print("\n");
        System.out.println("Numer albumu:");
        String albumNumber = br.readLine();
        System.out.println("Imie:");
        String firstName = br.readLine();
        System.out.println("Nazwisko:");
        String secondName = br.readLine();
        System.out.println("Adres email:");
        String email = br.readLine();
        System.out.println("Aktualny stopień naukowy:");
        String degree = br.readLine();
        System.out.println("Aktualny semestr:");
        String currentSemester = br.readLine();
        System.out.println("Kierunek:");
        String course = br.readLine();
        List<StudentSubjectsModel> studentSubjectsModels = addNewSubjects();

        try {
            if (albumNumber == null || firstName == null || secondName == null || email == null || degree == null || currentSemester == null || course == null || studentSubjectsModels.isEmpty()) {
                System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Dane nie mogą być puste!|@"));
                return;
            }
        } catch (NullPointerException e) {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Dane nie mogą być puste!|@"));
            return;
        }

        if (isNumeric(albumNumber) && isNumeric(currentSemester)) {
            studentModel.setId(Integer.parseInt(albumNumber));
            studentModel.setFirstName(firstName);
            studentModel.setSecondName(secondName);
            studentModel.setAlbumNumber(Integer.parseInt(albumNumber));
            studentModel.setEmail(email);
            studentModel.setCurrentSemester(Integer.parseInt(currentSemester));
            studentModel.setDegree(degree);
            studentModel.setStudentSubjectsModelList(studentSubjectsModels);
            studentModel.setCourse(course);
            mongodbStudentDAO.saveStudent(studentModel);
        } else {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Numer albumu oraz aktualny semestr musi być liczbą!|@"));
        }
    }

    /**
     * Deleting student.
     *
     * @throws IOException Terminal does not support buffer.
     */
    public void removeStudent() throws IOException {
        System.out.print("\n");
        System.out.println("Podaj numer albumu:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        if (isNumeric(input)) {
            mongodbStudentDAO.removeStudentByAlbumNumber(Integer.parseInt(input));
            waitForEnter();
        } else {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Menu przyjmuje tylko liczby!|@"));
        }
    }


    /**
     * Updating student grades.
     *
     * @throws IOException Terminal does not support buffer.
     */
    public void updateMenuBySubjectHelper() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("\n");
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [1]|@ Zmiana oceny z przedmiotu, typ: Wyklad"));
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [3]|@ Zmiana oceny z przedmiotu, typ: Cwiczenia"));
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [4]|@ Zmiana oceny z przedmiotu, typ: Laboratorium"));
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [5]|@ Zmiana oceny z przedmiotu, typ: Projekt"));
        String input = br.readLine();
        if (isNumeric(input)) {
            updateMenuBySubject(Integer.parseInt(input));
        }
    }

    /**
     * Updating student grades.
     *
     * @param option Selection of operations.
     * @throws IOException Terminal does not support buffer.
     */
    public void updateMenuBySubject(int option) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("\n");
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(white) Menu zmiany oceny|@"));

        System.out.println("Numer albumu:");
        String albumNumber = br.readLine();
        System.out.println("Nazwa przedmiotu:");
        String subject = br.readLine();
        System.out.println("Nowa ocena:");
        String grade = br.readLine();

        try {
            if (subject == null || !isFloat(grade) || !isNumeric(albumNumber)) {
                System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Menu przyjmuje tylko liczby albo brak danych!|@"));
                return;
            }
        } catch (NullPointerException e) {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Menu przyjmuje tylko liczby albo brak danych!|@"));
            return;
        }

        switch (option) {

            case 1:
                mongodbStudentDAO.setStudentGradesByAlbumNumberAndSubjectNameAndLectureType(Integer.parseInt(albumNumber), subject, Float.parseFloat(grade));
                break;
            case 2:
                mongodbStudentDAO.setStudentGradesByAlbumNumberAndSubjectNameAndDiscussionType(Integer.parseInt(albumNumber), subject, Float.parseFloat(grade));
                break;
            case 3:
                mongodbStudentDAO.setStudentGradesByAlbumNumberAndSubjectNameAndLaboratoryType(Integer.parseInt(albumNumber), subject, Float.parseFloat(grade));
                break;
            case 4:
                mongodbStudentDAO.setStudentGradesByAlbumNumberAndSubjectNameAndIndependentStudyType(Integer.parseInt(albumNumber), subject, Float.parseFloat(grade));
                break;
        }
    }

    /**
     * Method of menu printing.
     */
    public void searchMenuBySubjectHelper() throws IOException {
        System.out.print("\n");
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [1]|@ Wyszukiwanie studentów, którzy uzyskali ocene z przedmiotu, typ: Wyklad"));
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [2]|@ Wyszukiwanie studentów, którzy uzyskali ocene z przedmiotu, typ: Cwiczenia"));
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [3]|@ Wyszukiwanie studentów, którzy uzyskali ocene z przedmiotu, typ: Laboratorium"));
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [4]|@ Wyszukiwanie studentów, którzy uzyskali ocene z przedmiotu, typ: Projekt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        searchMenuBySubject(Integer.parseInt(input));
    }

    /**
     * Student searches by subject and grade.
     *
     * @param option Selection of operations.
     * @throws IOException Terminal does not support buffer.
     */
    public void searchMenuBySubject(Integer option) throws IOException {
        System.out.print("\n");
        System.out.println("Podaj nazwe przdmiotu:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String subjectName = br.readLine();
        System.out.print("\n");
        System.out.println("Podaj ocenę z przdmiotu:");
        String grade = br.readLine();

        if (isNumeric(grade)) {
            switch (option) {
                case 1:
                    mongodbStudentDAO.findAllBySubjectsNameAndLectureGrades(subjectName, Integer.parseInt(grade));
                    waitForEnter();
                    break;
                case 2:
                    mongodbStudentDAO.findAllBySubjectsNameAndDiscussionGrades(subjectName, Integer.parseInt(grade));
                    waitForEnter();
                    break;
                case 3:
                    mongodbStudentDAO.findAllBySubjectsNameAndLaboratoryGrades(subjectName, Integer.parseInt(grade));
                    waitForEnter();
                    break;
                case 4:
                    mongodbStudentDAO.findAllBySubjectsNameAndIndependentStudyGrades(subjectName, Integer.parseInt(grade));
                    waitForEnter();
                    break;
            }
        } else {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Menu przyjmuje tylko liczby!|@"));
        }
    }

    /**
     * Search student by group.
     *
     * @throws IOException Terminal does not support buffer.
     */
    public void findAllBySubjectsNameAndGroup() throws IOException {

        System.out.print("\n");
        System.out.println("Podaj nazwę przedmiotu:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String subject = br.readLine();
        System.out.println("Podaj grupe dziekańską:");
        String group = br.readLine();
        try {
            if (subject == null || group == null) {
                System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Bledne dane!|@"));
                return;
            }
        } catch (NullPointerException e) {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Bledne dane!|@"));
            return;
        }
        mongodbStudentDAO.findAllBySubjectsNameAndGroup(subject, group);

    }

    /**
     * Search student by album number.
     *
     * @throws IOException Terminal does not support buffer.
     */
    public void findStudentsByAlbumNumber() throws IOException {
        System.out.print("\n");
        System.out.println("Podaj numer albumu:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        if (isNumeric(input)) {
            mongodbStudentDAO.findOneByAlbumNumber(Integer.parseInt(input));
            waitForEnter();
        } else {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Menu przyjmuje tylko liczby!|@"));
        }
    }

    /**
     * Adding a new subject to the student.
     *
     * @return List of subjects.
     * @throws IOException Terminal does not support buffer.
     */
    public List<StudentSubjectsModel> addNewSubjects() throws IOException {
        List<StudentSubjectsModel> studentSubjectsModels = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(white) Menu dodawania przedmiotów|@"));
        for (; ; ) {
            System.out.println("Nazwa przedmiotu:");
            String name = br.readLine();
            System.out.println("Grupa dziekańska:");
            String group = br.readLine();
            System.out.println("Ocena z wykladu:");
            String lecture = br.readLine();
            System.out.println("Ocena z laboratorium:");
            String laboratory = br.readLine();
            System.out.println("Ocena z ćwiczeń:");
            String discussion = br.readLine();
            System.out.println("Ocena z projektu:");
            String independentStudy = br.readLine();

            try {
                if (name == null || group == null || lecture == null || laboratory == null || discussion == null || independentStudy == null) {
                    System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Dane nie mogą być puste!|@"));
                    return null;
                }
            } catch (NullPointerException e) {
                System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Dane nie mogą być puste!|@"));
            }

            if (isFloat(lecture) && isFloat(laboratory) && isFloat(discussion) && isFloat(independentStudy)) {
                studentSubjectsModels.add(new StudentSubjectsModel(name, group, Float.parseFloat(lecture), Float.parseFloat(laboratory), Float.parseFloat(discussion), Float.parseFloat(independentStudy)));
            } else {
                System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Oceny muszą być liczbą!|@"));
                return null;
            }

            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(white) Dodawanie kolejnego przedmiotu Y/N?|@"));
            String nextSubject = br.readLine();
            if (nextSubject.equals("N")) {
                return studentSubjectsModels;
            }
        }
    }

    /**
     * Deleting  lecturer.
     *
     * @throws IOException Terminal does not support buffer.
     */
    public void removeLecturer() throws IOException {
        System.out.print("\n");
        System.out.println("Podaj adres email:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        mongodbStudentDAO.removeLecturerByID(input);
    }

    private final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    /**
     * Checking if string is a number.
     *
     * @param strNum String to check.
     * @return True of false.
     */
    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    public static void waitForEnter() throws IOException {
        //TODO Dodać obsługę czekania
    }


    /**
     * Adding a new lecturer.
     *
     * @throws IOException Terminal does not support buffer.
     */
    public void addNewLecturer() throws IOException {
        LecturerModel lecturerModel = new LecturerModel();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(white) Menu dodawania nowego wykładowcy|@"));
        System.out.print("\n");
        System.out.println("Imie:");
        String firstName = br.readLine();
        System.out.println("Nazwisko:");
        String secondName = br.readLine();
        System.out.println("Adres email:");
        String email = br.readLine();
        System.out.println("Aktualny stopień naukowy:");
        String degree = br.readLine();
        List<LecturerSubjectModel> studentSubjectsModels = new ArrayList<>();

        for (; ; ) {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(white) Menu dodawania przedmiotów|@"));
            for (; ; ) {

                System.out.println("Nazwa przedmiotu:");
                String name = br.readLine();
                System.out.println("Grupa dziekańska:");
                String group = br.readLine();
                System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [1]|@ Prowadzony przedmiot: Wyklad"));
                System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [2]|@ Prowadzony przedmiot: Cwiczenia"));
                System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [3]|@ Prowadzony przedmiot: Laboratorium"));
                System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [4]|@ Prowadzony przedmiot: Projekt"));
                String type = br.readLine();
                try {
                    if (name == null || group == null || !isNumeric(type)) {
                        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Bledne dane!|@"));
                    }
                } catch (NullPointerException e) {
                    System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Bledne dane!|@"));
                }

                switch (type) {
                    case "1":
                        type="Wyklad";
                        break;
                    case "2":
                        type="Cwiczenia";
                        break;
                    case "3":
                        type="Laboratorium";
                        break;
                    case "4":
                        type="Projekt";
                        break;
                    default:
                        break;
                }
                studentSubjectsModels.add(new LecturerSubjectModel(name,group,type));
                System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(white) Dodawanie kolejnego przedmiotu Y/N?|@"));
                String nextSubject = br.readLine();
                if (nextSubject.equals("N")) {
                    break;
                }
            }

            try {
                if (firstName == null || secondName == null || email == null || degree == null || studentSubjectsModels.isEmpty()) {
                    System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Dane nie mogą być puste!|@"));
                    return;
                }
            } catch (NullPointerException e) {
                System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Dane nie mogą być puste!|@"));
                return;
            }
            lecturerModel.setId(email);
            lecturerModel.setFirstName(firstName);
            lecturerModel.setSecondName(secondName);
            lecturerModel.setEmail(email);
            lecturerModel.setDegree(degree);
            lecturerModel.setLecturerSubjectModelList(studentSubjectsModels);
            System.out.println( lecturerModel.toString());
            mongodbStudentDAO.saveLecturer(lecturerModel);
            break;
        }
    }

    /**
     * Checking if string is a float.
     *
     * @param strNum String to check.
     * @return True of false.
     */
    public boolean isFloat(String strNum) {
        try {
            Float.parseFloat(strNum);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
