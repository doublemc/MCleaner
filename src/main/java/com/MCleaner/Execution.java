package com.MCleaner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Map;

/**
 * Created by MCleaner on 21.01.17.
 */
public class Execution implements FileOperations {
    private Directory directory;

    public Execution(Directory directory) {
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
                        try {
                            // FileUtils.deleteDirectory deletes directoryFile WITH all the files inside
                            FileUtils.deleteDirectory(file);
                            System.out.println("Deleted directoryFile:  " + file.getName());
                        } catch (IOException x) {
                            x.printStackTrace();
                        }
                    } else {
                        // file isn't directoryFile so can be deleted with Files.deleteIfExists
                        try {
                            Files.delete(file.toPath());
                            System.out.println("Deleted file: " + file.getName());
                        } catch (IOException x) {
                            x.printStackTrace();
                        }
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
            folderToRename.renameTo(newName);
        }
    }

    @Override
    public void removeSamples(Collection<File> movieFilesAndDirs) {
        for (File file : movieFilesAndDirs) {
            // first removing all the sample files (size less than 200Mb)
            if (file.length() < 200000000) {
                if (!file.isDirectory()) {
                    if (file.canWrite()) {
                        try {
                            Files.delete(file.toPath());
                            System.out.println("Deleted sample " + file.getName());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("File is read-only");
                    }
                }
            }
        }
    }


}
