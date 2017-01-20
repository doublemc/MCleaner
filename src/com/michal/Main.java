package com.michal;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {


        System.out.println("Please enter full path to the folder which you want to clean:");
        Scanner s = new Scanner(System.in);
        String path = s.nextLine();
        System.out.println("Are you sure that \"" + path + "\" is the correct path?\nBe aware tat actions performed " +
                "by this program CANNOT be reverted!\nEnter \"yes\" to confirm, \"no\" to abort");
        String confirmation = s.nextLine();
        if (confirmation.equals("yes")) {
            Directory directory = new Directory(path);

            directory.removeAllTheCrap();
            directory.createMovieObjs();
            directory.renameFolders();
        } else {
            System.out.println("Have a nice day!");
        }

    }

}
