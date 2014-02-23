package com.example.diymarket.register;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.diymarket.R;

public class EditTool extends Activity {

	/** Called when the activity is first created. */
	ImageView image;
	Intent retIntent;
	int position;
	Bitmap bmp;
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
	    }
	    
	    
	    if(bmp != null){

		    image = (ImageView)findViewById(R.id.editSampleImage);
		    image.setImageBitmap(bmp);
		    
	    }
	    
	    
	    
	    Button btnRegister = (Button)findViewById(R.id.btnRegister);
	    btnRegister.setOnClickListener(OnClickRegister);
	}

	View.OnClickListener OnClickRegister = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub			
			
			
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
			
			retIntent.putExtra("img", bitmap);
			retIntent.putExtra("position", position);
		
			setResult(Activity.RESULT_OK, retIntent);
			finish();
		}
	};

}

