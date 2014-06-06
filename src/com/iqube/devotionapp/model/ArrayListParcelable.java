package com.iqube.devotionapp.model;

/**
 * Created by don_mayor on 5/16/14.
 */
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.iqube.api.DataBaseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArrayListParcelable implements Parcelable {

    private ArrayList<HashMap<String, String>> arrayList;
    private List<ArrayList> value = new ArrayList<ArrayList>();


    public static final Parcelable.Creator<HashMapParcelable> CREATOR =
            new Parcelable.Creator<HashMapParcelable>(){

                @Override
                public HashMapParcelable createFromParcel(Parcel source) {

                    return new HashMapParcelable(source);
                }

                @Override
                public HashMapParcelable[] newArray(int size) {
                    return new HashMapParcelable[size];
                }
            };

    public ArrayListParcelable(ArrayList<HashMap<String, String>> value){
        setArrayList(value);

    }

    public ArrayList<HashMap<String, String>> getArrayList() {

        return arrayList;
    }
    public void setArrayList(ArrayList<HashMap<String, String>> arrayList) {
        value.add(arrayList);
        this.arrayList = arrayList;
    }


    public ArrayListParcelable(Parcel source){
        readFromParcel(source);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {

        parcel.writeList(value);

    }

    public void readFromParcel(Parcel source){
       source.readList(value, ClassLoader.getSystemClassLoader());

    }

}