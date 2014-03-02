package com.ssm.quadrah.diymarket.profile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ssm.quadrah.diymarket.R;


public class DesignerProfileEdit extends Activity {

	protected static final int REQ_CODE_PICK_PICTURE = 3;	
	TextView textDesignerName;
	TextView textDesignerSatate;
	ImageView ProfilePicture;	
	TextView textCoverPicture;
	ImageView imageViewCoverPicture;
	
	 private static final int PICK_FROM_CAMERA = 0;
	 private static final int PICK_FROM_ALBUM = 1;
	 private static final int CROP_FROM_CAMERA = 2;
	 
	 private Uri mImageCaptureUri;
	 

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_profile_edit);
	    // TODO Auto-generated method stub
	    
	    ActionBar bar = getActionBar();
	    bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f34022")));
	    bar.setHomeButtonEnabled(true);
	    
	    ProfilePicture = (ImageView)findViewById(R.id.imageViewProfileEdit);
	    textDesignerName = (TextView)findViewById(R.id.EditDesignerName);
	    textDesignerSatate = (TextView)findViewById(R.id.EditDesignerState);
	    textCoverPicture = (TextView)findViewById(R.id.textCoverPicture);
	    
	    
	    
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
	}
	
	public void functionCoverPicture(View v){
		functionPicture(v);
		
//		final Intent intent = new Intent (Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//		this.startActivityForResult(intent, REQ_CODE_PICK_PICTURE);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch(item.getItemId()){
					
		case android.R.id.home:
			finish();
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
			break;
	
		default:
		}
		
	
		return super.onOptionsItemSelected(item);
	}
	
	private void doTakeAlbumAction()
	  {
		Intent intent = new Intent(Intent.ACTION_PICK);
	    intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
	    startActivityForResult(intent, PICK_FROM_ALBUM);
	  }

	 private void doTakePhotoAction()
	  {
		 Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		 
		 String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
		 mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));
		 
		 intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
		 startActivityForResult(intent, PICK_FROM_CAMERA);
	  }
	
	public void functionName(View v){
		switch(v.getId()){
		case R.id.layoutDevelopers:
			Intent i = new Intent(DesignerProfileEdit.this, DesignerProfileName.class);
			if(textDesignerName.getText().toString() != null)
			{
				i.putExtra("Name", textDesignerName.getText().toString());
			}
			
			startActivityForResult(i, 10);			
			
		}
	}
	
	public void functionState(View v){
		switch(v.getId()){
		case R.id.layoutDevelopersState:
			Intent i = new Intent(DesignerProfileEdit.this, DesignerProfileState.class);
			if(textDesignerSatate.getText().toString() != null)
			{
				i.putExtra("State", textDesignerSatate.getText().toString());
			}
			startActivityForResult(i, 20);
		}
	}
	
	
	
	public void functionPicture(View v){

			if(v.getId() == R.id.imageViewProfileEdit || v.getId() == R.id.layoutDesignerProfile)
			{
				DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener()
			    {
			      @Override
			      public void onClick(DialogInterface dialog, int which)
			      {
			        doTakePhotoAction();
			      }
			    };
			    
			    DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener()
			    {
			      @Override
			      public void onClick(DialogInterface dialog, int which)
			      {
			        doTakeAlbumAction();
			      }
			    };
			    
			    DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener()
			    {
			      @Override
			      public void onClick(DialogInterface dialog, int which)
			      {
			        dialog.dismiss();
			      }
			    };
			    
			    AlertDialog.Builder alert = new AlertDialog.Builder(DesignerProfileEdit.this);
			    
			    alert.setTitle("프로필 업로드할 이미지 선택") .setPositiveButton("사진촬영", cameraListener)
			      .setNeutralButton("앨범선택", albumListener)
			      .setNegativeButton("취소", cancelListener)
			      .show();
			}

		if(v.getId() == R.id.layoutCoverPicture || v.getId() == R.id.relativeCover){
			DialogInterface.OnClickListener cameraCoverListener = new DialogInterface.OnClickListener()
		    {
		      @Override
		      public void onClick(DialogInterface dialog, int which)
		      {
		        doTakePhotoAction();
		      }
		    };
		    
		    DialogInterface.OnClickListener albumCoverListener = new DialogInterface.OnClickListener()
		    {
		      @Override
		      public void onClick(DialogInterface dialog, int which)
		      {
		    	  Intent i = new Intent(Intent.ACTION_PICK);
			  		i.setAction(Intent.ACTION_GET_CONTENT);
			  		i.setType("image/*");
			  		startActivityForResult(i, REQ_CODE_PICK_PICTURE);
		      }
		    };
		    
		    DialogInterface.OnClickListener cancelCoverListener = new DialogInterface.OnClickListener()
		    {
		      @Override
		      public void onClick(DialogInterface dialog, int which)
		      {
		        dialog.dismiss();
		      }
		    };
		    
		    AlertDialog.Builder coverAlert = new AlertDialog.Builder(DesignerProfileEdit.this);
		    
		    coverAlert.setTitle("커버 업로드할 이미지 선택") .setPositiveButton("사진촬영", cameraCoverListener)
		      .setNeutralButton("앨범선택", albumCoverListener)
		      .setNegativeButton("취소", cancelCoverListener)
		      .show();

		
					
		}
			
		
	}
	

	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK)
		{
			switch(requestCode)
			{
			case CROP_FROM_CAMERA:
			{
				final Bundle extras = data.getExtras();

		        if(extras != null)
		        {
		          Bitmap photo = extras.getParcelable("data");
		          ProfilePicture.setImageBitmap(photo);
		        }

		        // 임시 파일 삭제
		        File f = new File(mImageCaptureUri.getPath());
		        if(f.exists())
		        {
		          f.delete();
		        }
		        break;
			}
			case PICK_FROM_ALBUM:
		      {
		    	  mImageCaptureUri = data.getData();
		      }
			case PICK_FROM_CAMERA:
		      {
		        // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
		        // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.

		        Intent intent = new Intent("com.android.camera.action.CROP");
		        intent.setDataAndType(mImageCaptureUri, "image/*");

		        intent.putExtra("outputX", 90);
		        intent.putExtra("outputY", 90);
		        intent.putExtra("aspectX", 1);
		        intent.putExtra("aspectY", 1);
		        intent.putExtra("scale", true);
		        intent.putExtra("return-data", true);
		        startActivityForResult(intent, CROP_FROM_CAMERA);

		        break;
		      }
		      
			case REQ_CODE_PICK_PICTURE:
			    
				Uri selPhotoUri = data.getData(); 
				
				Bitmap selPhoto = null;
				try { 
					
					selPhoto = Images.Media.getBitmap(getContentResolver(), selPhotoUri );
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block e.printStackTrace();
				}
				
				imageViewCoverPicture = (ImageView)findViewById(R.id.imageViewCover);
				BitmapDrawable ob = new BitmapDrawable(selPhoto);
				imageViewCoverPicture.setBackgroundDrawable(ob);
								//imageViewCoverPicture.setBackgroundResource(R.drawable.collage);
				break;
							
				
			}
			
			if(requestCode == 10)
			{
				textDesignerName.setText(data.getStringExtra("data_name"));				
			}
			else if(requestCode == 20)
			{
				textDesignerSatate.setText(data.getStringExtra("data_state"));				
			}
		}
	}
}
