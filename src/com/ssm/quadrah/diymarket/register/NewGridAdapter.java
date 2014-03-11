package com.ssm.quadrah.diymarket.register;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.ssm.quadrah.diymarket.R;

public class NewGridAdapter extends BaseAdapter{

	private Context context;
	private LinkedList<NewGridItems> newGridItems;

	private LayoutInflater mInflater;	
	public ViewHolder viewHolder = null;


	

	public NewGridAdapter(Context context, LinkedList<NewGridItems> newGridItems)
	{
		this.context = context;
		this.newGridItems = newGridItems;

		
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		public void onClick(final View v) {
			// TODO Auto-generated method stub
			
			AlertDialog.Builder dlg = new AlertDialog.Builder(context);
            dlg.setIcon(context.getResources().getDrawable(android.R.drawable.ic_dialog_alert));
            dlg.setTitle(context.getResources().getString(R.string.app_name))
                    .setMessage("삭제하시겠습니까?")
                    .setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            
                        	NewGridItems removeItem = (NewGridItems)v.getTag();

                			Log.d("removeItem", "remove : " + removeItem.id );
                			for(Iterator<NewGridItems> it = newGridItems.iterator(); it.hasNext();)
                			{
                				NewGridItems removeWork = it.next();

                				if(removeWork.id == removeItem.id && removeWork.title == removeItem.title && removeWork.title != "registerBasic")
                				{
                					it.remove();

                					
                					if(newGridItems.size() == 11 && newGridItems.getLast().title != "registerBasic")
                					{
                						ImageView image = new ImageView(context);
                					    image.setImageResource(R.drawable.register_add_btn);
                			            NewGridItems newGridItem = new NewGridItems(12, "registerBasic");
                			            newGridItem.image = image;
                			            newGridItems.add(newGridItem);
                			            break;
                					}
                				}
                			}						

                			notifyDataSetChanged();
                			
                            dialog.dismiss();
                        }
                    }).setNeutralButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            
                            
                        }
                    }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            dlg = null;
            
			
		}
	};



	public void setCatImage(int pos, ViewHolder viewHolder) {	
		
		viewHolder.imageView.setImageDrawable(newGridItems.get(pos).image.getDrawable());
		viewHolder.imageView.setAdjustViewBounds(true);
		viewHolder.btnX.setVisibility(View.GONE);
		viewHolder.btnX.setTag(newGridItems.get(pos));	
		
		
		if(newGridItems.get(pos).title != "registerBasic"){
			viewHolder.btnX.setVisibility(View.VISIBLE);

		}
	}
}
