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

    //    // parses IMDB page and sets releaseDate, genre and imdbRating in Movie objects
//    public void parseImbd() throws IOException {
//        String xmlLink = createXmlLink();
//
//        // using "new Url..." because my xml is on the web, not on my disk
//        Document doc = Jsoup.parse(new URL(xmlLink).openStream(), "UTF-8", "", Parser.xmlParser());
//
//        Element movieFromXml = doc.select("movie").first();
//        if (movieFromXml != null) {
//
//            // using array to extract only last genre name - usually the most substantive one
//            String[] genreArray = movieFromXml.attr("genre").split(", ");
//            this.genre = genreArray[genreArray.length - 1];
//
//            this.imdbRating = Float.parseFloat(movieFromXml.attr("imdbRating"));
//
//            // using array to extract only year of release
//            String[] dateArray = movieFromXml.attr("released").split(" ");
//            this.releaseYear = Integer.parseInt(dateArray[2]);
//        } else {
//            System.out.println("Can't find movie: " + this.title + " on IMDB");
//        }
//
//    }
//
//    private String createXmlLink() {
//        StringBuilder sb = new StringBuilder(XML_PART_ONE);
//        // need to replace spaces in movie names to "+" - api works that way
//        String namePassedToXml = this.title.trim().replaceAll(" ", "+");
//        sb.append(namePassedToXml);
//        sb.append(XML_PART_TWO);
//        return sb.toString();
//    }




}
