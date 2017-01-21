package com.MCleaner;

import java.io.File;
import java.util.Collection;

/**
 * Created by MCleaner on 21.01.17.
 */
public interface FileOperations {
    // removes all files without avi, flv, mkv, mp4, srt, txt extension and directories not containing movies
    void cleanDirectory();

    void renameFolders();

    void removeSamples(Collection<File> movieFilesAndDirs);

}


