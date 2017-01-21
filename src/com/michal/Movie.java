package com.michal;

import java.util.regex.Pattern;

/**
 * Created by michal on 17.01.17.
 */
public class Movie {
    private String title;
    private int releaseYear;
    private float imdbRating;
    private String genre;

    private static final String NAME_REGEX = "([ -\\.\\w'\\[\\]]+?)(\\W?[0-9]{4}\\.?.*)(avi|flv|mkv|mp4)";
    public static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);

    public Movie(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public float getImdbRating() {
        return imdbRating;
    }

    public String getGenre() {
        return genre;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setImdbRating(float imdbRating) {
        this.imdbRating = imdbRating;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}
