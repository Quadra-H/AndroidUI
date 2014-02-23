package com.example.diymarket.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.diymarket.R;

public class DesignerProfileState extends Activity {

	EditText mStateInput;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_designer_state_edit);
	    // TODO Auto-generated method stub
	    
	    mStateInput  = (EditText)findViewById(R.id.editTextState);
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
			Intent intent = getIntent();
		    intent.putExtra("data_state", mStateInput.getText().toString());
		    setResult(RESULT_OK, intent);
		    finish();
			
			break;		
		default:
			break;
		
		}
		return super.onOptionsItemSelected(item);
	}
}
