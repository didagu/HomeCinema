package com.example.android.homecinema.Utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dominic Idagu on 13/04/2017.
 */

public class Movies implements Parcelable {

    private final String title;
    private final String posterImageUrl;
    private final String overview;
    private final String userRatings;
    private final String releaseDate;

    public Movies(String title,String posterImageUrl,String overview,String userRatings,String releaseDate){
        this.title=title;
        this.posterImageUrl=posterImageUrl;
        this.overview=overview;
        this.userRatings=userRatings;
        this.releaseDate=releaseDate;
    }

    private Movies(Parcel parcel){
        title=parcel.readString();
        posterImageUrl=parcel.readString();
        overview=parcel.readString();
        userRatings=parcel.readString();
        releaseDate=parcel.readString();

    }

    public String getTitle(){
        return title;
    }
    public String getPosterImageUrl(){
        return posterImageUrl;
    }
    public String getOverview(){
        return overview;
    }
    public String getUserRatings(){
        return userRatings;
    }
    public String getReleaseDate(){
        return releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(posterImageUrl);
        parcel.writeString(overview);
        parcel.writeString(userRatings);
        parcel.writeString(releaseDate);
    }

    public static final Parcelable.Creator<Movies> CREATOR = new Parcelable.Creator<Movies>(){

        @Override
        public Movies createFromParcel(Parcel parcel) {
            return new Movies(parcel);
        }

        @Override
        public Movies[] newArray(int i) {
            return new Movies[i];
        }
    };
}
