package com.iqube.devotionapp;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.AssetManager;


import android.database.Cursor;
import android.support.v4.app.*;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.*;
import android.widget.DatePicker;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.iqube.api.BaseSampleActivity;
import com.iqube.api.DataBaseHandler;
import com.iqube.devotionapp.model.HashMapParcelable;

public class DailyDevotionalFragment extends FragmentActivity {
	
	public ViewPager mViewPager;
    public static final String TAG =  DailyDevotionalFragment.class.getSimpleName();
	private DevotionPagerAdapter devotionAdapter;
    public final static String ListPosition = "POSITION";


    public DailyDevotionalFragment(){
		
	}

    @Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// setup action bar for tabs
        setContentView(R.layout.fragment_dailydevotion);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        devotionAdapter = new DevotionPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(devotionAdapter);
        setTitle(MainActivity.All_DailyDevotions.get(DevotionFragment.Date_num).get(DataBaseHandler.KEY_DATE));
		super.onCreate(savedInstanceState);
		
	}

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mViewPager.setCurrentItem(getIntent().getIntExtra(DailyDevotionalFragment.ListPosition, 0));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        //menu.findItem(R.id.action_settings).setVisible(!drawerOpen);


        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                 Intent i = new Intent(this, MainActivity.class);

                i.putExtra(MainActivity.INTENT_EXTRA, 1);
                NavUtils.navigateUpTo(this, i);
                 return true;
            case R.id.action_settings:
                return true;
            case R.id.action_calendar:{


                return true;
            }
            case R.id.action_bookmark:{



            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public class DevotionPagerAdapter extends FragmentPagerAdapter {

		public DevotionPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {

			Fragment fragment = new DevotionSectionFragment();
            HashMapParcelable parcel = new HashMapParcelable(MainActivity.ArrayListHashMapList.get(position), MainActivity.All_DailyDevotions.get(DevotionFragment.Date_num));
			Bundle args = new Bundle();
			args.putParcelable(DevotionSectionFragment.ARG_DAY, parcel);

			fragment.setArguments(args);
			return fragment;
		}

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
		public int getCount() {
			// Show 3 total pages.
			return MainActivity.ArrayListHashMapList.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();

			return MainActivity.ArrayListHashMapList.get(position).get(DevotionFragment.LIST_TITLE);
		}
	}

	public static class DevotionSectionFragment extends Fragment{
        public static final String ARG_DAY = "DAY_CONTENT";
        /**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */


        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);


        }

        public DevotionSectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_tabbed_dummy,
					container, false);

            HashMapParcelable Parcel = (HashMapParcelable) getArguments().getParcelable(ARG_DAY);
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



        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {

            super.onViewCreated(view, savedInstanceState);



        }

    }


}
