package com.ssm.quadrah.diymarket.register;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ssm.quadrah.diymarket.R;

public class EditTool extends Activity {

	/** Called when the activity is first created. */
	ImageView image;
	Intent retIntent;
	int position;
	Bitmap bmp;
	Button btnRegister;
	
	EditText editTitle;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_edittool);
	    // TODO Auto-generated method stub
	    retIntent = getIntent();
	    
	    Bundle extras = getIntent().getExtras();
	    
	    if(extras != null){
	    	bmp = (Bitmap) extras.getParcelable("imagebitmap");
	    	position = retIntent.getIntExtra("position", position);
	    	Log.d("position", "" + position);
	    }
	    
	    
	    if(bmp != null){
		    image = (ImageView)findViewById(R.id.editSampleImage);
		    image.setImageBitmap(bmp);
	    }
	    
	    
	    btnRegister = (Button)findViewById(R.id.btnRegister);
	    btnRegister.setOnClickListener(OnClickRegister);
	    
	    
	}
	
	

	View.OnClickListener OnClickRegister = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub			
			View dialog = View.inflate(getApplicationContext(), R.layout.edittool_dialog, null);  
            final AlertDialog ad = new AlertDialog.Builder(EditTool.this).setView(dialog).create();  
            editTitle = (EditText) dialog.findViewById(R.id.Title);
            editTitle.setSingleLine();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editTitle, InputMethodManager.SHOW_FORCED);
           
            
            
            dialog.findViewById(R.id.completeButton).setOnClickListener(new OnClickListener() {  
                  
                @Override  
                public void onClick(View v) {  
                    //폴더 이름검증해야함  
                      
                    //하상이 Format Image 주면 될 듯.
                	Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                	
        			retIntent.putExtra("img", bitmap);
        			retIntent.putExtra("position", position);
        			retIntent.putExtra("title", editTitle.getText().toString());
        		
        			setResult(Activity.RESULT_OK, retIntent);
        			finish();
                    ad.dismiss();  
                      
                      
                }  
            });  
            
            ad.show();  
			
		}
	};

}

