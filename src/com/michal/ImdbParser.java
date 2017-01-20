package com.michal;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import java.io.IOException;
import java.net.URL;

/**
 * Created by michal on 20.01.17.
 */
public class ImdbParser {

    Movie movie;

    private static final String API_LINK_PART_ONE = "http://www.omdbapi.com/?t=";
    // between those two Strings comes name of the movie and it is then fully working link to the IMDB api
    private static final String API_LINK_PART_TWO = "&y=&plot=short&r=xml";

    // parses IMDB page and sets releaseDate, genre and imdbRating in Movie object
    public void parseImbd(Movie movieToParse) throws IOException {
        String xmlLink = createXmlLink(movieToParse);

        // using "new Url..." because my xml is on the web, not on my disk
        Document doc = Jsoup.parse(new URL(xmlLink).openStream(), "UTF-8", "", Parser.xmlParser());

        Element movieFromXml = doc.select("movie").first();
        if (movieFromXml != null) {

            // using array to extract only last genre name - usually the most substantive one
            String[] genreArray = movieFromXml.attr("genre").split(", ");
            movieToParse.setGenre(genreArray[genreArray.length - 1]);

            movieToParse.setImdbRating(Float.parseFloat(movieFromXml.attr("imdbRating")));

            // using array to extract only year of release
            String[] dateArray = movieFromXml.attr("released").split(" ");
            movieToParse.setReleaseYear(Integer.parseInt(dateArray[2]));
        } else {
            System.out.println("Can't find movie: " + movieToParse.getTitle() + " on IMDB");
        }

    }

    private String createXmlLink(Movie movie) {
        StringBuilder sb = new StringBuilder(API_LINK_PART_ONE);
        // need to replace spaces in movie names to "+" - api works that way
        String namePassedToXml = movie.getTitle().trim().replaceAll(" ", "+");
        sb.append(namePassedToXml);
        sb.append(API_LINK_PART_TWO);
        return sb.toString();
    }


}
