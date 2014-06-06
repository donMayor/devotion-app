package com.iqube.devotionapp;

import com.iqube.devotionapp.R;

import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public class UpdateFragment extends Fragment {
	
	public UpdateFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_update, container, false);
        setHasOptionsMenu(true);
        return rootView;
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
		ActionBar actionBar= getActivity().getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	    actionBar.setDisplayShowTitleEnabled(true);
		actionBar.removeAllTabs();
	}
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onPrepareOptionsMenu(menu);
		menu.findItem(R.id.action_take_note).setVisible(false);
		menu.findItem(R.id.action_bookmark).setVisible(false);
		menu.findItem(R.id.action_share).setVisible(false);
	}
	
	
	
}
