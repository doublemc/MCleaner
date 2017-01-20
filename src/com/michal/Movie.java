package com.michal;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Pattern;

/**
 * Created by michal on 17.01.17.
 */
public class Movie {
    public String title;
    private int releaseYear;
    private float imdbRating;
    private String genre;

    private static final String NAME_REGEX = "([ -\\.\\w'\\[\\]]+?)(\\W?[0-9]{4}\\.?.*)(avi|flv|mkv|mp4)";

    public static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);
//    public static final String EXTENSION_REGEX =  "^.*\\.(avi|flv|mkv|mp4)";

    private static final String XML_PART_ONE = "http://www.omdbapi.com/?t=";
    private static final String XML_PART_TWO = "&y=&plot=short&r=xml";


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

    // parses IMDB page and sets releaseDate, genre and imdbRating in Movie objects
    public void parseImbd() throws IOException {
        String xmlLink = createXmlLink();

        // using "new Url..." because my xml is on the web, not on my disk
        Document doc = Jsoup.parse(new URL(xmlLink).openStream(), "UTF-8", "", Parser.xmlParser());

        Element movieFromXml = doc.select("movie").first();
        if (movieFromXml != null) {

            // using array to extract only last genre name - usually the most substantive one
            String[] genreArray = movieFromXml.attr("genre").split(", ");
            this.genre = genreArray[genreArray.length - 1];

            this.imdbRating = Float.parseFloat(movieFromXml.attr("imdbRating"));

            // using array to extract only year of release
            String[] dateArray = movieFromXml.attr("released").split(" ");
            this.releaseYear = Integer.parseInt(dateArray[2]);
        } else {
            System.out.println("Can't find movie: " + this.title + " on IMDB");
        }

    }

    private String createXmlLink() {
        StringBuilder sb = new StringBuilder(XML_PART_ONE);
        // need to replace spaces in movie names to "+" - api works that way
        String namePassedToXml = this.title.trim().replaceAll(" ", "+");
        sb.append(namePassedToXml);
        sb.append(XML_PART_TWO);
        return sb.toString();
    }


}
