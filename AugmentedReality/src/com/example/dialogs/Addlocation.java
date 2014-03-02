package com.example.dialogs;


import com.example.augmentedreality.R;
import com.google.android.gms.maps.model.Marker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class Addlocation extends DialogFragment {
	LayoutInflater inflater;
	View v;
	Marker m;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Bundle b = getArguments();
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		inflater = getActivity().getLayoutInflater();
		v = inflater.inflate(R.layout.add_new_locationview,null);
		((TextView) v.findViewById(R.id.txt_Add_lat)).setText("Lat :" + b.getString("lat"));
		((TextView) v.findViewById(R.id.txt_add_lng)).setText("Lng :" + b.getString("lng"));
    	builder.setView(v)
        	.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    // sign in the user ...
                }
            })
            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
				public void onClick(DialogInterface dialog, int id) {
                    
                }
            });
    	return builder.create();
		
	}
	

}
