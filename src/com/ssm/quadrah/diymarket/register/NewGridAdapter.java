package com.ssm.quadrah.diymarket.register;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ssm.quadrah.diymarket.R;

public class NewGridAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<NewGridItems> newGridItems;
	private ArrayList<ImageView> images;
	
	
	
	private LayoutInflater mInflater;
	
	public ViewHolder viewHolder = null;
	
	
	

	public NewGridAdapter(Context context, ArrayList<NewGridItems> newGridItems, ArrayList<ImageView> images)
	{
		this.context = context;
		
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		this.newGridItems = newGridItems;
		this.images = images;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(newGridItems != null){							  
			return newGridItems.size();
		}
		return 0;
	}

	@Override
	 public void notifyDataSetChanged() {
	  super.notifyDataSetChanged();
	 }
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(newGridItems != null && position >= 0 && position < getCount()){
			return newGridItems.indexOf(position);
		}
		return null;
	}
	
	public void setItemsList(ArrayList<NewGridItems> newGridItems){
		this.newGridItems = newGridItems;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		if(newGridItems != null && position >= 0 && position < getCount()){
			return newGridItems.get(position).id;
		}
		return 0;
	}

	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		
		if(view == null){
			
			view = mInflater.inflate(R.layout.grid_new_custom, parent, false);
			viewHolder = new ViewHolder();	
			viewHolder.imageView = (ImageView) view.findViewById(R.id.grid_new_item_image);
			viewHolder.btnX = (Button)view.findViewById(R.id.btnX);			

			viewHolder.btnX.setOnClickListener(OnClickRemove);
			view.setTag(viewHolder);
			
			
		}else{
			viewHolder = (ViewHolder)view.getTag();			
		}
		
			
		setCatImage(position, viewHolder);
		
		return view;
	}
	
	View.OnClickListener OnClickRemove = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			NewGridItems removeItem = (NewGridItems)v.getTag();			
			Log.d("removeItem", "remove : " + removeItem.id );
			for(Iterator<NewGridItems> it = newGridItems.iterator(); it.hasNext();)
			{
				NewGridItems removeWork = it.next();
				
				
				if(removeWork.id == removeItem.id)
				{
					it.remove();
					
					Log.d("removeWork", " " + removeWork.id);	
				}
			}						
			
			notifyDataSetChanged();
		}
	};
	
	

	public void setCatImage(int pos, ViewHolder viewHolder) {		
		
		viewHolder.imageView.setImageDrawable(images.get(pos).getDrawable());
		viewHolder.btnX.setTag(newGridItems.get(pos));
		
		
	}
}
