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
        // �ʱ� ��Ƽ��Ƽ�� GridView ���� �̹��� �׸��� Ŭ���� �� ������ ����Ʈ��
        // �� ��Ƽ��Ƽ�� getIntent �޼ҵ带 ȣ���Ͽ� ������ �� �ֽ��ϴ�.
        
        if(receivedIntent != null){
        //----------------------------------------------------------------
        // Ȯ��Ǵ� �̹����� ���ҽ� ID�� ����Ʈ�κ��� �о���̰�,
        // �װ��� ImageView ���� �̹��� ���ҽ��� �����մϴ�.
        
        int imageID = (Integer)receivedIntent.getExtras().get("image ID");
        imageView.setImageResource(imageID);
        }
    }




}

