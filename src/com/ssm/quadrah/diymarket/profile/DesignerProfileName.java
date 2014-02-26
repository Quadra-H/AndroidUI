package com.ssm.quadrah.diymarket.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.ssm.quadrah.diymarket.R;

public class DesignerProfileName extends Activity {

	EditText mNameInput;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_designer_name_edit);
	    // TODO Auto-generated method stub
	    mNameInput = (EditText)findViewById(R.id.editTextName);
	    	    
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
		    intent.putExtra("data_name", mNameInput.getText().toString());
		    setResult(RESULT_OK, intent);
		    finish();
			
			break;		
		default:
			break;
		
		}
		return super.onOptionsItemSelected(item);
	}

}
