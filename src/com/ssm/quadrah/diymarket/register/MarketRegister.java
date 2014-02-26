package com.ssm.quadrah.diymarket.register;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ssm.quadrah.diymarket.R;
import com.ssm.quadrah.diymarket.content.circle.CirclePageIndicator;
import com.ssm.quadrah.diymarket.content.tab.PageIndicator;
import com.ssm.quadrah.diymarket.profile.DesignerProfileName;

public class MarketRegister extends FragmentActivity {




	protected NewGridAdapter mAdapter;

	protected ViewPager mPager;
	protected PageIndicator mIndicator;
	public static PagerAdapter pm;

	private TextView editWorkTitle;
	private TextView editWorkCost;
	private ImageView imageView;


	static ArrayList<NewGridItems> itmLst;
	public static List<NewGridFragment> gridFragments;

	ArrayList<String> a;
	Iterator<String> it;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivitySplitAnimationUtil.prepareAnimation(this);
		setContentView(R.layout.activity_register);
		ActivitySplitAnimationUtil.animate(this, 1000);
		// TODO Auto-generated method stub


		editWorkTitle = (TextView)findViewById(R.id.EditDesignerName);
		editWorkCost = (TextView)findViewById(R.id.EditDesignerName);

		Button BtnRegister = (Button)findViewById(R.id.btnRegister);
		BtnRegister.setOnClickListener(OnClickBtnRegister);

		imageView = (ImageView)findViewById(R.id.imageViewProfile);
		imageView.setOnClickListener(OnClickImage);

		mPager = (ViewPager)findViewById(R.id.pager2);        
		mIndicator = (CirclePageIndicator)findViewById(R.id.pagerIndicator1);

		itmLst = new ArrayList<NewGridItems>();

		NewGridItems itm1 = new NewGridItems(0, "registerBasic");


		gridFragments = new ArrayList<NewGridFragment>();        
		gridFragments.add(new NewGridFragment(itm1, MarketRegister.this, gridFragments, pm));

		pm = new PagerAdapter(getSupportFragmentManager(), gridFragments);
		mPager.setAdapter(pm);
		mIndicator.setViewPager(mPager);


	}	


	/**
	 * AsyncTask for calling Mobile Assistant API for checking into a 
	 * place (e.g., a store).
	 */
	private class GetUploadURLTask extends AsyncTask<String, String, String> {

		/**
		 * Calls appropriate CloudEndpoint to indicate that user checked into a place.
		 *
		 * @param params the place where the user is checking in.
		 */
		@Override
		protected String doInBackground(String... params) {

			String responseStr = null;
			
			try {

				HttpClient httpclient = new DefaultHttpClient();
				HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); //Timeout Limit
				HttpGet httpGet = new HttpGet("http://10.0.2.2:8888/media/mediaList");				
				HttpResponse response = httpclient.execute(httpGet);								


				responseStr = EntityUtils.toString(response.getEntity());
				Log.i("", "responseStr 1: " + responseStr);
				
				/*JSONObject resultJson = new JSONObject(responseStr);

				String uploadURL = resultJson.getString("uploadURL");
				
				httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(uploadURL);				
				
				
				Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                        R.drawable.add_btn);				
				
				Log.i("", "File name : " + getFilesDir()+"test4.png");				
				File file = new File(getFilesDir()+"test4.png");
		        OutputStream out = null;
		 
		        try
		        {
		        	file.createNewFile();
		            out = new FileOutputStream(file);
		 
		            bitmap.compress(CompressFormat.JPEG, 100, out);
		        }
		        catch (Exception e) 
		        {
		            e.printStackTrace();
		        }
		        finally
		        {
		            try
		            {
		                out.close();
		            }
		            catch (IOException e)
		            {
		                e.printStackTrace();
		            }
		        }
				
				

				FileBody fileBody  = new FileBody(file);
				MultipartEntity reqEntity = new MultipartEntity();

				reqEntity.addPart("file", fileBody);

				httppost.setEntity(reqEntity);
				response = httpclient.execute(httppost);
				responseStr = EntityUtils.toString(response.getEntity());
				
				Log.i("", "responseStr 2: " + responseStr );
				
				
//				resultJson = new JSONObject(responseStr);
//
//				String blobKey = resultJson.getString("title");
//				String servingUrl = resultJson.getString("description");
//
//				Log.i("", "blobKey: " + blobKey + " servingUrl: "+servingUrl );
				
				
				*/
				
				/*
				httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(responseStr);

				//Drawable drawable= getResources().getDrawable(R.drawable.ratingstars);
				//drawable.

				String path = Environment.getExternalStorageDirectory().toString();
				//File file = new File(path, "normal_star.png");
				
				
				Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                        R.drawable.add_btn);
				//Uri saveFile = FileUtil.getTemporaryFileName();
				
				
				Log.i("", "File name : " + getFilesDir()+"test2.png");				
				File file = new File(getFilesDir()+"test2.png");
		        OutputStream out = null;
		 
		        try
		        {
		        	file.createNewFile();
		            out = new FileOutputStream(file);
		 
		            bitmap.compress(CompressFormat.JPEG, 100, out);
		        }
		        catch (Exception e) 
		        {
		            e.printStackTrace();
		        }
		        finally
		        {
		            try
		            {
		                out.close();
		            }
		            catch (IOException e)
		            {
		                e.printStackTrace();
		            }
		        }
				
				

				FileBody fileBody  = new FileBody(file);
				MultipartEntity reqEntity = new MultipartEntity();

				reqEntity.addPart("file", fileBody);

				httppost.setEntity(reqEntity);
				response = httpclient.execute(httppost);
				responseStr = EntityUtils.toString(response.getEntity());
				
				Log.i("", "responseStr 2: " + responseStr );
				
				
				JSONObject resultJson = new JSONObject(responseStr);

				String blobKey = resultJson.getString("blobKey");
				String servingUrl = resultJson.getString("servingUrl");

				Log.i("", "blobKey: " + blobKey + " servingUrl: "+servingUrl );
				
				
				*/
				
				

			} catch (IOException e) {				
				Log.i("", "IOException e: " + e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {				
				Log.i("", "Exception e: " + e.getMessage());
				e.printStackTrace();
			}

			return responseStr;
		}

		@Override
		protected void onPostExecute(String result) {
			Log.i("", "GetUploadURLTask: onPostExecute : " + result);
			
			String responseStr=null;



			try{
				
				
				/*
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

				nameValuePairs.add(new BasicNameValuePair("userId", userId));
				nameValuePairs.add(new BasicNameValuePair("blobKey",blobKey));
				nameValuePairs.add(new BasicNameValuePair("servingUrl",servingUrl));

				HttpClient httpclient = new DefaultHttpClient();
				HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000);

				HttpPost httppost = new HttpPost(url);
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);
				*/


			}catch(Exception e){

				Log.i("", "Exception e: " + e.getMessage());
				e.printStackTrace();				
			}



			super.onPostExecute(result);			

		}


	}



	View.OnClickListener OnClickBtnRegister = new View.OnClickListener() {

		@Override
		public void onClick(View arg0) {


			new GetUploadURLTask().execute();



			//			BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
			//			UploadOptions uploadOptions = UploadOptions.Builder.withGoogleStorageBucketName("dcimg13");
			//			String url = blobstoreService.createUploadUrl("/uploaded", uploadOptions);
			//			
			//			Log.i("", "blob_url: " + url);
			// TODO Auto-generated method stub
			//ArrayList<ImageView> list = gridFragments.get(0).getImageList();
			//ArrayList<ImageView> list1 = gridFragments.get(1).getImageList()


			finish();
		}
	};

	public void functionTitle(View v){
		switch(v.getId()){
		case R.id.textViewTitle:
			Intent i = new Intent(MarketRegister.this, DesignerProfileName.class);
			startActivityForResult(i, 10);
		}
	}
	BitmapDrawable bitmapDrawable;

	int position = 0;;
	View.OnClickListener OnClickImage = new View.OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub

			Intent i = new Intent(MarketRegister.this, EditTool.class);
			startActivityForResult(i, 30);
		}
	};



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if(resultCode == RESULT_OK)
		{
			if(requestCode == 10)
			{				
				editWorkTitle.setText(data.getStringExtra("data_title"));				
			}
			else if(requestCode == 20)
			{
				editWorkCost.setText(data.getStringExtra("data_cost"));				
			}
			else if(requestCode == 30)
			{				
				Bitmap thumbnail = (Bitmap) data.getExtras().get("img");				
				imageView.setImageBitmap(thumbnail);
			}

		}
	}

	public class PagerAdapter extends FragmentStatePagerAdapter {
		private List<NewGridFragment> fragments;

		@Override
		public void notifyDataSetChanged() {
			// TODO Auto-generated method stub
			super.notifyDataSetChanged();
		}

		public PagerAdapter(FragmentManager fm, List<NewGridFragment> fragments) {
			super(fm);
			this.fragments = fragments;
		}

		@Override
		public Fragment getItem(int position) {
			return this.fragments.get(position);
		}

		@Override
		public int getCount() {
			return this.fragments.size();
		}
	}

	@Override
	protected void onStop() {
		// If we're currently running the entrance animation - cancel it
		ActivitySplitAnimationUtil.cancel();

		super.onStop();    //To change body of overridden methods use File | Settings | File Templates.
	}
}
