package com.ssm.quadrah.diymarket.register;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ssm.quadrah.diymarket.R;
import com.ssm.quadrah.diymarket.content.circle.CirclePageIndicator;
import com.ssm.quadrah.diymarket.content.tab.PageIndicator;
import com.ssm.quadrah.diymarket.profile.DesignerProfileName;

public class MarketRegister extends FragmentActivity {

	protected NewGridAdapter mAdapter;
	
    protected ViewPager mPager;
    protected PageIndicator mIndicator;
    public static PagerAdapter pm;
    
    private TextView editWorkTitle;
    private TextView editWorkCost;
    private ImageView imageView;
    private Button btnRegister;
  
    private ArrayList<NewGridItems> newGridItems;
    public static List<NewGridFragment> gridFragments;
    
    ArrayList<String> a;
    Iterator<String> it;
   
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    ActivitySplitAnimationUtil.prepareAnimation(this);
	    setContentView(R.layout.activity_register);
	    ActivitySplitAnimationUtil.animate(this, 1000);
	    // TODO Auto-generated method stub
	    
	   
	    
	    editWorkTitle = (TextView)findViewById(R.id.EditDesignerName);
	    editWorkCost = (TextView)findViewById(R.id.EditDesignerName);	    
	    
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(OnClickBtnRegister);
        
        imageView = (ImageView)findViewById(R.id.imageViewProfile);
        imageView.setOnClickListener(OnClickImage);
        
        mPager = (ViewPager)findViewById(R.id.pager2);        
        mIndicator = (CirclePageIndicator)findViewById(R.id.pagerIndicator1);
        
        newGridItems = new ArrayList<NewGridItems>();
        
        NewGridItems basicItem = new NewGridItems(0, "registerBasic");    
        newGridItems.add(basicItem);
        
        gridFragments = new ArrayList<NewGridFragment>();        
        gridFragments.add(new NewGridFragment(newGridItems, MarketRegister.this, gridFragments, pm));
        
        pm = new PagerAdapter(getSupportFragmentManager(), gridFragments);
        mPager.setAdapter(pm);
        mIndicator.setViewPager(mPager);
       
	}	
	

	
	View.OnClickListener OnClickBtnRegister = new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			//ArrayList<ImageView> list = gridFragments.get(0).getImageList();
			//ArrayList<ImageView> list1 = gridFragments.get(1).getImageList()
			
			
			finish();
		}
	};
	
	public void functionTitle(View v){
		switch(v.getId()){
		case R.id.textViewTitle:
			Intent i = new Intent(MarketRegister.this, DesignerProfileName.class);
			startActivityForResult(i, 10);
		}
	}
	BitmapDrawable bitmapDrawable;

	int position = 0;;
	View.OnClickListener OnClickImage = new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			Intent i = new Intent(MarketRegister.this, EditTool.class);
			startActivityForResult(i, 30);
		}
	};
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == RESULT_OK)
		{
			if(requestCode == 10)
			{				
				editWorkTitle.setText(data.getStringExtra("data_title"));				
			}
			else if(requestCode == 20)
			{
				editWorkCost.setText(data.getStringExtra("data_cost"));				
			}
			else if(requestCode == 30)
			{				
				Bitmap thumbnail = (Bitmap) data.getExtras().get("img");				
				imageView.setImageBitmap(thumbnail);
			}
			
		}
	}

	public class PagerAdapter extends FragmentStatePagerAdapter {
		  private List<NewGridFragment> fragments;
		 
		  @Override
		public void notifyDataSetChanged() {
			// TODO Auto-generated method stub
			super.notifyDataSetChanged();
		}

		public PagerAdapter(FragmentManager fm, List<NewGridFragment> fragments) {
		   super(fm);
		   this.fragments = fragments;
		  }
		 
		  @Override
		  public Fragment getItem(int position) {
		   return this.fragments.get(position);
		  }
		 
		  @Override
		  public int getCount() {
		   return this.fragments.size();
		  }
		 }

	@Override
    protected void onStop() {
        // If we're currently running the entrance animation - cancel it
        ActivitySplitAnimationUtil.cancel();

        super.onStop();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
