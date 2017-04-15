package com.example.android.homecinema;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.homecinema.Utils.Movies;
import com.squareup.picasso.Picasso;

/**
 * Created by Dominic Idagu on 13/04/2017.
 */

public class MovieDetails extends AppCompatActivity {
    private final static String BASE_POSTER_URL = "https://image.tmdb.org/t/p/";
    private final static String LOGO_SIZE = "w500";
    private static final String MOVIE = "MOVIE";
    private ImageView detailImage;
    private TextView title;
    private TextView yearOfRelease;
    private TextView ratings;
    private TextView moviesDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_details);

        Movies movies =getIntent().getParcelableExtra(MOVIE);

        title = (TextView) findViewById(R.id.title);
        detailImage = (ImageView) findViewById(R.id.logo_image_view);
        yearOfRelease = (TextView) findViewById(R.id.year);
        ratings = (TextView) findViewById(R.id.rating);
        moviesDescription = (TextView) findViewById(R.id.description);

        if (movies != null) {
            System.out.println(movies);
            title.setText(movies.getTitle());
            loadImage(movies.getPosterImageUrl());
            yearOfRelease.setText("Year: "+String.format("%.4s", movies.getReleaseDate()));
            ratings.setText("Ratings: "+String.format("%s/10", movies.getUserRatings()));
            moviesDescription.setText(movies.getOverview());
        }
    }

    private void loadImage(String path) {
        String urlBuilder = new StringBuilder()
                .append(BASE_POSTER_URL)
                .append(LOGO_SIZE)
                .append(path).toString();

        Picasso.with(this)
                .load(urlBuilder)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher)
                .into(detailImage);
    }
}
