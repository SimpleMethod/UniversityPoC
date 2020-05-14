package com.simplemethod.university;


import com.simplemethod.university.couchbaseDAO.CouchbaseStudentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

@Controller
public class MainView {

    @Autowired
    CouchbaseStudentDAO couchbase;

    public void menu() throws IOException {
        for (;;)
        {
            searchMenu();
        }
    }

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
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red)  [8]|@ Zamknięcie programu"));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input =  br.readLine();
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
                    updateMenuBySubject();
                    break;
                case 7:
                    RemoveUser();
                    break;
                case 8:
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
     *
     */
    public void findAllStudentsMenu() {
        //1
    }


    public void findStudentsByAlbumNumber() throws IOException {
        System.out.print("\n");
        System.out.println("Podaj numer albumu:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input =  br.readLine();
        if (isNumeric(input)) {

        }
        else
        {
            System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(red) Menu przyjmuje tylko liczby!|@"));
        }
    }
        //2


    /**
     *
     */
    public void searchMenuBySubjectHelper() {
        //3
        System.out.print("\n");
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [1]|@ Wyszukiwanie studentów, którzy uzyskali ocen? z przedmiotu, typ: Wyk?ad"));
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [3]|@ Wyszukiwanie studentów, którzy uzyskali ocen? z przedmiotu, typ: ?wiczenia"));
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [4]|@ Wyszukiwanie studentów, którzy uzyskali ocen? z przedmiotu, typ: Laboratorium"));
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [5]|@ Wyszukiwanie studentów, którzy uzyskali ocen? z przedmiotu, typ: Projekt"));

    }

    public void searchMenuBySubject(Integer option) {
        //3
    }

    public void addNewStudent() {
        //4
    }

    public void setStudentByEmail()
    {
        //5
    }

    public void updateMenuBySubject() {
        //6
        System.out.print("\n");
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [1]|@ Zmiana oceny z przedmiotu, typ: Wyk?ad"));
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [3]|@ Zmiana oceny z przedmiotu, typ: ?wiczenia"));
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [4]|@ Zmiana oceny z przedmiotu, typ: Laboratorium"));
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,fg(blue)  [5]|@ Zmiana oceny z przedmiotu, typ: Projekt"));

    }

    public void RemoveUser() {
        //7
    }


    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
}
