package com.example.android.popularmovies.utilities;

import com.example.android.popularmovies.MovieDetailActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.android.popularmovies.utilities.MoviedbApi.MovieApiCall.POPULAR;

/**
 * Created by smccabe on 12/13/17.
 */

public final class MovieResults {
    public ArrayList<Movie> movies = new ArrayList<Movie>(); ;
    private int resultPage = 0;
    private MoviedbApi.MovieApiCall resultType;

    public MovieResults(MoviedbApi.MovieApiCall callType) {
        resultType = callType;
    }

    public void fetchMovieResults() {
        resultPage++;
        processMovieResults(MoviedbApi.makeMovieQuery(resultType, resultPage));
    }

    private void processMovieResults(String responseJson) {
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
    }
}
