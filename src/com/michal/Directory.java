package com.michal;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Created by michal on 19.01.17.
 */
public class Directory {
    private File directory;
    public ArrayList<Movie> movies;
    private ArrayList<File> files;
    private HashMap<File, Movie> map = new HashMap<>();

    // .srt and .txt are subtitle extensions - don't want to remove them
    public final String FILE_REGEX = "([ -\\.\\w'\\[\\]]+?)(\\W?[0-9]{4}\\.?.*)(avi|flv|mkv|mp4|srt|txt)";
    public final String FOLDER_REGEX = "([ -\\.\\w'\\[\\]]+?)(\\W?[0-9]{4}\\.?.*)";

    // creating IOFileFilters for FileUtils.listFilesAndDirs
    private final IOFileFilter fileFilter = new RegexFileFilter(FILE_REGEX);
    private final IOFileFilter dirFilter = new RegexFileFilter(FOLDER_REGEX);


    public Directory(String path) {
        this.directory = new File(path);
        this.movies = new ArrayList<>();
        this.files = new ArrayList<>();
    }


    // removes all files without avi, flv, mkv, mp4, srt, txt extension and directories not containing movies
    public void removeAllTheCrap() {
        Collection<File> movieFilesAndDirs = findMovieFilesAndDirs();
        Collection<File> allFilesAndFolders = FileUtils.listFilesAndDirs(this.directory, TrueFileFilter.TRUE, TrueFileFilter.TRUE);

        for (File file : movieFilesAndDirs) {
            // first removing all the sample files (size less than 200Mb)
            if (file.length() < 200000000) {
                if (!file.isDirectory()) {
                    try {
                        Files.delete(file.toPath());
                        System.out.println("Deleted sample " + file.getName());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        for (File file : allFilesAndFolders) {
            if (!movieFilesAndDirs.contains(file)) {
                if (file.isDirectory()) {
                    try {
                        FileUtils.deleteDirectory(file);
                        System.out.println("Deleted directory:  " + file.getName());
                    } catch (IOException x) {
                        x.printStackTrace();
                    }
                } else {
                    try {
                        Files.deleteIfExists(file.toPath());
                        System.out.println("Deleted file: " + file.getName());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

        }

    }


    // returns all movie files and directories in this ProcessDirectory object using two IOFileFilters
    private Collection<File> findMovieFilesAndDirs() {
        return FileUtils.listFilesAndDirs(this.directory, fileFilter, dirFilter);
    }

    public void createMovieObjs() {
        for (File file : findMovieFilesAndDirs()) {
            Matcher matcher = Movie.NAME_PATTERN.matcher(file.getName());
            while (matcher.find()) {
                String movieName = matcher.group(1).replaceAll("\\.", " ");
                Movie movie = new Movie(movieName);
                this.map.put(file, movie);

                if (!this.movies.contains(movie)) {
                    this.movies.add(movie);
                }
            }
        }
    }

    public void renameFolders() {
        for (Map.Entry<File, Movie> entry : this.map.entrySet()) {
            File folderToRename = new File(entry.getKey().getParentFile().getAbsolutePath());
            Movie movie = entry.getValue();

            File newName = createNewFolderName(folderToRename, movie);
            folderToRename.renameTo(newName);

        }
    }

    private File createNewFolderName(File folderToRename, Movie movie) {
        try {
            ImdbParser parser = new ImdbParser();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder folderName = new StringBuilder();
        folderName.append(movie.getTitle());
        folderName.append(" ");
        folderName.append(movie.getReleaseYear());
        folderName.append(" ");
        folderName.append(movie.getGenre());
        folderName.append(" ");
        folderName.append(movie.getImdbRating());

        StringBuilder completePathToNewFolder = new StringBuilder(folderToRename.getParent());
        completePathToNewFolder.append(System.getProperty("file.separator"));
        completePathToNewFolder.append(folderName);

        return new File(completePathToNewFolder.toString());
    }
}
