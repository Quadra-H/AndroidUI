package com.ssm.quadrah.diymarket.profile;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.ssm.quadrah.diymarket.R;

public class DesignerProfileName extends Activity {

	EditText mNameInput;
	int MaxLength =20;
	TextView mTextView;
	String str;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_designer_name_edit);
	    // TODO Auto-generated method stub
	    mNameInput = (EditText)findViewById(R.id.editTextName);	  
	    mTextView = (TextView)findViewById(R.id.countText);
	    
	    
	    str = getIntent().getStringExtra("Name");
	    if(str != null){
	    	mNameInput.setText(str);
	    	mNameInput.setSelection(mNameInput.getText().length(), mNameInput.getText().length());
	    	mTextView.setText(mNameInput.getText().length() + "/20");
	    }
	    
	    mNameInput.addTextChangedListener(new TextWatcher() {
	    	
	    	public void onTextChanged(CharSequence s, int start, int before, int count) {
	    	if (s.length() > MaxLength) {	    		
	    		mNameInput.setText(str);
	    		mNameInput.setSelection(start);
	    	} else {
	    		mTextView.setText(String.valueOf(s.length())+ "/20");
	    	}
	    	}

	    	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	    		str = s.toString();
	    	}

	    	public void afterTextChanged(Editable s) {
	    	}
    	});
	    	

	    
	    InputFilter[] filterArray = new InputFilter[1];
	    filterArray[0] = new InputFilter.LengthFilter(MaxLength);
	    mNameInput.setFilters(filterArray);
	    
	    ActionBar bar = getActionBar();
	    bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f34022")));
	    bar.setHomeButtonEnabled(true);

	    	    
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
