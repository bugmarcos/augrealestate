package com.example.augmentedreality;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.example.net.ServiceHandler;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends SherlockFragmentActivity implements OnMapClickListener,OnMarkerClickListener,android.view.View.OnClickListener {

SensorManager sensorManager;
	
	int orientationSensor;
	int accelerometerSensor;
	private GoogleMap googleMap;
	Marker addlocationmarker;
	android.view.View.OnClickListener newlocationfragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try {
            // Loading map
            initilizeMap();
            googleMap.setOnMarkerClickListener(this);
            googleMap.setMyLocationEnabled(true);
 
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		/*try {
			  ViewConfiguration config = ViewConfiguration.get(this);
			  Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");

			  if (menuKeyField != null) {
			    menuKeyField.setAccessible(true);
			    menuKeyField.setBoolean(config, false);
			  }
			}
			catch (Exception e) {
			  // presumably, not relevant
			}*/
		MenuInflater menuinflate = getSupportMenuInflater();
		menuinflate.inflate(R.menu.main, menu);
		
		return true;
	}
	private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(
                    R.id.map)).getMap();
 
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
 
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case R.id.add_new_loc:
            // search action
        	googleMap.setOnMapClickListener(this);
        	break;
        case R.id.action_settings:
        	Fragment f = getSupportFragmentManager().findFragmentByTag("addlocationfrag");
            getSupportFragmentManager().beginTransaction()
            	.remove(f).commit();
            FrameLayout fl = (FrameLayout) findViewById(R.id.map_cont);
 			fl.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
 			googleMap.setOnMapClickListener(null);
 			break;
        case R.id.action_search:
        	googleMap.setBuildingsEnabled(true);
        	CameraPosition cameraPosition = CameraPosition
					.builder()
					.target(new LatLng(googleMap.getMyLocation().getLatitude(), googleMap.getMyLocation().getLongitude()))
					.tilt(90)
					.zoom(googleMap.getMaxZoomLevel())
					.build();

			googleMap.moveCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));
			gettomyloc();
			break;
        case R.id.zoomlevel:
        	Toast.makeText(getApplicationContext(),String.valueOf(googleMap.getCameraPosition().zoom), Toast.LENGTH_SHORT).show();
        	sensorManager.unregisterListener(sensorEventListener);
        	break;
        default:
            return super.onOptionsItemSelected(item);
        }
		return false;
    }
    @Override
    	protected void onPause() {
    		// TODO Auto-generated method stub
    		super.onPause();
    		sensorManager.unregisterListener(sensorEventListener);
    	}
    public void gettomyloc()
    {
    	
    	sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        orientationSensor = Sensor.TYPE_ORIENTATION;
        accelerometerSensor = Sensor.TYPE_ACCELEROMETER;
        sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(orientationSensor), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(accelerometerSensor), SensorManager.SENSOR_DELAY_NORMAL);
    	
    }

    final SensorEventListener sensorEventListener = new SensorEventListener() {
    	public void onSensorChanged(SensorEvent sensorEvent) {
    		if (sensorEvent.sensor.getType() == Sensor.TYPE_ORIENTATION)
    		{
    			float headingAngle;
    			float pitchAngle;
    			float rollAngle;
    			headingAngle = sensorEvent.values[0];
    			pitchAngle = sensorEvent.values[1];
    			rollAngle = sensorEvent.values[2];
    			
    			CameraPosition cameraPosition = CameraPosition
    					.builder()
    					.target(new LatLng(googleMap.getMyLocation().getLatitude(), googleMap.getMyLocation().getLongitude()))
    					.bearing((int)headingAngle)
    					.zoom(googleMap.getCameraPosition().zoom)
    					.tilt(90)
    					.build();
    			
    			
    			googleMap.moveCamera(CameraUpdateFactory
    					.newCameraPosition(cameraPosition));
    				
    		}
    	}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}
	};
    
	@Override
	public void onMapClick(LatLng latlng) {
		// TODO Auto-generated method stub
		if(addlocationmarker != null) {
			addlocationmarker.remove();
		}
		addlocationmarker = googleMap.addMarker(new MarkerOptions().position(latlng).snippet("click to add"));
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		/*DialogFragment showform = new Addlocation();
		showform.show(getSupportFragmentManager(), "show");
		Bundle args = new Bundle();
		args.putString("lat", String.valueOf(arg0.getPosition().latitude));
		args.putString("lng", String.valueOf(arg0.getPosition().longitude));
		Geocoder gc = new Geocoder(getApplicationContext());
		List<Address> ad = null;
		try {
			ad = gc.getFromLocation(arg0.getPosition().latitude, arg0.getPosition().longitude, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(ad == null || ad.size()!=0) {
			Toast.makeText(getApplicationContext(), ad.get(0).getLocality().toString(), Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(getApplicationContext(), "location not found", Toast.LENGTH_SHORT).show();
		}
		showform.onCancel(new DialogInterface() {
			
			@Override
			public void dismiss() {
				// TODO Auto-generated method stub
				googleMap.setOnMapClickListener(null);
			}
			
			@Override
			public void cancel() {
				// TODO Auto-generated method stub
				googleMap.setOnMapClickListener(null);
			}
		});
		showform.setArguments(args);*/
		
		AddlocationData ff = (AddlocationData) getSupportFragmentManager().findFragmentByTag("addlocationfrag");
		if(ff != null) {
			
			View v = ff.getView();
			TextView tlat = (TextView) v.findViewById(R.id.txt_Add_lat);
			TextView tlng = (TextView) v.findViewById(R.id.txt_add_lng);
			TextView tloc = (TextView) v.findViewById(R.id.txt_add_loc);
			List<Address> ad = null;
			try {
				ad = new Geocoder(getApplicationContext()).getFromLocation(arg0.getPosition().latitude, arg0.getPosition().longitude, 1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(ad!=null) {
				tloc.setText(ad.get(0).getAddressLine(0)+","+ad.get(0).getAddressLine(1)+","+ad.get(0).getAddressLine(2));
				
			}
			else
				tloc.setText("NOT FOUND");
			tlat.setText(String.valueOf(arg0.getPosition().latitude));
			tlng.setText(String.valueOf(arg0.getPosition().longitude));
	        
			Button btnsubmit = (Button) v.findViewById(R.id.submitnewloc);
			Button btncancel = (Button) v.findViewById(R.id.cancelnewloc);
			btnsubmit.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					new AddlocationtoDB().execute();
				}
			});
		}
		else
		{
			AddlocationData showfrag = new AddlocationData();
			getSupportFragmentManager().beginTransaction()
				.add(R.id.container_main, showfrag,"addlocationfrag").commit();
			FrameLayout fl = (FrameLayout) findViewById(R.id.map_cont);
			fl.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, (getResources().getDisplayMetrics().heightPixels/2)));
			Toast.makeText(getApplicationContext(), "created", Toast.LENGTH_SHORT).show();
		}
		
		return false;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.submitnewloc:
			new AddlocationtoDB().execute();
			break;
		case R.id.cancelnewloc:
			Fragment f = getSupportFragmentManager().findFragmentByTag("addlocationfrag");
	           getSupportFragmentManager().beginTransaction()
	           	.remove(f).commit();
	           FrameLayout fl = (FrameLayout) findViewById(R.id.map_cont);
				fl.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				googleMap.setOnMapClickListener(null);
	         break;
		default:
			break;
		}
	}
	
	private class AddlocationtoDB extends AsyncTask<Void, Void, Void> {
		 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            
        }
 
        @Override
        protected Void doInBackground(Void... v) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
 
            // Making a request to url and getting response

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			AddlocationData ff = (AddlocationData) getSupportFragmentManager().findFragmentByTag("addlocationfrag");
			ViewGroup ve = (ViewGroup) ff.getView();
			params.add(new BasicNameValuePair("lat", String.valueOf(((TextView)ve.findViewById(R.id.txt_Add_lat)).getText())));
	        params.add(new BasicNameValuePair("lng", String.valueOf(((TextView)ve.findViewById(R.id.txt_add_lng)).getText())));
	        params.add(new BasicNameValuePair("full_name", String.valueOf(((TextView)ve.findViewById(R.id.txt_add_loc)).getText())));
	        params.add(new BasicNameValuePair("type", String.valueOf(((Spinner)ve.findViewById(R.id.spn_add_type)).getSelectedItem())));
	        params.add(new BasicNameValuePair("extrainfo", String.valueOf(((TextView)ve.findViewById(R.id.mulextrainfo_add)).getText())));
            String jsonStr = sh.makeServiceCall("http://192.168.0.3:8080/examples/servlets/servlet/Addlocationdata", ServiceHandler.GET,params);
 
            Log.d("Response: ", "> " + jsonStr);
 
            
 
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
           
           Fragment f = getSupportFragmentManager().findFragmentByTag("addlocationfrag");
           getSupportFragmentManager().beginTransaction()
           	.remove(f).commit();
           FrameLayout fl = (FrameLayout) findViewById(R.id.map_cont);
			fl.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			googleMap.setOnMapClickListener(null);
        }
 
    }
}
