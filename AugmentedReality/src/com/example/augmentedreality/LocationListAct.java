package com.example.augmentedreality;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.example.augmentedreality.extra.LocationListAdapter;
import com.example.net.ServiceHandler;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

public class LocationListAct extends SherlockActivity implements OnItemClickListener {

	ListView lv_loclist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_list);
		lv_loclist = (ListView) findViewById(R.id.lv_location_list);
		new LoadDataToList().execute();
		lv_loclist.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflator = getSupportMenuInflater();
		inflator.inflate(R.menu.location_list, menu);
		return true;
	}
	class LoadDataToList extends AsyncTask<Void, Integer, JSONArray> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			((ProgressBar)findViewById(R.id.pb_locationlist)).setVisibility(ProgressBar.VISIBLE);
		}
		
		@Override
		protected JSONArray doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ServiceHandler sh = new ServiceHandler();
			JSONArray arr = null;
//			Toast.makeText(getApplicationContext(), "backgroind", Toast.LENGTH_SHORT).show();
			String response = sh.makeServiceCall("http://192.168.0.3:8080/examples/servlets/servlet/LocationData", ServiceHandler.GET);
			Log.e("harsh","response --"+response);
			if(response!=null && response.equals("")!=true)
			try {
				arr = new JSONArray(response);
//				Toast.makeText(getApplicationContext(), "data", Toast.LENGTH_SHORT).show();
//				loclist = new String[arr.length()];
//				for(int i=0;i<arr.length();i++) {
//					JSONObject J_loc = arr.getJSONObject(i);
//					Log.d("harsh",J_loc.getString("name"));
//					loclist[i] = J_loc.getString("name");
//				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return arr;
		}
		@Override
		protected void onPostExecute(JSONArray result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			((ProgressBar)findViewById(R.id.pb_locationlist)).setVisibility(ProgressBar.GONE);
			if(result!=null && result.length()>0) {
				lv_loclist.setAdapter(new LocationListAdapter(LocationListAct.this,result));
			}
		}
		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		startActivity(new Intent(LocationListAct.this,LocationDetailAct.class));
	}

}
