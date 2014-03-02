package com.ssm.quadrah.diymarket.content;

import java.util.ArrayList;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ssm.quadrah.diymarket.R;
import com.ssm.quadrah.diymarket.content.endless.EndlessAdapter;

public class DetailItems extends EndlessAdapter {

	Context context;
	private ArrayList<Items> listItems;
	
	private RotateAnimation rotate=null;
	private View vi = null;
	
	TextView title;
	TextView artist;
	RatingBar rating;
	
	public DetailItems(Context context, ArrayList<Items> listItems){
		super(new ArrayAdapter<Items>(context,
                R.layout.row,
                android.R.id.text1,
                listItems));
		rotate=new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
		            0.5f, Animation.RELATIVE_TO_SELF,
		            0.5f);
		
		rotate.setDuration(600);
		rotate.setRepeatMode(Animation.RESTART);
		rotate.setRepeatCount(Animation.INFINITE);
		
		this.context = context;
		this.listItems = listItems;
	}
	
	public void setListItems(ArrayList<Items> listItems){
		this.listItems= listItems;
	}
	
	@Override
	  protected View getPendingView(ViewGroup parent) {
	    View row=LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_row, null);
	    
	    vi=row.findViewById(R.id.linearLayout1);
	    vi.setVisibility(View.GONE);
	    vi=row.findViewById(R.id.throbber);
	    vi.setVisibility(View.VISIBLE);
	    startProgressAnimation();
	    
	    return(row);
	  }
	
	@Override
	protected boolean cacheInBackground() throws Exception {
		// TODO Auto-generated method stub
		SystemClock.sleep(10000);       // pretend to do work
	    
	    return(getWrappedAdapter().getCount()<75);
	}
	
	@Override
	protected void appendCachedData() {
		// TODO Auto-generated method stub
		 if (getWrappedAdapter().getCount()<75) {
		      @SuppressWarnings("unchecked")
		      ArrayAdapter<Items> a=(ArrayAdapter<Items>)getWrappedAdapter();
		      
		      for (int i=0;i<25;i++) { 
		    	  Items item = new Items("333","444","Love");
		    	  a.add(item); }
		    }
	}
	
	public void startProgressAnimation() {
	    if (vi!=null) {
	    	vi.startAnimation(rotate);
	    }
	  }
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (keepOnAppending.get()) {
		      return(listItems.size()+1); // one more for
		                                    // "pending"
		}
		else{
			return(listItems.size());
		}
			
		
		//return listItems.size();
	}


	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;
		
		Log.d("getCount", "" +getCount());
		Log.d("position", "" +position);
		Log.d("keepOnAppending", "" + keepOnAppending.get());
		
		if (position == (getCount()-1) && keepOnAppending.get()) {
			if(vi == null){
				vi=getPendingView(parent);
			}
			
			if (runInBackground) {
		          executeAsyncTask(buildTask());
		        }
		        else {
		          try {
		            setKeepOnAppending(cacheInBackground());
		          }
		          catch (Exception e) {
		            setKeepOnAppending(onException(vi, e));
		          }
		     }
			return(vi);
		}
		else  
		{
			if(vi == null){
				LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				vi = inflater.inflate(R.layout.listview_row, null);
			}
			//ImageView thumb_image = (ImageView)vi.findViewById(R.id.list_image);
			Items p = listItems.get(position);
			
			if(p!= null){
				title = (TextView)vi.findViewById(R.id.title);
				artist = (TextView)vi.findViewById(R.id.desinger);
				rating = (RatingBar)vi.findViewById(R.id.rtbProductRating);
				
				
				rating.setFocusable(false);
				rating.setOnTouchListener(new OnTouchListener() 
				{
					@Override
					public boolean onTouch(View arg0, MotionEvent arg1) {
						// TODO Auto-generated method stub
						
						return true;
					}
					
				});			
				
				if(title != null){
					title.setText(p.getTitle());
				}
				if(artist != null){
					artist.setText(p.getDesigner());
				}
			}
		}
		return vi;
	}
}
