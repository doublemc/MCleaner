package com.MCleaner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.regex.Matcher;

/**
 * Created by MCleaner on 19.01.17.
 */
public class Directory {
    private File directoryAsFile;
    private Collection<File> movieFilesAndDirs;
    private HashMap<File, Movie> filesAndMovies;

    // .srt and .txt are subtitle extensions - don't want to remove them
    public final String FILE_REGEX = "([ -\\.\\w'\\[\\]]+?)(\\W?[0-9]{4}\\.?.*)(avi|flv|mkv|mp4|srt|txt)";
    public final String FOLDER_REGEX = "([ -\\.\\w'\\[\\]]+?)(\\W?[0-9]{4}\\.?.*)";

    // creating IOFileFilters for FileUtils.listFilesAndDirs
    private final IOFileFilter fileFilter = new RegexFileFilter(FILE_REGEX);
    private final IOFileFilter dirFilter = new RegexFileFilter(FOLDER_REGEX);


    public Directory(String path) {
        if ((new File(path).exists()) && new File(path).isDirectory()) {
            this.directoryAsFile = new File(path);
        } else {
            throw new IllegalArgumentException("Given path doesn't exist or isn't a directory");
        }
        this.movieFilesAndDirs = findMovieFilesAndDirs();
        this.filesAndMovies = mapMoviesToObjs();
    }

    public File getDirectoryAsFile() {
        return directoryAsFile;
    }

    public HashMap<File, Movie> getFilesAndMovies() {
        return filesAndMovies;
    }

    public Collection<File> getMovieFilesAndDirs() {
        return movieFilesAndDirs;
    }

    private HashMap<File, Movie> mapMoviesToObjs() {
        HashMap<File, Movie> map = new HashMap<>();
        for (File file : findMovieFilesAndDirs()) {
            Matcher matcher = Movie.NAME_PATTERN.matcher(file.getName());
            while (matcher.find()) {
                String movieName = matcher.group(1).replaceAll("\\.", " ");
                Movie movie = new Movie(movieName);
                map.put(file, movie);
            }
        }
        return map;
    }

    public File createNewFolderName(File folderToRename, Movie movie) {
        StringBuilder folderName = new StringBuilder(movie.getTitle());
        try {
            ImdbParser parser = new ImdbParser();
            if (parser.fillMovieInfo(movie)) {
                folderName.append(" ");
                folderName.append(movie.getReleaseYear());
                folderName.append(" ");
                folderName.append(movie.getGenre());
                folderName.append(" ");
                folderName.append(movie.getImdbRating());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder completePathToNewFolder = new StringBuilder(folderToRename.getParent());
        completePathToNewFolder.append(System.getProperty("file.separator"));
        completePathToNewFolder.append(folderName);

        return new File(completePathToNewFolder.toString());
    }

    // returns all movie files and directories containing movie files using two IOFileFilters
    private Collection<File> findMovieFilesAndDirs() {
        return FileUtils.listFilesAndDirs(this.directoryAsFile, fileFilter, dirFilter);
    }

}



