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
public class FileOperation {
    private Directory directory;

    enum Mode {
        EXECUTE,
        SIMULATE
    }

    public FileOperation(Directory directory) {
        this.directory = directory;
    }

    void cleanDirectory(Mode mode) {
        Collection<File> movieFilesAndDirs = directory.getMovieFilesAndDirs();
        Collection<File> allFilesAndFolders = FileUtils.listFilesAndDirs(directory.getDirectoryAsFile(), TrueFileFilter.TRUE,
                TrueFileFilter.TRUE);

        removeSamples(movieFilesAndDirs, mode);

        for (File file : allFilesAndFolders) {
            if (!movieFilesAndDirs.contains(file)) {
                // have to check both conditions because isWritableFile only works with files and
                // I need to check for directories here
                if (file.canWrite()) {
                    if (mode == Mode.EXECUTE) {
                        if (file.isDirectory()) {
                            try {
                                // FileUtils.deleteDirectory deletes directoryFile WITH all the files inside
                                FileUtils.deleteDirectory(file);
                                System.out.println("Deleted directory:  " + file.getName());
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
                    } else if (mode == Mode.SIMULATE) {
                        System.out.println("Program will delete: " + file.getName());
                    }
                } else {
                    System.out.println("File: " + file.getName() + " is read-only, can't access");

                }

            }
        }
    }

    void renameFolders(Mode mode) {
        for (Map.Entry<File, Movie> entry : directory.getFilesAndMovies().entrySet()) {
            File folderToRename = new File(entry.getKey().getParentFile().getAbsolutePath());
            Movie movie = entry.getValue();

            File newName = directory.createNewFolderName(folderToRename, movie);
            if (mode == Mode.EXECUTE) {
                folderToRename.renameTo(newName);
            } else if (mode == Mode.SIMULATE) {
                System.out.println("Folder " + folderToRename.getName() + " will be renamed to " + newName.getName());
            }
        }
    }

    private void removeSamples(Collection<File> movieFilesAndDirs, Mode mode) {
        for (File file : movieFilesAndDirs) {
            // first removing all the sample files (size less than 200Mb)
            if (isLessThan20Mb(file)) {
                if (isWritableFile(file)) {
                    if (mode == Mode.EXECUTE) {
                        try {
                            Files.delete(file.toPath());
                            System.out.println("Deleted sample " + file.getName());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (mode == Mode.SIMULATE) {
                        System.out.println("Program will delete sample: " + file.getName());
                    }
                }

            }
        }
    }

    private boolean isLessThan20Mb(File file) {
        return file.length() < 200000000;
    }

    private boolean isWritableFile(File file) {
        if (!file.isDirectory() && file.canWrite()) {
            return true;
        }
        return false;
    }


}
