package com.ssm.quadrah.diymarket.register;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.ssm.quadrah.diymarket.R;
import com.ssm.quadrah.diymarket.register.MarketRegister.PagerAdapter;

@SuppressLint("ValidFragment")
public class NewGridFragment extends Fragment{

	static final int IMAGE_REQUSET = 1;
	static final int IMAGE_RE_REQUEST = 2;
	
	private static final int PICK_FROM_CAMERA = 3;
	private static final int PICK_FROM_ALBUM = 4;
	private static final int CROP_FROM_CAMERA = 5;
    private Uri mImageCaptureUri;
    
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
	
	public NewGridFragment(ArrayList<NewGridItems> newGridItems, Activity activity, List<NewGridFragment> gridFragments, PagerAdapter pm){
		
		this.newGridItems = newGridItems;
		this.activity = activity;		
		this.gridFragments = gridFragments;
		
		
		
		mPager = (ViewPager)activity.findViewById(R.id.pager2);		
		viewHolder = new ViewHolder();		
		mCreate = true;      
	}
	
	public NewGridFragment(ArrayList<NewGridItems> itmLst,List<NewGridFragment> gridFragments, Activity activity, ViewPager mPager)
	{
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
			    image.setImageResource(R.drawable.arrow); // basicImage
			    
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
						functionPicture(position);
					}
					
					
					onGridItemClick((GridView) parent, view, position, id);
				}
			});
		}
	}
	
	
		


	public void onGridItemClick(GridView g, View v, int position, long id) {
		
		ViewHolder viewHolder = (ViewHolder)v.getTag();
//		Log.d("v.getId", " : " + v.getId());
		Log.d("g.getId", " : " + g.getId());
		Log.d("viewHolder.btnX.getId()", " : " + R.id.btnX);	
		if(g.getId() == viewHolder.btnX.getId()){
			NewGridItems removeItem = (NewGridItems)viewHolder.btnX.getTag();
			
			
			for(Iterator<NewGridItems> it = newGridItems.iterator(); it.hasNext();)
			{
				NewGridItems removeWork = it.next();				
				if(removeWork.id == removeItem.id)
				{
					it.remove();
					Log.d("removeWork", " " + removeWork.id);	
					images.remove(position);
				}
			}									
			
			mGridAdapter.notifyDataSetChanged();
		}
			
		
	}
 
	

	
	public void functionPicture(final int position){
		
			
		    
			DialogInterface.OnClickListener editTool = new DialogInterface.OnClickListener()
		    {
		      @Override
		      public void onClick(DialogInterface dialog, int which)
		      {
					Intent intent = new Intent(getActivity(), EditTool.class);	
					intent.putExtra("position", position);	
					startActivityForResult(intent, IMAGE_REQUSET); 	 	
		      }
		    };
		    
		    DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener()
		    {
		      @Override
		      public void onClick(DialogInterface dialog, int which)
		      {
		        doTakeAlbumAction(position);
		      }
		    };
		    
		    DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener()
		    {
		      @Override
		      public void onClick(DialogInterface dialog, int which)
		      {
		        dialog.dismiss();
		      }
		    };
		    
		    AlertDialog.Builder alert = new AlertDialog.Builder(activity);
		    
		    alert.setTitle("추가 방식 선택") .setPositiveButton("직접 그리기", editTool)
		      .setNeutralButton("불러오기", albumListener)
		      .setNegativeButton("취소", cancelListener)
		      .show();
		
	}
	
	static int imagePoint;
	
	private void doTakeAlbumAction(int position)
	{
		Intent intent = new Intent(Intent.ACTION_PICK);
	    intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
	    startActivityForResult(intent, PICK_FROM_ALBUM);
		imagePoint = position;
        
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
			
		
		
		if(resultCode == -1){

			int position;	
			position = data.getIntExtra("position", 0);	
			
			newGridItems.get(position).title = data.getStringExtra("title");
			Log.d("title", "title : " + data.getStringExtra("title"));
			final Bundle extras = data.getExtras();
			
			
			
			
			if(requestCode == IMAGE_REQUSET)
			{					
				
				if(newGridItems.size() % 12 == 0 && mCreate)
				{
					Bitmap thumbnail = (Bitmap) data.getExtras().get("img");
					ImageView image = new ImageView(getActivity());
					image.setImageBitmap(thumbnail);
					images.get(position).setImageDrawable(image.getDrawable());
					mGridAdapter.viewHolder.btnX.setVisibility(View.VISIBLE);
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
					Log.d("position", "position : " + position);
					
					Bitmap thumbnail = (Bitmap) data.getExtras().get("img");
					ImageView image = new ImageView(getActivity());
					image.setImageBitmap(thumbnail);
					images.get(position).setImageDrawable(image.getDrawable());
					mGridAdapter.viewHolder.btnX.setVisibility(View.VISIBLE);
					//newGridItems.get(position).btnX.setVisibility(View.VISIBLE);
					
					mGridAdapter.notifyDataSetChanged();
					
					
					
					if(newGridItems.size() < 12 && newGridItems.get(position).flag == 1){
						newGridItems.get(position).flag = 0;
						image = new ImageView(getActivity());
					    image.setImageResource(R.drawable.arrow);
					    
						images.add(image);
						
						position = position + 1;
						
			            NewGridItems newGridItem = new NewGridItems(position, "registerBasic");
			            
			            newGridItems.add(newGridItem);			            
						mGridAdapter.notifyDataSetChanged();
					}
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
		//mGridView.setOverScrollMode(View.OVER_SCROLL_NEVER);
		return view;
	}
	
}
