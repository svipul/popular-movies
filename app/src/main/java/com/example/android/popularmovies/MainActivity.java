package com.example.android.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.MenuItemHoverListener;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.popularmovies.utilities.Movie;
import com.example.android.popularmovies.utilities.MovieResults;
import com.example.android.popularmovies.utilities.MoviedbApi;

import java.util.ArrayList;

import static com.example.android.popularmovies.utilities.MoviedbApi.MovieApiCall.POPULAR;
import static com.example.android.popularmovies.utilities.MoviedbApi.MovieApiCall.TOP_RATED;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mMovieGrid;
    private MoviePosterAdapter mMoviePosterAdapter;
    private MoviedbApi.MovieApiCall DEFAULT_SORT_OPTION = POPULAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieGrid = findViewById(R.id.rv_movie_grid);

        GridLayoutManager layoutManager = new GridLayoutManager(mMovieGrid.getContext(), 2);
        mMovieGrid.setLayoutManager(layoutManager);

        mMoviePosterAdapter = new MoviePosterAdapter();
        mMovieGrid.setAdapter(mMoviePosterAdapter);

        mMoviePosterAdapter.setSortOrder(DEFAULT_SORT_OPTION);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        switch(DEFAULT_SORT_OPTION) {
            case POPULAR:
                checkMenuItem(menu.getItem(0), POPULAR);
                break;
            case TOP_RATED:
                checkMenuItem(
                        menu.getItem(1), TOP_RATED);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!item.isChecked()) {
            switch (item.getItemId()) {
                case R.id.sort_popular:
                    checkMenuItem(item, POPULAR);
                    break;
                case R.id.sort_top_rated:
                    checkMenuItem(item, TOP_RATED);
                    break;
            }
            return true;
        } else {
            return false;
        }
    }

    private void checkMenuItem(MenuItem item, MoviedbApi.MovieApiCall call) {
        item.setChecked(true);
        mMoviePosterAdapter.setSortOrder(call);
    }
}
