package com.example.diymarket.content;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.diymarket.R;

public class ImageActivity extends Activity {

	/** Called when the activity is first created. */
	Intent receivedIntent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_image);
	    
	    ImageView imageView = (ImageView)findViewById(R.id.imageView);
       

        //imageView.setImageResource(imageID);

        receivedIntent = getIntent();	
        setImage(imageView);

	    // TODO Auto-generated method stub
	}
	
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
