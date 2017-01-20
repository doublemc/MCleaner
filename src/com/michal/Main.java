package com.michal;

public class Main {


    public static void main(String[] args) {

        String path = "/run/media/michal/F04AA6E24AA6A536/Filmy/";
        Directory directory = new Directory(path);


        directory.removeAllTheCrap();
        directory.createMovieObjs();
        directory.renameFolders();
    }

}
