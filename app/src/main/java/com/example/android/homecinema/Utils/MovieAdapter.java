package com.example.android.homecinema.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.android.homecinema.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Dominic Idagu on 13/04/2017.
 */

public class MovieAdapter extends BaseAdapter {

    private final static String BASE_POSTER_URL="https://image.tmdb.org/t/p/";
    private final static String IMAGE_SIZE="w185";
    private final Context context;
    private final List<Movies> moviesList;

    public MovieAdapter(Context context, List<Movies> movieList){
        this.context=context;
        this.moviesList=movieList;
    }

    @Override
    public int getCount() {
        return moviesList.size();
    }

    @Override
    public Object getItem(int position) {
        return moviesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Movies movie= (Movies) getItem(position);
        ImageView imageView;

        if(view == null ){
            LayoutInflater inflater= (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            imageView=(ImageView) inflater.inflate(R.layout.movie_item,viewGroup,false);
        }else{
            imageView=(ImageView) view;
        }

        String url= new StringBuilder()
                .append(BASE_POSTER_URL)
                .append(IMAGE_SIZE)
                .append(movie.getPosterImageUrl().trim())
                .toString();

        Picasso.with(context)
                .load(url)
                //.placeholder()
                //.error()
                .into(imageView);
        return imageView;
    }

    public void clear(){
        if(moviesList.size() > 0){
            moviesList.clear();
        }
    }
}
