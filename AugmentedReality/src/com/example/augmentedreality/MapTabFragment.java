package com.example.augmentedreality;

import com.actionbarsherlock.app.SherlockFragment;

import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MapTabFragment extends SherlockFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v =inflater.inflate(R.layout.activity_map_tab_fragment,container, false);
		return v;
	}

}
