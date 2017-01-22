package com.MCleaner;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("Please enter full path to the folder which you want to clean:");
        Scanner s = new Scanner(System.in);
        String path = s.nextLine();
        System.out.println("Are you sure that \"" + path + "\" is the correct path?\nType \"yes\" to confirm. " +
                "Type anything else to quit");
        String confirmation = s.nextLine().toLowerCase();
        if (confirmation.equals("yes")) {
            Directory directory = new Directory(path);
            System.out.println("If you want to EXECUTE actions of the program please type \"execute\".\n " +
                    "Be aware that actions performed by this program cannot be reverted! " +
                    "Type \"simulate\" to SIMULATE operations.");
            String simOrExec = s.nextLine().toUpperCase();
            try {
                FileOperation.Mode mode = FileOperation.Mode.valueOf(simOrExec);
                FileOperation oper = new FileOperation(directory);
                oper.cleanDirectory(mode);
                oper.renameFolders(mode);
            } catch ( IllegalArgumentException e ) {
                System.err.println( "No such mode, type EXECUTE or SIMULATE next time" );
            }

        } else {
            System.out.println("Have a nice day!");
        }
    }
}



