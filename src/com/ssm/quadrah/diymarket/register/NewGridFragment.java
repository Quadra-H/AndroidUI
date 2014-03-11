package com.ssm.quadrah.diymarket.register;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.ssm.quadrah.diymarket.R;
import com.ssm.quadrah.diymarket.designeditor.DesignEditorTool;
import com.ssm.quadrah.diymarket.profile.DesignerProfile;

@SuppressLint("ValidFragment")
public class NewGridFragment extends Fragment{
	
	

	static final int IMAGE_REQUSET = 1;
	static final int IMAGE_RE_REQUEST = 2;
	
	private static final int IMAGE_DPI_1280_800 = 0;
	private static final int IMAGE_DPI_1920_1200 = 1;
	private static final int IMAGE_DPI_1280_720 = 2;
	private static final int IMAGE_DPI_1920_1080 = 3;
	
	private static final int PICK_FROM_CAMERA = 3;
	private static final int PICK_FROM_ALBUM = 4;
	private static final int CROP_FROM_CAMERA = 5;
    private Uri mImageCaptureUri;
    
    private String strLast = "_lst";
    
	private GridView mGridView;
	private NewGridAdapter mGridAdapter;
	//private ArrayList<NewGridItems> newGridItems;
	private LinkedList<NewGridItems> newGridItems;	
	private Activity activity;	
	private List<NewGridFragment> gridFragments;
	protected ViewPager mPager;
	
	private int dpi_width;
	private int dpi_height;
	
	private boolean mCreate;
	ViewHolder viewHolder;
	
	
	
	public NewGridFragment(LinkedList<NewGridItems> newGridItems, Activity activity, List<NewGridFragment> gridFragments){
		
		this.newGridItems = newGridItems;
		this.activity = activity;		
		this.gridFragments = gridFragments;
		
		mPager = (ViewPager)activity.findViewById(R.id.pager2);		
		viewHolder = new ViewHolder();		
		mCreate = true;      
	}
	
	public NewGridFragment(LinkedList<NewGridItems> newGridItems, Activity activity, List<NewGridFragment> gridFragments, ViewPager mPager)
	{
		this.activity = activity;
		this.newGridItems = newGridItems;	
		this.gridFragments = gridFragments;
		this.mPager = mPager;
		
		mCreate =true;
	}
	

	
	
	DialogInterface mPopupDlg = null;
	public int staticPosition;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
		
		// TODO Auto-generated method stub		
		if(activity != null){
			
			if(newGridItems.get(0).image == null){
				newGridItems.get(0).image = new ImageView(activity);
				newGridItems.get(0).image.setImageResource(R.drawable.register_add_btn); // basicImage
			}
			

			
			mGridAdapter = new NewGridAdapter(activity, newGridItems);
			
			
			if(mGridView != null){
				mGridView.setAdapter(mGridAdapter);
			}
			
			mGridView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub		

					 Bundle extras = new Bundle();
					
					if(newGridItems.get(position).title != "registerBasic"){

						Intent intent = new Intent(getActivity(), DesignEditorTool.class);
						intent.putExtras(extras);

						intent.putExtra("position", position);
						intent.putExtra("title", newGridItems.get(position).title);
						intent.putExtra("dpi_width", newGridItems.get(position).width);
						intent.putExtra("dpi_height", newGridItems.get(position).height);
						
						
						Log.d("saveFilePathSPD", newGridItems.get(position).saveFilePathSPD); 
						
						
						startActivityForResult(intent, IMAGE_REQUSET); 	 	
					}
					else {						
						
						String dpi[] ={"1280*800","1920*1200","1280*720","1920*1080"};
				        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
				        LayoutInflater inflater = activity.getLayoutInflater();
				        View convertView = (View) inflater.inflate(R.layout.dialog_work_list, null);
				        alertDialog.setView(convertView);		        
				        ListView lv = (ListView) convertView.findViewById(R.id.listview_profile);
				        
				        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,android.R.layout.simple_list_item_1, dpi);
				        lv.setAdapter(adapter);
				        
				        staticPosition = position;
				        
				        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener()
					    {
					      @Override
					      public void onClick(DialogInterface dialog, int which)
					      {
					        dialog.dismiss();
					      }
					    };
					    
					    lv.setOnItemClickListener(new OnItemClickListener(){
				        	@Override
							public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				        		
				        		Intent intent = new Intent(getActivity(), DesignEditorTool.class);	
				        		switch(position)
				        		{
				        		case IMAGE_DPI_1280_800:	
				        			dpi_width = 1280;
				        			dpi_height = 800;
				        			break;
				        		case IMAGE_DPI_1920_1200:
				        			dpi_width = 1920;
				        			dpi_height = 1200;   		
				        			break;
				        		case IMAGE_DPI_1280_720:
				        			dpi_width = 1280;
				        			dpi_height = 720;     				        			
				        			break;
				        		case IMAGE_DPI_1920_1080:
				        			dpi_width = 1920;
				        			dpi_height = 1080;	
				        			break;
				        		}
				        		
				        		
				        		intent.putExtra("position", staticPosition);
				        		intent.putExtra("dpi_width", newGridItems.get(staticPosition).width);
				        	    intent.putExtra("dpi_height", newGridItems.get(staticPosition).height);
				        	    
				        	    newGridItems.get(staticPosition).width = dpi_width;
				        	    newGridItems.get(staticPosition).height = dpi_height;
					        	newGridItems.get(staticPosition).strType = MarketRegister.strType;
					        	newGridItems.get(staticPosition).saveFilePathPNG += newGridItems.get(staticPosition).strType.toString();
					        	
					        	Log.d("saveFilePathPNG", newGridItems.get(staticPosition).saveFilePathPNG); 
					        	
					        	intent.putExtra("strType", newGridItems.get(staticPosition).strType);
					        	
					        	startActivityForResult(intent, IMAGE_REQUSET); 	
				        		mPopupDlg.dismiss();
				        	}
				        });
				        
				        mPopupDlg = alertDialog.setTitle("해상도를 선택하세요.").setNegativeButton("취소", cancelListener).show();
				        
				       
						
						
						
						
				        
				        
						
						
					}
				}
			});
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
			newGridItems.get(position).width = data.getIntExtra("dpi_width", 0);
			newGridItems.get(position).height = data.getIntExtra("dpi_height", 0);
			newGridItems.get(position).saveFilePathPNG = data.getStringExtra("PNG");
			newGridItems.get(position).saveFilePathSPD = data.getStringExtra("SPD");
			
			Log.d("saveFilePathSPD", newGridItems.get(position).saveFilePathSPD); 
			
			//String saveFilePath = data.getStringExtra("saveFilePath");
			File imgFile = new  File(newGridItems.get(position).saveFilePathPNG);
			
			
			Log.d("title", "title : " + data.getStringExtra("title"));
			final Bundle extras = data.getExtras();
			
			
			
			
			if(requestCode == IMAGE_REQUSET)
			{					
				Bitmap myBitmap = null;
				if(imgFile.exists()){

				    myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
				}	
							
				if(newGridItems.size() % 12 == 0 && mCreate)
				{
					
					ImageView image = new ImageView(getActivity());
					image.setImageBitmap(myBitmap);
					
					newGridItems.get(position).image.setImageDrawable(image.getDrawable());
									
					mGridAdapter.notifyDataSetChanged();
					
					position = 0;
					
					
					LinkedList<NewGridItems> itmList = new LinkedList<NewGridItems>();
					NewGridItems itm7 = new NewGridItems(position, "registerBasic");
					
					itmList.add(itm7);
					image = new ImageView(getActivity());
				    image.setImageResource(R.drawable.register_add_btn);
					itm7.image = image;
					
					
					
					mCreate = false;
					
					gridFragments.add(new NewGridFragment(itmList, activity, gridFragments,  mPager));
					mPager.getAdapter().notifyDataSetChanged();
				}
				else if(newGridItems.get(position).title != "registerBasic"){
					
					
							
					ImageView image = new ImageView(getActivity());
					image.setImageBitmap(myBitmap);
					newGridItems.get(position).image.setImageDrawable(image.getDrawable());
					
					
					mGridAdapter.notifyDataSetChanged();
					
					if(newGridItems.size() < 12 && newGridItems.get(position).flag == 1){
						newGridItems.get(position).flag = 0;
						image = new ImageView(getActivity());
					    image.setImageResource(R.drawable.register_add_btn);
					    
						position = position + 1;
						
			            NewGridItems newGridItem = new NewGridItems(position, "registerBasic");
			            newGridItem.image = image;
			            
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
		

		return view;
	}
	
}
