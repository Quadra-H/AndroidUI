package com.ssm.quadrah.diymarket.profile;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ssm.quadrah.diymarket.DesignerAccount;
import com.ssm.quadrah.diymarket.R;
import com.ssm.quadrah.diymarket.content.DetailItems;
import com.ssm.quadrah.diymarket.content.Items;
import com.ssm.quadrah.diymarket.content.ItemsPackage;
import com.ssm.quadrah.diymarket.register.ActivitySplitAnimationUtil;
import com.ssm.quadrah.diymarket.register.MarketRegister;

public class DesignerProfile extends Activity {

	private RelativeLayout rlBusinessCard;
	private ImageView ivProfile;
	private TextView tvName;
	private TextView tvState;
	private Button btnWorkAdd;
	
	DetailItems adapter;
	
	protected static final int LAYOUT_MENU = 0;
	protected static final int BACKGROUND_MENU = 1;
	protected static final int FRAME_MENU = 2;
	protected static final int STICKER_MENU = 3;
	
	DetailItems detailItem_adapter = null;
	
	private static String strID;	
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
	    
	   
	    Bundle getUserID = getIntent().getExtras();
	    if(getUserID != null){
	    	strID = getIntent().getStringExtra("Account");
	    }
	    
	    
	    
	    ListView listView = (ListView)findViewById(R.id.listviewProfile);
		ArrayList<Items> listItems = new ArrayList<Items>();  
		
		
		for(int i = 0; i < 25; i++){				
			Items item = new Items("Image title 01","DesignerID","Free");
			listItems.add(item);
		}
		detailItem_adapter = new DetailItems(this, listItems);		
		
		if(strID != null)
	    {
		    strCompareID = ((DesignerAccount)getApplication()).getAccount(); 
		    if(!strID.equals(strCompareID))
		    {
		    	btnWorkAdd.setVisibility(View.GONE);		    	
		    }		    
	    }
		
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
				if(strID != null)
			    {
				    strCompareID = ((DesignerAccount)getApplication()).getAccount(); 
				    if(!strID.equals(strCompareID))
				    {
				    	Intent i = new Intent(DesignerProfile.this, ItemsPackage.class);
						startActivity(i);
						overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
				    	
				    }
				    else
				    {
				    	Intent i = new Intent(DesignerProfile.this, MarketRegister.class);				    	
				    	ActivitySplitAnimationUtil.startActivity(DesignerProfile.this, i);
				    }
			    }
				
			}
			
		});

		
		
		
        listView.setAdapter(detailItem_adapter);

	}
	
	DialogInterface mPopupDlg = null;
	
	View.OnClickListener OnClickWorkAdd = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v.getId() == R.id.btnWorkAdd){		
				
				String names[] ={"Layout","Background","Frame","Sticker"};
		        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DesignerProfile.this);
		        LayoutInflater inflater = getLayoutInflater();
		        View convertView = (View) inflater.inflate(R.layout.dialog_work_list, null);
		        alertDialog.setView(convertView);		        
		        ListView lv = (ListView) convertView.findViewById(R.id.listview_profile);
		        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DesignerProfile.this,android.R.layout.simple_list_item_1, names);
		        lv.setAdapter(adapter);
		        
		       		    
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
		        		
		        		Intent i = new Intent(DesignerProfile.this, MarketRegister.class);
		        		switch(position)
		        		{
		        		case LAYOUT_MENU:	
		        			mPopupDlg.dismiss();		        			
		        			ActivitySplitAnimationUtil.startActivity(DesignerProfile.this, i);
		        			break;
		        		case BACKGROUND_MENU:
		        			mPopupDlg.dismiss();		        			
		        			ActivitySplitAnimationUtil.startActivity(DesignerProfile.this, i);		        			
		        			break;
		        		case FRAME_MENU:
		        			mPopupDlg.dismiss();		        			
		        			ActivitySplitAnimationUtil.startActivity(DesignerProfile.this, i);		        					        			
		        			break;
		        		case STICKER_MENU:
		        			mPopupDlg.dismiss();
		        			
		        			ActivitySplitAnimationUtil.startActivity(DesignerProfile.this, i);		        			
		        			break;
		        		}
		        	}
		        });
		        
		        mPopupDlg = alertDialog.setTitle("작업할 메뉴를 선택하세요.").setNegativeButton("취소", cancelListener).show();
		        
				
			}
		}
	};
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
	
		if(strID != null){
			if(strID.equals(strCompareID))
		    {	    	
				getMenuInflater().inflate(R.menu.menu, menu);				
			}
		}

		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch(item.getItemId()){
		case R.id.action_profile_edit :
			
			Intent i = new Intent(DesignerProfile.this ,DesignerProfileEdit.class);
			i.putExtra("Account", strID);
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
