package com.ssm.quadrah.diymarket.register;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.ssm.quadrah.diymarket.R;

public class NewGridAdapter extends BaseAdapter{

	private Context context;
	private LinkedList<NewGridItems> newGridItems;

	private LayoutInflater mInflater;	
	public ViewHolder viewHolder = null;


	int idx;

	public NewGridAdapter(Context context, LinkedList<NewGridItems> newGridItems, int idx)
	{
		this.context = context;
		this.newGridItems = newGridItems;

		this.idx = idx;
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(newGridItems != null){
			return newGridItems.size()-2*idx; 

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

	public void setItemsList(LinkedList<NewGridItems> newGridItems){
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

				if(removeWork.id == removeItem.id && removeWork.title == removeItem.title && removeWork.title != "registerBasic")
				{
					it.remove();

					Log.d("removeWork", " " + removeWork.id);	
				}
			}						

			notifyDataSetChanged();
		}
	};



	public void setCatImage(int pos, ViewHolder viewHolder) {	
		
		
		
		Log.d("setCatImage", idx+ " : " + pos + " : " + newGridItems.size());
			
		
		viewHolder.imageView.setImageDrawable(newGridItems.get((idx*2)+pos).image.getDrawable());
		viewHolder.btnX.setVisibility(View.GONE);
		viewHolder.btnX.setTag(newGridItems.get(pos));	
		
		
		if(newGridItems.get((idx*2)+pos).title != "registerBasic"){
			viewHolder.btnX.setVisibility(View.VISIBLE);

		}
	}
}
