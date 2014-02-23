package com.example.diymarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btnMarketMenu = (Button)findViewById(R.id.btnMarketMenu);
		btnMarketMenu.setOnClickListener(OnClickBtnMarketMenu);
	}
	
	View.OnClickListener OnClickBtnMarketMenu = new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent i = new Intent(MainActivity.this, MarketMenu.class);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);;
		}
	};
}
