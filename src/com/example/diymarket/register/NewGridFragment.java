package com.example.diymarket.register;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.diymarket.R;
import com.example.diymarket.register.MarketRegister.PagerAdapter;

@SuppressLint("ValidFragment")
public class NewGridFragment extends Fragment{

	static final int IMAGE_REQUSET = 1;
	static final int IMAGE_RE_REQUEST = 2;
	
	int i;
	
	private GridView mGridView;
	private NewGridAdapter mGridAdapter;
	private ArrayList<NewGridItems> newGridItems;
	private ArrayList<ImageView> images = null;
	
	private Activity activity;	
	List<NewGridFragment> gridFragments;
	protected ViewPager mPager;
	
	private boolean mCreate;
	ViewHolder viewHolder;
	
	public ArrayList<ImageView> getImageList()
	{
		return images;
	}
	
	public NewGridFragment(NewGridItems newGridItem, Activity activity, List<NewGridFragment> gridFragments, PagerAdapter pm){
		if(newGridItems == null)
			newGridItems = new ArrayList<NewGridItems>();
		
		newGridItems.add(newGridItem);
		i = 0;
		this.gridFragments = gridFragments;
		this.activity = activity;
		
		mPager = (ViewPager)activity.findViewById(R.id.pager2);
		viewHolder = new ViewHolder();
		
		mCreate = true;      
	}
	
	public NewGridFragment(ArrayList<NewGridItems> itmLst,List<NewGridFragment> gridFragments, Activity activity, ViewPager mPager)
	{
		i = 0;
		this.activity = activity;
		this.newGridItems = itmLst;	
		this.gridFragments = gridFragments;
		this.mPager = mPager;
		mCreate =true;
	}
	
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
		
		// TODO Auto-generated method stub		
		if(activity != null){
			if(images == null){
				images = new ArrayList<ImageView>();
				ImageView image = new ImageView(getActivity());
			    image.setImageResource(R.drawable.arrow); // basic + image
			    //image.setLayoutParams(new FrameLayout.LayoutParams(120, 30, Gravity.CENTER));
			    images.add(image);
			}
				

			mGridAdapter = new NewGridAdapter(activity, newGridItems, images);
			
			if(mGridView != null){
				mGridView.setAdapter(mGridAdapter);
			}
			mGridView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub		
					
					
					if(newGridItems.get(position).title != "registerBasic"){
						
						ImageView tempImage = images.get(position);
					    BitmapDrawable bitmapDrawable = ((BitmapDrawable) tempImage.getDrawable());
					    Bitmap image = bitmapDrawable.getBitmap(); 
					    Bundle extras = new Bundle();
					    extras.putParcelable("imagebitmap", image);
					    
						Intent intent = new Intent(getActivity(), EditTool.class);
						intent.putExtras(extras);
						intent.putExtra("position", position);
						
						startActivityForResult(intent, IMAGE_REQUSET); 	 	
					}
					else {
						Intent intent = new Intent(getActivity(), EditTool.class);
						intent.putExtra("position", position);
						startActivityForResult(intent, IMAGE_REQUSET); 	 	
					}
				}
			});
		}
	}
	
	

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		int position = 0;
		
		if(resultCode == -1){
	
			if(requestCode == IMAGE_REQUSET)
			{
				
				position = data.getIntExtra("position", 0);
				
				if(newGridItems.size() % 9 == 0 && mCreate)
				{
					//i = i + 1;
					
					Toast.makeText(activity, "if : i : " + i, Toast.LENGTH_LONG).show();
					Bitmap thumbnail = (Bitmap) data.getExtras().get("img");
					ImageView image = new ImageView(getActivity());
					image.setImageBitmap(thumbnail);
					//viewHolder.imageView.setImageDrawable(images.get(pos).getDrawable());
					images.get(position).setImageDrawable(image.getDrawable());
					mGridAdapter.notifyDataSetChanged();
					
					position = 0;
					
					ArrayList<NewGridItems> itmLst = new ArrayList<NewGridItems>();
					NewGridItems itm7 = new NewGridItems(position, "registerBasic");
					itmLst.add(itm7);
					
					mCreate = false;
					gridFragments.add(new NewGridFragment(itmLst, gridFragments, activity, mPager));
					mPager.getAdapter().notifyDataSetChanged();
				}	
				else if(newGridItems.get(position).title != "registerBasic"){
					Bitmap thumbnail = (Bitmap) data.getExtras().get("img");
					ImageView image = new ImageView(getActivity());
					image.setImageBitmap(thumbnail);
					//viewHolder.imageView.setImageDrawable(images.get(pos).getDrawable());
					images.get(position).setImageDrawable(image.getDrawable());
					
					newGridItems.get(position).title = data.getStringExtra("title");
				}
				else if (newGridItems.size() < 9 && newGridItems.get(position).title == "registerBasic"){ 
					Bitmap thumbnail = (Bitmap) data.getExtras().get("img");
					ImageView image = new ImageView(getActivity());
					image.setImageBitmap(thumbnail);
					//viewHolder.imageView.setImageDrawable(images.get(pos).getDrawable());
					//images.get(i).setImageDrawable(image.getDrawable());
					images.get(position).setImageDrawable(image.getDrawable());
					
					//newGridItems.get(i).title = data.getStringExtra("title");
					newGridItems.get(position).title = data.getStringExtra("title");
					//Toast.makeText(activity, "else if : i : " + i, Toast.LENGTH_LONG).show();
					image = new ImageView(getActivity());
				    image.setImageResource(R.drawable.arrow);
					
					images.add(image);
					
					//i = i + 1;
					position = position + 1;
		            NewGridItems newGridItem = new NewGridItems(position, "registerBasic");
		            
		            newGridItems.add(newGridItem);      
					mGridAdapter.notifyDataSetChanged();
				}
			}
		}
	}
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.new_grid, container, false);
		mGridView = (GridView)view.findViewById(R.id.newGridView);
		return view;
	}
	
}
