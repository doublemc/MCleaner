package com.michal;

import java.util.regex.Pattern;

/**
 * Created by michal on 19.01.17.
 */
public class Folder {

    private String name;

    public static final String NAME_REGEX = "([ -\\.\\w'\\[\\]]+?)(\\W?[0-9]{4}\\.?.*)(avi|flv|mkv|mp4)";
    public static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);

    public Folder(String name) {
        this.name = name;
    }
}
