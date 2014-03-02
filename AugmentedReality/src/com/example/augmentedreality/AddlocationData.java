package com.example.augmentedreality;

import com.actionbarsherlock.app.SherlockFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class AddlocationData extends SherlockFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v =inflater.inflate(R.layout.activity_addlocation_data, null);
		LinearLayout ll = (LinearLayout) v.findViewById(R.id.add_location_par);
		return ll;
		
	}
}
