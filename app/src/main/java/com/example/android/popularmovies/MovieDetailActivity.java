package com.example.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.utilities.Movie;
import com.example.android.popularmovies.utilities.MoviedbApi;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {
    ImageView mLargePosterView;
    TextView mTitle;
    TextView mDate;
    TextView mRating;
    TextView mOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();
        mLargePosterView = findViewById(R.id.poster_view);
        mTitle = findViewById(R.id.movie_detail_title);
        mDate = findViewById(R.id.movie_detail_date);
        mRating = findViewById(R.id.movie_detail_rating);
        mOverview = findViewById(R.id.movie_detail_overview);

        String titleText = intent.getStringExtra(Movie.ORIGINAL_TITLE_NAME);
        String dateText = intent.getStringExtra(Movie.RELEASE_DATE_NAME);
        String ratingText = String.format("%s/10",
                Double.toString(intent.getDoubleExtra(Movie.VOTE_AVERAGE_NAME, 0)));
        String overviewText = intent.getStringExtra(Movie.OVERVIEW_NAME);

        Picasso.with(mLargePosterView.getContext())
                .load(MoviedbApi.getPosterUrl(MoviedbApi.MovieApiCall.IMAGE_LARGE,
                        intent.getStringExtra(Movie.POSTER_PATH_NAME)))
                .into(mLargePosterView);

        mTitle.setText(titleText);
        mDate.setText(dateText);
        mRating.setText(ratingText);
        mOverview.setText(overviewText);
    }
}
