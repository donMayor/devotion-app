package com.iqube.devotionapp;

import android.graphics.Color;
import android.view.*;
import android.widget.*;
import com.iqube.api.DataBaseHandler;

import java.util.ArrayList;
import java.util.HashMap;

import com.iqube.devotionapp.adapter.NavDrawerListAdapter;
import com.iqube.devotionapp.model.NavDrawerItem;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;

public class MainActivity extends FragmentActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	public static ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;
	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
    public static ArrayList<HashMap<String, String>> All_DailyDevotions;
    private DataBaseHandler db;

    //Bookmark declaration
    public static ListAdapter bookmark_adapter;
    public static Boolean reload_bookmarks=true;

    //Devotion declaration
    public static ListAdapter devotion_adapter;
    public static ArrayList<HashMap<String, String>> ArrayListHashMapList;

    public static String INTENT_EXTRA = "EXTRA";

    //Action Mode
    public static Boolean isActionModeOn =  false;
    //End of Bookmark declaration
    //Calendar
    public static  boolean open = true;
    public static boolean drawerOpen;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Instantiate a viewPager and a PagerAdapter
		//viewPager =(ViewPager) findViewById(R.id.pager);
		db = new DataBaseHandler(this);
        All_DailyDevotions = db.getAllRecords(DataBaseHandler.Devotion_Table_NAME);

        //Bookmark_adapter



		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		// Find People
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		// Photos
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1), true, "0"));
		// Communities, Will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, "22"));
		// Pages
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
		// What's hot, We  will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
		

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);



		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {

            displayView(getIntent().getIntExtra(INTENT_EXTRA, 0));

		}
	}


	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
	    drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);


		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
            fragment = new MeditationFragment();
			break;
		case 1:
            fragment = new DevotionFragment();
			break;
		case 2:
			//fragment = new UpdateFragment();
			break;
		case 3:
			fragment = new BookmarksFragment();
            Bundle args = new Bundle();
            args.putBoolean(BookmarksFragment.BOOKMARK_RELOAD, MainActivity.reload_bookmarks);
            fragment.setArguments(args);
			break;
		case 4:
			fragment = new SubscribeFragment();
			break;
		case 5:
			fragment = new AboutFragment();
			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager =  getSupportFragmentManager();
			fragmentManager.beginTransaction()
			.replace(R.id.frame_container,  fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
        if(open){
            mDrawerLayout.openDrawer(mDrawerList);
            invalidateOptionsMenu();
            open = false;
        }




	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}


    static class ActionClass implements ActionMode.Callback, AdapterView.OnItemLongClickListener{

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.daily_devotional, menu);
            isActionModeOn = true;

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {



            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            isActionModeOn = false;

        }


        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//            for(int a = 0; a < parent.getChildCount(); a++)
//            {
//                parent.getChildAt(a).setBackgroundColor(Color.TRANSPARENT);
//            }

            view.setBackgroundColor(Color.BLUE);
            return true;
        }


    }
    //Calendar Call

    static class ActionListClass implements AbsListView.MultiChoiceModeListener, AdapterView.OnItemLongClickListener{

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.main, menu);
            isActionModeOn = true;

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            isActionModeOn = false;

        }


        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            for(int a = 0; a < parent.getChildCount(); a++)
            {
                parent.getChildAt(a).setBackgroundColor(Color.TRANSPARENT);
            }
            Log.d("anything here", "something here");
            view.setBackgroundColor(Color.GREEN);
            return true;
        }

        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

        }
    }
}
