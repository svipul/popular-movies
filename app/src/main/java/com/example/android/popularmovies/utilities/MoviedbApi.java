package com.example.android.popularmovies.utilities;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by smccabe on 12/13/17.
 */

public class MoviedbApi {
    final static String MOVIEDB_API_PARAM =
            "api_key";
    final static String MOVIEDB_API_KEY =
                MoviedbApiKey.KEY;          // Overwrite this with a string for your own API key
    final static String MOVIEDB_PAGE_PARAM =
            "page";

    public enum MovieApiCall {
        POPULAR ("http://api.themoviedb.org/3/movie/popular"),
        TOP_RATED ("http://api.themoviedb.org/3/movie/top_rated"),
        IMAGE ("http://image.tmdb.org/t/p/w185//"),
        IMAGE_LARGE ("http://image.tmdb.org/t/p/w342//");

        private final String url;
        MovieApiCall(String url) {
            this.url = url;
        }

        String getUrl() {
            return url;
        }
    }

    /**
     * Build a url for a MovieDB data API call
     * @param callType The sorting type used in the call, either POPULAR or TOP_RATED
     * @return Full url of the call
     */

    private static URL buildUrl(MovieApiCall callType) {
        Uri builtUri = Uri.parse(callType.getUrl()).buildUpon()
                .appendQueryParameter(MOVIEDB_API_PARAM, MOVIEDB_API_KEY)
                .build();

        return uriToUrl(builtUri);
    }

    /**
     * Build a url for a MovieDB data API call
     * @param callType The sorting type used in the call, either POPULAR or TOP_RATED
     * @return Full url of the call
     */

    private static URL buildUrl(MovieApiCall callType, int page) {
        Uri builtUri = Uri.parse(callType.getUrl()).buildUpon()
                .appendQueryParameter(MOVIEDB_API_PARAM, MOVIEDB_API_KEY)
                .appendQueryParameter(MOVIEDB_PAGE_PARAM, Integer.toString(page))
                .build();

        return uriToUrl(builtUri);
    }

    /**
     * Build a url for an MovieDB image API call
     * @param posterPath Path to a movie poster image
     * @return Full url of the image
     */

    private static URL buildUrl(MovieApiCall callType, String posterPath) {

        Uri builtUri = Uri.parse(callType.getUrl() + posterPath).buildUpon()
                .build();
        return uriToUrl(builtUri);
    }

    private static URL uriToUrl(Uri uri) {
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String makeMovieQuery(MovieApiCall sortType, int page) {
        URL url = buildUrl(sortType, page);
        try {
            return getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Movie> getMovieResults(MovieApiCall sortType) {
        URL url = buildUrl(sortType);
        try {
            return makeMovieList(getResponseFromHttpUrl(url));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getPosterUrl(MovieApiCall apiCall, String posterPath) {
        return buildUrl(apiCall, posterPath).toString();
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    private static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    private static ArrayList<Movie> makeMovieList(String responseJson) {
        ArrayList<Movie> movies = new ArrayList<Movie>();

        try {
            JSONObject apiResponse = new JSONObject(responseJson);
            JSONArray moviesJson = apiResponse.getJSONArray("results");
            for (int i = 0; i < moviesJson.length(); i++) {
                JSONObject movieJSON = moviesJson.getJSONObject(i);
                movies.add(new Movie(movieJSON.getString("original_title"),
                        movieJSON.getString("poster_path"),
                        movieJSON.getString("overview"),
                        movieJSON.getString("release_date"),
                        movieJSON.getDouble("vote_average")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }
}