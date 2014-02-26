package com.ssm.quadrah.diymarket;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);



		//Account - name: dhl5091@gmail.com, type :com.google
		//Account - name: dhl5091@gmail.com, type :com.evernote
		//Account - name: 이동희, type :com.sec.android.app.sns3.facebook

		AccountManager manager = AccountManager.get(this);
		Account[] accounts =  manager.getAccounts();
		final int count = accounts.length;
		Account account = null;

		for(int i=0;i<count;i++) {
			account = accounts[i];
			Log.d("HSGIL", "Account - name: " + account.name + ", type :" + account.type);

			if(account.type.equals("com.google")){		//이러면 구글 계정 구분 가능

			} 
		}   



		Button btnMarketMenu = (Button)findViewById(R.id.btnMarketMenu);
		btnMarketMenu.setOnClickListener(OnClickBtnMarketMenu);


		Button btnTest1 = (Button)findViewById(R.id.btnTest1);
		btnTest1.setOnClickListener(OnClickBtnTest1);

		Button btnTest2 = (Button)findViewById(R.id.btnTest2);
		btnTest2.setOnClickListener(OnClickBtnTest2);
	}

	View.OnClickListener OnClickBtnMarketMenu = new View.OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent i = new Intent(MainActivity.this, MarketMenu.class);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);;
		}
	};
	View.OnClickListener OnClickBtnTest1 = new View.OnClickListener() {

		@Override
		public void onClick(View arg0) {			


			new GetUploadURLTask().execute();

		}
	};
	View.OnClickListener OnClickBtnTest2 = new View.OnClickListener() {

		@Override
		public void onClick(View arg0) {

			new GetMediaListTask().execute();

		}
	};

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
				HttpGet httpGet = new HttpGet("http://10.0.2.2:8888/media/uploadUrl");				
				HttpResponse response = httpclient.execute(httpGet);								


				responseStr = EntityUtils.toString(response.getEntity());
				Log.i("", "responseStr 1: " + responseStr);

				JSONObject resultJson = new JSONObject(responseStr);

				String uploadURL = resultJson.getString("uploadURL");

				httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(uploadURL.replaceAll("localhost","10.0.2.2"));				




				Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),
						R.drawable.add_btn);				

				Log.i("", "File name : " + getFilesDir()+"test4.png");				
				File file = new File(getFilesDir()+"test4.png");
				OutputStream out = null;

				try
				{
					file.createNewFile();
					out = new FileOutputStream(file);

					bitmap.compress(CompressFormat.PNG, 100, out);
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
	private class GetMediaListTask extends AsyncTask<String, String, String> {

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

			}catch(Exception e){

				Log.i("", "Exception e: " + e.getMessage());
				e.printStackTrace();				
			}

			super.onPostExecute(result);			
		}

	}

	private class GetUploadURLTask2 extends AsyncTask<String, String, String> {

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



}
