package com.iqube.devotionapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.iqube.devotionapp.model.HashMapParcelable;

/**
 * Created by don_mayor on 5/21/14.
 */
public class BookmarkedContent_Fragment extends Fragment {

    public final static String BOOKMARK_PARCEL = "PARSE";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bookmarkedcontent_fragment,
                container, false);

        HashMapParcelable Parcel = (HashMapParcelable) getArguments().getParcelable(BOOKMARK_PARCEL);
        if(Parcel!=null){
            TextView date = (TextView) rootView
                    .findViewById(R.id.date);
            TextView header = (TextView) rootView
                    .findViewById(R.id.header);
            TextView title = (TextView) rootView
                    .findViewById(R.id.title);
            TextView content = (TextView) rootView
                    .findViewById(R.id.content);

            date.setText(Parcel.getDate());

            header.setText(Parcel.getHeader());

            title.setText(Parcel.getTitle());
            String v = Parcel.getContent().replace("{paragraph}", "\n");
            content.setText(v);


        }

        return rootView;
    }



}