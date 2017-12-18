package com.example.android.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.utilities.Movie;
import com.example.android.popularmovies.utilities.MovieResults;
import com.example.android.popularmovies.utilities.MoviedbApi;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.startActivity;
import static com.example.android.popularmovies.utilities.MoviedbApi.MovieApiCall.POPULAR;
import static com.example.android.popularmovies.utilities.MoviedbApi.MovieApiCall.TOP_RATED;

/**
 * Created by smccabe on 12/13/17.
 */

public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.PosterViewHolder> {
    private MovieResults popularMovies = new MovieResults(POPULAR);
    private MovieResults topRatedMovies = new MovieResults(TOP_RATED);
    private MovieResults currentResults = popularMovies;

    @Override
    public PosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View thisItemsView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_cell, parent, false);

        return new PosterViewHolder(thisItemsView);
    }

    @Override
    public void onBindViewHolder(PosterViewHolder holder, int position) {
        if (position +1 == currentResults.movies.size()) {
            new FetchMoviedbData().execute();
        } else {
            ImageView imageView = holder.getGridItem();
            Movie movie = currentResults.movies.get(position);
            holder.setMovieNumber(position);
            Picasso.with(holder.mPosterImage.getContext())
                    .load(MoviedbApi.getPosterUrl(MoviedbApi.MovieApiCall.IMAGE, movie.getPosterPath()))
                    .into(imageView);
        }
    }

    @Override
    public int getItemCount() {
        return currentResults.movies.size();
    }

    class PosterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView mPosterImage;
        private int movieNumber;

        public PosterViewHolder(View v) {
            super(v);
            mPosterImage = (ImageView) v.findViewById(R.id.poster_image);
            mPosterImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    posterClicked(v);
                }
            });

        }

        public ImageView getGridItem() { return mPosterImage; }

        @Override
        public void onClick(View v) {
            Log.v("BUTTON", "buttoned");
        }

        public void setMovieNumber(int number) {
            this.movieNumber = number;
        }
        public int getMovieNumber() {
            return this.movieNumber;
        }

        private void posterClicked(View v){
            Intent intent = new Intent(v.getContext(), MovieDetailActivity.class);
            Movie thisViewsMovie = currentResults.movies.get(getMovieNumber());
            intent.putExtra(Movie.ORIGINAL_TITLE_NAME, thisViewsMovie.getOriginalTitle());
            intent.putExtra(Movie.POSTER_PATH_NAME, thisViewsMovie.getPosterPath());
            intent.putExtra(Movie.OVERVIEW_NAME, thisViewsMovie.getOverview());
            intent.putExtra(Movie.RELEASE_DATE_NAME, thisViewsMovie.getReleaseDate());
            intent.putExtra(Movie.VOTE_AVERAGE_NAME, thisViewsMovie.getRating());
            v.getContext().startActivity(intent);
        }
    }

    public class FetchMoviedbData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Log.v("boO", "tig");
            try {
                currentResults.fetchMovieResults();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            notifyDataSetChanged();
        }
    }

    public void setSortOrder(MoviedbApi.MovieApiCall orderBy) {
        switch(orderBy) {
            case POPULAR:
                currentResults = popularMovies;
                break;
            case TOP_RATED:
                currentResults = topRatedMovies;
                break;
        }
        if (currentResults.movies.isEmpty()) {
            new FetchMoviedbData().execute();
        } else {
            notifyDataSetChanged();
        }
    }
}
