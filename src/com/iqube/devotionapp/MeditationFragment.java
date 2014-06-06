package com.iqube.devotionapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import com.iqube.api.DataBaseHandler;
import com.iqube.devotionapp.model.HashMapParcelable;

public class MeditationFragment extends Fragment {
	
	ViewPager mViewPager;
	public static final String TAG =  DailyDevotionalFragment.class.getSimpleName();
	DevotionPagerAdapter devotionAdapter;

    private DatePickerDialog.OnDateSetListener date;
    private DataBaseHandler db;
    public MainActivity mainactivity;


    //Calendar
    public static int Med_num=0;
    private Calendar myCalendar = Calendar.getInstance();
    private DateFormat fmtDateAndTime = new SimpleDateFormat("EEEE MMM dd, yyyy");

	public MeditationFragment(){

    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_meditation, container, false);

        devotionAdapter = new DevotionPagerAdapter(getChildFragmentManager());
        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
        mViewPager.setAdapter(devotionAdapter);
        setHasOptionsMenu(true);
        
        return rootView;
    }
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// setup action bar for tabs
        db = new DataBaseHandler(getActivity());
        mainactivity = new MainActivity();
		super.onCreate(savedInstanceState);

	}

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDateListener();
        mViewPager.setCurrentItem(getID(fmtDateAndTime.format(myCalendar.getTime())));
        getActivity().setTitle(MainActivity.All_DailyDevotions.get(Med_num).get(DataBaseHandler.KEY_DATE));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_settings).setVisible(!MainActivity.drawerOpen);

        menu.findItem(R.id.action_calendar).setVisible(!MainActivity.drawerOpen);
        menu.findItem(R.id.action_take_note).setVisible(!MainActivity.drawerOpen);
        menu.findItem(R.id.action_bookmark).setVisible(!MainActivity.drawerOpen);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (MainActivity.mDrawerToggle.onOptionsItemSelected(item)) {

            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_calendar:
            {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                return true;
            }
            case R.id.action_bookmark:
                int result= UpdateBookmark(mViewPager.getCurrentItem()+1);
                Log.d("value", "result"+result);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void setDateListener(){

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {



                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                mViewPager.setCurrentItem(getID(fmtDateAndTime.format(myCalendar.getTime())));
                getActivity().setTitle(MainActivity.All_DailyDevotions.get(Med_num).get(DataBaseHandler.KEY_DATE));

            }

        };


    }

    public String trimDate(String date){


        return date;
    }

    public int getID(String date){
        Cursor cursor = db.getSelectedRecord(DataBaseHandler.Devotion_Table_NAME, 1, new String[]{DataBaseHandler.KEY_ID, DataBaseHandler.KEY_DATE}, new String[]{date});
        if(cursor.getCount()!=0){
            Log.d("cursor", "" + cursor.getColumnName(0));
            cursor.moveToFirst();
            Med_num =new  Integer(cursor.getString(0)) -1;

            return Med_num;
        }
        else{
            return 0;
        }
    }

    public int UpdateBookmark(int KEY_ID){
        int result = db.updateRecord(DataBaseHandler.Devotion_Table_NAME, 1, KEY_ID );


        if(result>0){
            MainActivity.reload_bookmarks = true;
        }
        return result;

    }



    public class DevotionPagerAdapter extends FragmentPagerAdapter {

		public DevotionPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.

            Fragment fragment = new DevotionSectionFragment();
            HashMapParcelable parcel = new HashMapParcelable(MainActivity.All_DailyDevotions.get(position));
            Bundle args = new Bundle();
            args.putParcelable(DevotionSectionFragment.ARG_DAY, parcel);

            fragment.setArguments(args);
            return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return MainActivity.All_DailyDevotions.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {

			return MainActivity.All_DailyDevotions.get(position).get(DataBaseHandler.KEY_DATE);
		}
	}

	public static class DevotionSectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_DAY= "DAY_MEDITATION";

		public DevotionSectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_meditation_tabbed,
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
	}
	
}
