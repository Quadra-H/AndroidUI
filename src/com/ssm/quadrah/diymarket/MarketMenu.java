package com.ssm.quadrah.diymarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
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
	    btnLayout.setOnClickListener(OnClickBtnLayout);
	    
	    Button btnMy = (Button)findViewById(R.id.btnMy);
	    btnMy.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
	    btnMy.setOnClickListener(OnClickBtnMy);
	    
	    Button btnBackground = (Button)findViewById(R.id.btnBackground);
	    btnBackground.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
	    btnBackground.setOnClickListener(OnClickBtnBackground);
	}
	
	View.OnClickListener OnClickBtnBackground = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
	};
	
	View.OnClickListener OnClickBtnLayout = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(MarketMenu.this, MarketMenuLayout.class);
			i.putExtra("type", Constants.TYPE_LAYOUT);			
			startActivity(i);			
		}
	};
	
	View.OnClickListener OnClickBtnMy = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(MarketMenu.this, DesignerProfile.class);
			startActivity(i);
			
		}
	};
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
	}

}
