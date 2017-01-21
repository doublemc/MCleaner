package com.michal;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        System.out.println("Please enter full path to the folder which you want to clean:");
        Scanner s = new Scanner(System.in);
        String path = s.nextLine();
        System.out.println("Are you sure that \"" + path + "\" is the correct path?\nType \"yes\" to confirm.");
        String confirmation = s.nextLine();
        if (confirmation.equals("yes")) {
            Directory directory = new Directory(path);
            System.out.println("If you want to EXECUTE actions of the program please type \"execute\".\n " +
                    "Be aware that actions performed by this program cannot be reverted! " +
                    "Type anything else to SIMULATE");
            String simOrExec = s.nextLine();
            if (simOrExec.equals("execute")) {
                Execution exec = new Execution(directory);
                exec.cleanDirectory();
            } else {
                Simulation sim = new Simulation(directory);
                sim.cleanDirectory();
            }

        } else {
            System.out.println("Have a nice day!");
        }
    }
}



