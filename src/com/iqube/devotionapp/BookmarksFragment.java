package com.iqube.devotionapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.iqube.api.DataBaseHandler;
import com.iqube.devotionapp.R;

import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import com.iqube.devotionapp.model.ArrayListParcelable;
import com.iqube.devotionapp.model.HashMapParcelable;

import java.util.ArrayList;
import java.util.HashMap;

public class BookmarksFragment extends ListFragment{

    public final static String BOOKMARK_RELOAD = "RELOAD";
    private ArrayList<HashMap<String, String>> bookmarked;
    private ListView listview;
    private ActionMode mActionMode;

    public BookmarksFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_bookmark, container, false);
        setHasOptionsMenu(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
	    getActivity().getActionBar().setDisplayShowTitleEnabled(true);
        menu.findItem(R.id.action_settings).setVisible(false);

        menu.findItem(R.id.action_calendar).setVisible(false);
        menu.findItem(R.id.action_take_note).setVisible(false);
	}
	

        @Override
        public void onPrepareOptionsMenu(Menu menu) {

            super.onPrepareOptionsMenu(menu);

        }




    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listview = getListView();
        if(getArguments().getBoolean(BOOKMARK_RELOAD)){
            Log.d("view", "view recreated");
            getBookmarked();

            MainActivity.bookmark_adapter = new SimpleAdapter(
                    getActivity(),bookmarked,
                    R.layout.bookmark_list, new String[] {DataBaseHandler.KEY_HEADER, DataBaseHandler.KEY_DATE},
                    new int[] { R.id.title, R.id.date});
            MainActivity.reload_bookmarks =false;
            setListAdapter(MainActivity.bookmark_adapter);

        }else
        {
            Log.d("string_value", "am loving this");
            setListAdapter(MainActivity.bookmark_adapter);
        }

        MainActivity.reload_bookmarks = false;

        listListener();
    }

    public void getBookmarked(){
        DataBaseHandler db  = new DataBaseHandler(getActivity());

        bookmarked = db.getFilteredRecord(DataBaseHandler.Devotion_Table_NAME, 2, new String[]{DataBaseHandler.KEY_ID,
                                                DataBaseHandler.KEY_DATE , DataBaseHandler.KEY_BOOKMARK, DataBaseHandler.KEY_HEADER,
                                                    DataBaseHandler.KEY_MEDITATION_OF_THE_DAY,
                                                     },new String[] {"1"});
    }

    public void listListener(){
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("ListView ", "" + MainActivity.bookmark_adapter.getItem(position));
                        HashMap<String, String> item  =(HashMap<String, String>) MainActivity.bookmark_adapter.getItem(position);
                        HashMapParcelable parcel  = new HashMapParcelable(item);
                        Bundle arg = new Bundle();
                        arg.putParcelable(BookmarkedContent_Fragment.BOOKMARK_PARCEL, parcel);
                        Intent i = new Intent(getActivity(), BookmarkedContent.class);
                        i.putExtras(arg);
                        startActivity(i);

            }
        });
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
       // listview.setOnItemLongClickListener(new MainActivity.ActionClass());


        listview.setMultiChoiceModeListener(new  AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                  long id, boolean checked) {


            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // Respond to clicks on the actions in the CAB
                switch (item.getItemId()) {
                    case R.id.action_calendar:
                        //deleteSelectedItems();
                        //mode.finish(); // Action picked, so close the CAB
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Inflate the menu for the CAB
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.main, menu);
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // Here you can make any necessary updates to the activity when
                // the CAB is removed. By default, selected items are deselected/unchecked.
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // Here you can perform updates to the CAB due to
                // an invalidate() request
                return false;
            }
        });
    }



}
