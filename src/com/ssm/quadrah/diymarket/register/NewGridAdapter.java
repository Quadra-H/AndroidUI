package com.ssm.quadrah.diymarket.register;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
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
	
	private ArrayList<Integer> btnNum;
	private List<Button> buttons;
	private LayoutInflater mInflater;
	
	ViewHolder viewHolder = null;
	
	
	private int numButton;
	
	private LinearLayout dynamicLayout;
	public NewGridAdapter(Context context, ArrayList<NewGridItems> newGridItems, ArrayList<ImageView> images)
	{
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		this.context = context;
		this.newGridItems = newGridItems;
		this.images = images;
		
		numButton = 0;
		
		buttons = new ArrayList<Button>();
		btnNum = new ArrayList<Integer>();
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
			
			dynamicLayout = (LinearLayout) view.findViewById(R.id.dynamicArea);
			dynamicLayout.setFocusable(false);
			
			Button dynamicButton = new Button(context);
			dynamicButton.setFocusable(false);
			
			
			
			dynamicButton.setId(numButton);
			btnNum.add(numButton);
			dynamicButton.setTag(numButton);
			dynamicButton.setOnClickListener(OnClickListener);
			
			dynamicLayout.addView(dynamicButton, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			
			
			numButton++;
			
			view.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)view.getTag();
		}
		
		setCatImage(position, viewHolder);
		
		return view;
	}
	
	
	View.OnClickListener OnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//Button removeBtn = (Button)v.findViewById(DYNAMIC_BTN_ID+viewHolder.numButton);
			
			String str = v.getTag().toString();
			int strint = Integer.parseInt(str);
			
			for(int i = 0 ; i < btnNum.size(); i++){
				
				if(btnNum.get(i) == strint){
					
					images.remove(i);
					newGridItems.remove(i);
					//numButton--; 
					notifyDataSetChanged();
				}
			}
		}
	};
	

	public void setCatImage(int pos, ViewHolder viewHolder) {		
		viewHolder.imageView.setImageDrawable(images.get(pos).getDrawable());
	}
}
