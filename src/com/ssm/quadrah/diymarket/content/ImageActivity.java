package com.ssm.quadrah.diymarket.content;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.ssm.quadrah.diymarket.R;

public class ImageActivity extends Activity {

	/** Called when the activity is first created. */
	Intent receivedIntent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_image);
	    //requestWindowFeature(Window.FEATURE_NO_TITLE);
	    
	    ImageView imageView = (ImageView)findViewById(R.id.imageView);
       

	    Button btnX = (Button)findViewById(R.id.btnX);
	    btnX.setOnClickListener(OnClickX);
        //imageView.setImageResource(imageID);

        receivedIntent = getIntent();	
        setImage(imageView);

	    // TODO Auto-generated method stub
	}
	
	View.OnClickListener OnClickX = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
	};
	
	private void setImage(ImageView imageView) {
        //----------------------------------------------------------------
        // 초기 액티비티의 GridView 뷰의 이미지 항목을 클릭할 때 생성된 인텐트는
        // 이 액티비티는 getIntent 메소드를 호출하여 접근할 수 있습니다.
        
        if(receivedIntent != null){
        //----------------------------------------------------------------
        // 확대되는 이미지의 리소스 ID를 인텐트로부터 읽어들이고,
        // 그것을 ImageView 뷰의 이미지 리소스로 설정합니다.
        
        int imageID = (Integer)receivedIntent.getExtras().get("image ID");
        imageView.setImageResource(imageID);
        }
    }




}

