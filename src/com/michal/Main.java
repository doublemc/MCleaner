package com.michal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;

public class Main {


    public static void main(String[] args) {

        String path = "/run/media/michal/F04AA6E24AA6A536/Filmy/FilmyTest/";
        ProcessDirectory directory = new ProcessDirectory(path);

        try {
            directory.removeAllNonMovieFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
//         removes all the sample files (filesize < 200MB) from videoFiles
//        videoFiles.removeIf(movie -> movie.length() < 200000000);



//        // creating map to connect File objs and Movie objs so can change name of dirs later on
//        Map<File, Movie> mappedFilesToMovies = new HashMap<>();
//
//        // movieArrayList contains all the movie objects with correct names
//        List<Movie> movieArrayList = createMovieObjs(videoFiles, mappedFilesToMovies);
//        for (Movie movie : movieArrayList) {
//            System.out.println(movie.getTitle());
//        }
//        // TODO: 18.01.17  Remove all extra, unnecessary files, leave just .avi
//        // TODO: 18.01.17 Change directories' names to approprietary values (name + releaseYear + imdbRating + genre)
//
//        // want to parse genre, release year and imdbrating for every Movie object
////        for (Movie movie : movieArrayList) {
////            try {
////                movie.imdbParser();
////            } catch (IOException e) {
////                System.out.println("Parsing failed: " + e);
////            }
////        }
//

    }

    // returns a list containing movie objects with correct names
    private static ArrayList<Movie> createMovieObjs(Collection<File> videoFiles, Map<File, Movie> mappedFilesToMovies) {
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        Matcher matcher;

        for (File file : videoFiles) {
            matcher = Movie.NAME_PATTERN.matcher(file.getName());
            while (matcher.find()) {
                String movieName = matcher.group(1).replaceAll("\\.", " ");
                Movie movie = new Movie(movieName);
                mappedFilesToMovies.put(file, movie);

                if (!movieArrayList.contains(movie)) {
                    movieArrayList.add(movie);
                }
            }
        }
        return movieArrayList;

    }
}

