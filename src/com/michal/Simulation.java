package com.michal;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.util.Collection;
import java.util.Map;

/**
 * Created by michal on 21.01.17.
 */
public class Simulation implements FileOperations {
    private Directory directory;

    public Simulation(Directory directory) {
        this.directory = directory;
    }

    @Override
    public void cleanDirectory() {
        Collection<File> movieFilesAndDirs = directory.getMovieFilesAndDirs();
        Collection<File> allFilesAndFolders = FileUtils.listFilesAndDirs(directory.getDirectoryAsFile(), TrueFileFilter.TRUE,
                TrueFileFilter.TRUE);

        removeSamples(movieFilesAndDirs);

        for (File file : allFilesAndFolders) {
            if (!movieFilesAndDirs.contains(file)) {
                if (file.canWrite()) {
                    if (file.isDirectory()) {
                        System.out.println("Program will delete directoryFile: " + file.getName());
                    } else {
                        // file isn't directoryFile so can be deleted with Files.deleteIfExists
                        System.out.println("Program will delete: " + file.getName());
                    }
                } else {
                    System.out.println("File: " + file.getName() + " is read-only, can't access");

                }
            }
        }
    }

    @Override
    public void renameFolders() {
        for (Map.Entry<File, Movie> entry : directory.getFilesAndMovies().entrySet()) {
            File folderToRename = new File(entry.getKey().getParentFile().getAbsolutePath());
            Movie movie = entry.getValue();

            File newName = directory.createNewFolderName(folderToRename, movie);
            System.out.println("Program will rename folder: " + folderToRename.getName() + " to: " + newName.getName());
        }
    }

    @Override
    public void removeSamples(Collection<File> movieFilesAndDirs) {
        for (File file : movieFilesAndDirs) {
            // first removing all the sample files (size less than 200Mb)
            if (file.length() < 200000000) {
                if (!file.isDirectory()) {
                    if (file.canWrite()) {
                        System.out.println("Program will delete sample: " + file.getName());
                    } else {
                        System.out.println("File is read-only");
                    }
                }
            }
        }
    }


}
