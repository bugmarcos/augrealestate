package com.example.augmentedreality.extra;

import com.example.augmentedreality.AddlocationData;
import com.example.augmentedreality.MapTabFragment;
import com.example.dialogs.Addlocation;

import android.R;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabsPagerAdapter extends FragmentStatePagerAdapter {

	Context context;
	public TabsPagerAdapter(FragmentManager fm,Context c) {
        super(fm);
        context = c;
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            // Top Rated fragment activity
            return new AddlocationData();
        case 1:
            // Games fragment activity
            return new MapTabFragment();
        case 2:
            // Movies fragment activity
            return new AddlocationData();
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String[] arr = context.getResources().getStringArray(com.example.augmentedreality.R.array.LocationDetailTabs);
    	return arr[position];
    }

}
