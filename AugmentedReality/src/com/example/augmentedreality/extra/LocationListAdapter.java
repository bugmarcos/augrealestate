package com.example.augmentedreality.extra;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.augmentedreality.R;
import com.example.augmentedreality.R.id;
import com.example.augmentedreality.R.layout;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class LocationListAdapter extends BaseAdapter implements ListAdapter {

	Context cont;
	JSONArray c;
	public LocationListAdapter(Context context, JSONArray c) {
		// TODO Auto-generated constructor stub
		cont = context;
		this.c=c;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return c.length();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	private class ViewHolder {
        ImageView imageView;
        TextView txtname;
        TextView txttime;
//        TextView txtmsg;
//        TextView time;
    }
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		 
        LayoutInflater mInflater = (LayoutInflater)
            cont.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
//        	Toast.makeText(cont, "done", Toast.LENGTH_SHORT).show();
            convertView = mInflater.inflate(R.layout.locationlistview_item, null);
            holder = new ViewHolder();
            holder.txtname = (TextView) convertView.findViewById(R.id.item_locationlist_name);
            holder.txttime = (TextView) convertView.findViewById(R.id.item_locationlist_time);
            holder.imageView = (ImageView) convertView.findViewById(R.id.item_locationlist_pic);
//            holder.txtmsg = (TextView) convertView.findViewById(R.id.msg);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        try {
			JSONObject jo = c.getJSONObject(position);
			holder.txtname.setText(jo.getString("name"));
			String[] timetemp = jo.getString("time").split(" ");
			holder.txttime.setText(timetemp[0]);
//	        holder.txtmsg.setText(jo.getString("stat"));
//	        ImageLoader imgLoader = new ImageLoader(cont);
//			int loader = R.drawable.loader;
//			
//			imgLoader.DisplayImage(cont.getResources().getString(R.string.server_url_profile_pics)+jo.getString("name")+".jpg", loader, holder.imageView);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
 
        return convertView;
	}

}
