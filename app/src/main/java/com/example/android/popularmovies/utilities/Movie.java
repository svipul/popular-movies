package com.example.android.popularmovies.utilities;

/**
 * Created by smccabe on 12/13/17.
 */

public final class Movie {
    private String originalTitle;
    private String posterPath;
    private String overview;
    private String releaseDate;
    private Double voteAverage;

    public static String ORIGINAL_TITLE_NAME = "com.example.android.popularmovies.ORIGINAL_TITLE";
    public static String POSTER_PATH_NAME = "com.example.android.popularmovies.POSTER_PATH";
    public static String OVERVIEW_NAME = "com.example.android.popularmovies.OVERVIEW";
    public static String RELEASE_DATE_NAME = "com.example.android.popularmovies.RELEASE_DATE";
    public static String VOTE_AVERAGE_NAME = "com.example.android.popularmovies.VOTE_AVERAGE";

    public Movie (String pOriginalTitle, String pPoster, String pOverview, String pReleaseDate, Double pVoteAverage) {
        originalTitle = pOriginalTitle;
        posterPath = pPoster;
        overview = pOverview;
        releaseDate = pReleaseDate;
        voteAverage = pVoteAverage;
    }

    public Double getRating() {
        return voteAverage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }
}