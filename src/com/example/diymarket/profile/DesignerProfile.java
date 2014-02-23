package com.example.diymarket.profile;


import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.diymarket.R;
import com.example.diymarket.content.DetailItems;
import com.example.diymarket.content.Items;

public class DesignerProfile extends ListActivity {

	private RelativeLayout rlBusinessCard;
	private ImageView ivProfile;
	private ImageView ivSpecial;
	private ImageView ivGithub;
	private ImageView ivTwitter;
	private TextView tvName;
	private TextView tvLocation;
	private TextView tvTitle;
	private TextView tvAbout;
	private TextView tvEndorsements;
	DetailItems detailItem_adapter = null;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_profile);
	    // TODO Auto-generated method stub
	    DetailItems adapter=(DetailItems)getLastNonConfigurationInstance();
	    
	    if (adapter==null) {	     
	      ArrayList<Items> listItems = new ArrayList<Items>();  
	      
	      for (int i=0;i<25;i++) {
	    	  Items item = new Items("111","222","Love");
	    	  listItems.add(item); 
	      }
	      
	      adapter=new DetailItems(this, R.layout.listview_row,listItems);
	    }
	    else {
	      adapter.startProgressAnimation();
	    }
	    
	    setListAdapter(adapter);
	  }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.menu, menu);		 
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch(item.getItemId()){
		case R.id.action_profile_edit :
			Intent i = new Intent(DesignerProfile.this ,DesignerProfileEdit.class);
			startActivity(i);
			
			break;		
		default:
			break;
		
		}
		return super.onOptionsItemSelected(item);
	}
	
	

}
