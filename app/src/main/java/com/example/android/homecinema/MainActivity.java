package com.example.android.homecinema;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.homecinema.utils.MovieAdapter;
import com.example.android.homecinema.utils.MovieFetcherTask;
import com.example.android.homecinema.utils.Movies;
import com.example.android.homecinema.utils.MyCallBack;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Dominic Idagu on 13/04/2017.
 */

public class MainActivity extends AppCompatActivity {

    private static final String MOVIE="MOVIE";
    private static final String MOVIE_LIST="MOVIE_LIST";
    private MovieAdapter  mMovieAdapter;
    private ArrayList<Movies> moviesArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //check if savedInstateState is null
        if(savedInstanceState == null || !savedInstanceState.containsKey(MOVIE_LIST)){
            moviesArrayList=new ArrayList<>();
        }else{
            moviesArrayList=savedInstanceState.getParcelableArrayList(MOVIE_LIST);
        }
        setContentView(R.layout.activity_main);

        mMovieAdapter= new MovieAdapter(this,moviesArrayList);
        GridView gridView=(GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(mMovieAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movies movies= (Movies) mMovieAdapter.getItem(i);
                Intent intent=new Intent(MainActivity.this,MovieDetails.class);
                intent.putExtra(MOVIE,movies);
                startActivity(intent);
            }
        });
        
        loadMovies();
    }

    public void loadMovies() {
        MovieFetcherTask moviesTask = new MovieFetcherTask(new MyCallBack() {
            @Override
            public void updateAdapter(Movies[] movies) {
                if (movies != null) {
                    mMovieAdapter.clear();
                    Collections.addAll(moviesArrayList, movies);
                    mMovieAdapter.notifyDataSetChanged();
                }
            }
        });
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        String sortingOrder = preferences.getString(getString(R.string.pref_sort_key),
                getString(R.string.pref_sort_popular_value));
        moviesTask.execute(sortingOrder);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(MOVIE_LIST, moviesArrayList);
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onStart() {
        super.onStart();
        loadMovies();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
