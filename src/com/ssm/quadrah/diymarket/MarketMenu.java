package com.ssm.quadrah.diymarket;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ssm.quadrah.diymarket.content.MarketMenuLayout;
import com.ssm.quadrah.diymarket.profile.DesignerProfile;

public class MarketMenu extends Activity {
	
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_marketmenu);
	    // TODO Auto-generated method stub
	    Button btnLayout = (Button)findViewById(R.id.btnLayout);
	    btnLayout.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
	    btnLayout.setOnClickListener(OnClickBtnMenu);	    
	    
	    Button btnBackground = (Button)findViewById(R.id.btnBackground);
	    btnBackground.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
	    btnBackground.setOnClickListener(OnClickBtnMenu);
	    
	    Button btnSticker = (Button)findViewById(R.id.btnSticker);
	    btnSticker.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
	    btnSticker.setOnClickListener(OnClickBtnMenu);
	    
	    Button btnFrame = (Button)findViewById(R.id.btnFrame);
	    btnFrame.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
	    btnFrame.setOnClickListener(OnClickBtnMenu);
	    
	    Button btnMy = (Button)findViewById(R.id.btnMy);
	    btnMy.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
	    btnMy.setOnClickListener(OnClickBtnMenu);
	    
	    
	    
	    ActionBar bar = getActionBar();
	    bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f34022")));
	    
	    bar.setHomeButtonEnabled(true);
	    bar.setDisplayShowHomeEnabled(false);
	    
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch(item.getItemId()){
					
		case android.R.id.home:
			finish();
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
			break;
	
		default:
		}
		
	
		return super.onOptionsItemSelected(item);
	}
	
	View.OnClickListener OnClickBtnMenu = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i;
			switch(v.getId())
			{
			
			case R.id.btnLayout:
				i = new Intent(MarketMenu.this, MarketMenuLayout.class);
				i.putExtra("type", Constants.TYPE_LAYOUT);			
				startActivity(i);	
				break;
			case R.id.btnBackground:
				i = new Intent(MarketMenu.this, MarketMenuLayout.class);
				i.putExtra("type", Constants.TYPE_BACKGROUND);			
				startActivity(i);	
				break;
			case R.id.btnSticker:
				i = new Intent(MarketMenu.this, MarketMenuLayout.class);
				i.putExtra("type", Constants.TYPE_STICKER);			
				startActivity(i);	
				break;
			case R.id.btnFrame:
				i = new Intent(MarketMenu.this, MarketMenuLayout.class);
				i.putExtra("type", Constants.TYPE_FRAME);			
				startActivity(i);	
				break;
			case R.id.btnMy:
				i = new Intent(MarketMenu.this, DesignerProfile.class);
				i.putExtra("type", Constants.TYPE_PROFILE);	
				i.putExtra("Account", ((DesignerAccount)getApplication()).getAccount());
				startActivity(i);
				break;
			}
		}
	};	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
	}

}
