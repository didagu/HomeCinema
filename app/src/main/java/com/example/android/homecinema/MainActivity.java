package com.example.android.homecinema;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.homecinema.Utils.MovieAdapter;
import com.example.android.homecinema.Utils.MovieFetcherTask;
import com.example.android.homecinema.Utils.Movies;
import com.example.android.homecinema.Utils.MyCallBack;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Dominic Idagu on 13/04/2017.
 */

public class MainActivity extends AppCompatActivity {

    private static final String MOVIE="MOVIE";
    private static final String MOVIE_LIST="MOVIE_LIST";
    private MovieAdapter  mMovieAdapter;
    private MovieFetcherTask moviesTask;
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
        
        loadMovies("popular");
    }

    public void loadMovies(String s) {
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
        moviesTask.execute(s);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(MOVIE_LIST, moviesArrayList);
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onStart() {
        super.onStart();
        loadMovies("");
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
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.settings);
            dialog.setTitle("Choose a sorting order:");
            dialog.setCancelable(true);
            TextView dialogueTitle=(TextView) findViewById(R.id.dialog_title);
            final RadioGroup radioGroup=(RadioGroup) dialog.findViewById(R.id.radio_group);
            RadioButton rd1 = (RadioButton) dialog.findViewById(R.id.rd_1);
            RadioButton rd2 = (RadioButton) dialog.findViewById(R.id.rd_2);
            Button btn=(Button) dialog.findViewById(R.id.ok_button);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (radioGroup.getCheckedRadioButtonId()){
                        case R.id.rd_1:
                            loadMovies("top_rated");
                            Toast.makeText(MainActivity.this,"Sorting based on Top Rated",Toast.LENGTH_LONG);
                        case R.id.rd_2:
                            loadMovies("popular");
                            Toast.makeText(MainActivity.this,"Sorting based on Popular",Toast.LENGTH_LONG);
                        default:
                    }
                    dialog.cancel();
                }
            });

            // now that the dialog is set up, it's time to show it
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }


}
