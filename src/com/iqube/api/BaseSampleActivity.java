package com.iqube.api;

import java.util.Random;




import com.iqube.devotionapp.R;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public abstract class BaseSampleActivity extends FragmentActivity {
    private static final Random RANDOM = new Random();
    public static boolean last_scrolled = false;
    public static String[] devotion_search_string = {"<date>", "</date>", "<header>", "</header>", "<morning_prayer>", "</morning_prayer>",
            "<intercession>", "</intercession>", "<concluding_prayer>", "</concluding_prayer>", "<entrance_antiphon>", "</entrance_antiphon>",
            "<opening_prayer>", "</opening_prayer>", "<suggested_prayer_for_the_faithful>", "</suggested_prayer_for_the_faithful>",
            "<prayer_over_offering>", "</prayer_over_offering>", "<communion_antiphon>", "</communion_antiphon>", "<prayer_after_communion>",
                "</prayer_after_communion>", "<meditation_of_the_day>", "</meditation_of_the_day>", "<midday_prayer>", "</midday_prayer>",
            "<evening_prayer_ii>", "</evening_prayer_ii>", "<intercession_ii>", "</intercession_ii>"};



    //protected static String[] bible_search_string = {"<date>", "</date>", "<verse>", "</verse>", "<content>", "</content>"};
    public static String[] devotion_Key = {DataBaseHandler.KEY_DATE, DataBaseHandler.KEY_HEADER, DataBaseHandler.KEY_MORNING_PRAYER,
                                                  DataBaseHandler.KEY_INTERCESSION, DataBaseHandler.KEY_CONCLUDING_PRAYER, DataBaseHandler.KEY_ENTRANCE_ANTIPHON
                                                  , DataBaseHandler.KEY_OPENING_PRAYER, DataBaseHandler.KEY_SUGGESTED_PRAYER_FOR_THE_FAITHFUL,
                                                    DataBaseHandler.KEY_PRAYER_OVER_OFFERING, DataBaseHandler.KEY_COMMUNION_ANTIPHON,
                                                DataBaseHandler.KEY_PRAYER_AFTER_COMMUNION, DataBaseHandler.KEY_MEDITATION_OF_THE_DAY, DataBaseHandler.KEY_MIDDAY_PRAYER,
                                                DataBaseHandler.KEY_EVENING_PRAYER_II, DataBaseHandler.KEY_INTERCESSION_II};
//    protected static String[] bible_key = {DataBaseHandler.KEY_DATE, DataBaseHandler.KEY_VERSE,
//            DataBaseHandler.KEY_CONTENT};
    protected static String devotion_filename = "new_devotion.txt";
    protected static String bible_filename = "bible.txt";


    TestFragmentAdapter mAdapter;
    static ViewPager mPager;
    PageIndicator mIndicator;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
 
        return super.onOptionsItemSelected(item);
    }
}