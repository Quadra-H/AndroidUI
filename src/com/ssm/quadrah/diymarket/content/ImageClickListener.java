package com.ssm.quadrah.diymarket.content;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class ImageClickListener implements OnClickListener{

	int imageID;
	
	Context context;
	
	public ImageClickListener(Context context, int imageID)
	{
		this.context = context;
		this.imageID = imageID;		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i = new Intent(context, ImageActivity.class);
		i.putExtra("image ID", imageID);
		context.startActivity(i);
	}
	
}
