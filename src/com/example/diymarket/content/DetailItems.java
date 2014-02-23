package com.example.diymarket.content;

import java.util.ArrayList;

import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.diymarket.R;
import com.example.diymarket.content.endless.EndlessAdapter;

public class DetailItems extends EndlessAdapter {

	Context context;
	private ArrayList<Items> listItems;
	
	 private RotateAnimation rotate=null;
	 private View pendingView=null;
	
	public DetailItems(Context context, int textViewResourceId, ArrayList<Items> listItems){
		super(new ArrayAdapter<Items>(context,
                R.layout.row,
                textViewResourceId,
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
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();
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
		
		if(vi == null){
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			vi = inflater.inflate(R.layout.listview_row, null);
			
		
		}
				
		//ImageView thumb_image = (ImageView)vi.findViewById(R.id.list_image);
		Items p = listItems.get(position);
		if(p!= null){
			TextView title = (TextView)vi.findViewById(R.id.title);
			TextView artist = (TextView)vi.findViewById(R.id.desinger);
			RatingBar rating = (RatingBar)vi.findViewById(R.id.rtbProductRating);
			rating.setFocusable(false);
			rating.setOnTouchListener(new OnTouchListener() {
			
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
		return vi;
	}

	@Override
	  protected View getPendingView(ViewGroup parent) {
	    View row=LayoutInflater.from(parent.getContext()).inflate(R.layout.row, null);
	    
	    pendingView=row.findViewById(android.R.id.text1);
	    pendingView.setVisibility(View.GONE);
	    pendingView=row.findViewById(R.id.throbber);
	    pendingView.setVisibility(View.VISIBLE);
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
	    if (pendingView!=null) {
	      pendingView.startAnimation(rotate);
	    }
	  }
}
