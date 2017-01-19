package com.michal;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by michal on 19.01.17.
 */
public class ProcessDirectory {
    private File directory;
    public static final String [] MOVIE_EXTENSIONS = {"avi", "mp4", "flv", "mkv"};


    public ProcessDirectory(String path) {
        this.directory = new File(path);
    }

    // returns all movie files in this ProcessDirectory object
    private Collection<File> findMovieFiles() throws IOException {
        return FileUtils.listFiles(this.directory, MOVIE_EXTENSIONS, true);
    }

    public void removeAllNonMovieFiles() throws IOException {
        Collection<File> movieFiles = findMovieFiles();
        Collection<File> allFilesAndFolders = FileUtils.listFilesAndDirs(this.directory, TrueFileFilter.TRUE, TrueFileFilter.TRUE);

        // have to use Iterator because otherwise it throws ConcurrentModificationException
        Iterator<File> iter = allFilesAndFolders.iterator();
        while (iter.hasNext()) {
            File currentElement = iter.next();
            if (!movieFiles.contains(currentElement)) {
                iter.remove();
            }
        }

    }

}
