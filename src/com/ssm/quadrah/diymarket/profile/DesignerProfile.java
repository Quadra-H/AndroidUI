package com.ssm.quadrah.diymarket.profile;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ssm.quadrah.diymarket.DesignerAccount;
import com.ssm.quadrah.diymarket.R;
import com.ssm.quadrah.diymarket.content.DetailItems;
import com.ssm.quadrah.diymarket.content.Items;
import com.ssm.quadrah.diymarket.register.ActivitySplitAnimationUtil;
import com.ssm.quadrah.diymarket.register.MarketRegister;

public class DesignerProfile extends ListActivity {

	private RelativeLayout rlBusinessCard;
	private ImageView ivProfile;
	private TextView tvName;
	private TextView tvState;
	private Button btnWorkAdd;
	
	DetailItems adapter;
	
	private String strID;
	private String strCompareID;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_profile);
	    // TODO Auto-generated method stub
	    
	    ActionBar bar = getActionBar();
	    bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f34022")));
	    bar.setHomeButtonEnabled(true);
	    
	    
	    
	   
	    btnWorkAdd = (Button)findViewById(R.id.btnWorkAdd);
	    btnWorkAdd.setOnClickListener(OnClickWorkAdd);
	    ivProfile = (ImageView)findViewById(R.id.imageViewProfilePicture);
	    tvName = (TextView)findViewById(R.id.textViewProfileName);
	    tvState = (TextView)findViewById(R.id.textViewState);
	    
//	    strID = getIntent().getStringExtra("Account");//error
//	    strCompareID = ((DesignerAccount)getApplication()).getAccount(); 
//	    if(!strID.equals(strCompareID))
//	    {	    	
//	    	btnWorkAdd.setVisibility(View.GONE);
//	    }
	    
	    //ivProfile.setImageResource(R.drawable.item1);
	    
	    //DetailItems adapter=(DetailItems)getLastNonConfigurationInstance();
	    adapter=(DetailItems)getLastNonConfigurationInstance();
	    
	    
	    if (adapter==null)  {	     
	      ArrayList<Items> listItems = new ArrayList<Items>();  
	      
	      for (int i=0;i<25;i++) {
	    	  //listItems.add(i);
	    	  Items item = new Items("Image title 01","DesignerID","Free");
	    	  listItems.add(item); 	    	  
	      }
	      
	      //adapter=new DetailItems(this, listItems);
	      adapter=new DetailItems(this, listItems);
	    }
	    else {
	      adapter.startProgressAnimation();
	    }
	    
	    setListAdapter(adapter);
	  }
	
	View.OnClickListener OnClickWorkAdd = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v.getId() == R.id.btnWorkAdd){				
				ActivitySplitAnimationUtil.startActivity(DesignerProfile.this, new Intent(DesignerProfile.this, MarketRegister.class));
			}
		}
	};
	
	@Override
	  public Object getLastNonConfigurationInstance() {
	    return(getListAdapter());
	  }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		//if(strID.equals(strCompareID))
	    //{	    	
			getMenuInflater().inflate(R.menu.menu, menu);	
	    //}
		
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch(item.getItemId()){
		case R.id.action_profile_edit :
			
			Intent i = new Intent(DesignerProfile.this ,DesignerProfileEdit.class);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);;
			break;		
		
			
		case android.R.id.home:
			finish();
			break;
			
		default:
		
		}
		return super.onOptionsItemSelected(item);
	}
	
	

}
