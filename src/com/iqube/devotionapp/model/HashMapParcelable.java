package com.iqube.devotionapp.model;

/**
 * Created by don_mayor on 5/16/14.
 */
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.iqube.api.DataBaseHandler;
import com.iqube.devotionapp.DevotionFragment;

import java.util.HashMap;

public class HashMapParcelable implements Parcelable {

    public String date;
    public String title;
    public String header;
    public String content;

    public static final Parcelable.Creator<HashMapParcelable> CREATOR =
            new Parcelable.Creator<HashMapParcelable>(){

                @Override
                public HashMapParcelable createFromParcel(Parcel source) {
                    Log.d("gettingHere", "Confirmed");
                    return new HashMapParcelable(source);
                }

                @Override
                public HashMapParcelable[] newArray(int size) {
                    return new HashMapParcelable[size];
                }
            };

    public HashMapParcelable(HashMap<String, String> value, HashMap<String, String> date_header){
        setDate(date_header.get(DataBaseHandler.KEY_DATE));
        setHeader(date_header.get(DataBaseHandler.KEY_HEADER));
        setTitle(value.get(DevotionFragment.LIST_TITLE));
        setContent(value.get(DevotionFragment.LIST_CONTENT));

    }

    public HashMapParcelable(HashMap<String, String> value){
        setDate(value.get(DataBaseHandler.KEY_DATE));
        setHeader(value.get(DataBaseHandler.KEY_HEADER));
        setTitle("Meditation of the day");

        setContent(value.get(DataBaseHandler.KEY_MEDITATION_OF_THE_DAY));
        Log.d("dateValue", getDate());
        Log.d("title", getTitle());
    }

    public String getDate() {
          return date;
      	     }
   	public void setDate(String date) {
          this.date = date;
         }
    public String getTitle() {
          return title;
        }
    public void setTitle(String title) {
         this.title = title;
          }
    public String getHeader() {
            return header;
        }
    public void setHeader(String header) {
        this.header = header;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public HashMapParcelable(Parcel source){
        Log.d("called", "yescalled");
        readFromParcel(source);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(date);
        parcel.writeString(title);
        parcel.writeString(header);
        parcel.writeString(content);
    }

    public void readFromParcel(Parcel source){

        date = source.readString();
        title = source.readString();
        header = source.readString();
        content = source.readString();
    }

}