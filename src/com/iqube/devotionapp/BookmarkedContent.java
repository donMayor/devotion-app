package com.iqube.devotionapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.*;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.iqube.devotionapp.R;
import com.iqube.devotionapp.model.HashMapParcelable;

/**
 * Created by don_mayor on 5/21/14.
 */
public class BookmarkedContent extends FragmentActivity {


    private FragmentManager fm       = getSupportFragmentManager();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.bookmarkedcontent);

        FragmentTransaction ft = fm.beginTransaction();
        BookmarkedContent_Fragment bookmarkfragment = new BookmarkedContent_Fragment();
        Bundle arg = new Bundle();
        HashMapParcelable parcel = getIntent().getParcelableExtra(BookmarkedContent_Fragment.BOOKMARK_PARCEL);
        arg.putParcelable(BookmarkedContent_Fragment.BOOKMARK_PARCEL, parcel);
        bookmarkfragment.setArguments(arg);
        ft.add(R.id.frame_container, bookmarkfragment );
        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch( item.getItemId()){
            case android.R.id.home:
                Intent i = new Intent(this, MainActivity.class);

                i.putExtra(MainActivity.INTENT_EXTRA, 3);
                NavUtils.navigateUpTo(this, i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}