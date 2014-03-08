package com.ssm.quadrah.diymarket.register;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
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

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ssm.quadrah.diymarket.DesignerAccount;
import com.ssm.quadrah.diymarket.R;
import com.ssm.quadrah.diymarket.content.circle.CirclePageIndicator;
import com.ssm.quadrah.diymarket.content.tab.PageIndicator;
import com.ssm.quadrah.diymarket.profile.DesignerProfileName;

public class MarketRegister extends FragmentActivity {

	protected NewGridAdapter mAdapter;
	
    protected ViewPager mPager;
    protected PageIndicator mIndicator;
    public static PagerAdapter pm;
    
    private TextView txtWorkTitle;
    private TextView txtWorkCost;
    private TextView txtDesingerID;
    private ImageView imageView;
    private Button btnRegister;
  
    //private ArrayList<NewGridItems> newGridItems;
    private LinkedList<NewGridItems> newGridItems;
    public static List<NewGridFragment> gridFragments;
    
    private MenuItem mSpinnerItem = null;
    
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
	    
	    ActionBar bar = getActionBar();
	    bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f34022")));
	    
	    txtWorkTitle = (TextView)findViewById(R.id.textViewTitle);
	    txtDesingerID = (TextView)findViewById(R.id.desingerID);
	    txtDesingerID.setText(((DesignerAccount)getApplication()).getAccount());
	    txtWorkTitle.setOnClickListener(OnClickTitle);
	    
        imageView = (ImageView)findViewById(R.id.imageViewProfile);
        imageView.setOnClickListener(OnClickImage);
        
        mPager = (ViewPager)findViewById(R.id.pager2);        
        mIndicator = (CirclePageIndicator)findViewById(R.id.pagerIndicator1);
        
        newGridItems = new LinkedList<NewGridItems>();
        
        NewGridItems basicItem = new NewGridItems(0, "registerBasic");    
        newGridItems.add(basicItem);
        
        gridFragments = new ArrayList<NewGridFragment>();        
        gridFragments.add(new NewGridFragment(newGridItems, MarketRegister.this, gridFragments));
        
        pm = new PagerAdapter(getSupportFragmentManager(), gridFragments);
        
        
        
        mPager.setAdapter(pm);
        mIndicator.setViewPager(mPager);
       
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}
	AlertDialog dialog; 
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch(item.getItemId()){
		case R.id.action_register_edit :
			final CharSequence[] items = {"Love","Winter","Animal"};
            // arraylist to keep the selected items
            final ArrayList seletedItems=new ArrayList();
           
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("태그를 선택하세요.");
            builder.setMultiChoiceItems(items, null,
                    new DialogInterface.OnMultiChoiceClickListener() {
           
             @Override
             public void onClick(DialogInterface dialog, int indexSelected,
                     boolean isChecked) {
                 if (isChecked) {
                     seletedItems.add(indexSelected);
                 } else if (seletedItems.contains(indexSelected)) {
           
                     seletedItems.remove(Integer.valueOf(indexSelected));
                 }
             }
         })
         .setPositiveButton("확인", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int id) {
            	 SparseBooleanArray CheCked = ((AlertDialog)dialog).getListView().getCheckedItemPositions();
            	 if(CheCked.get(0) == true){
            		 Toast.makeText(MarketRegister.this, "Love", Toast.LENGTH_SHORT).show();
            	 }
            	 if(CheCked.get(1) == true){
            		 Toast.makeText(MarketRegister.this, "Winter", Toast.LENGTH_SHORT).show();
            	 }
            	 if(CheCked.get(2) == true){
            		 Toast.makeText(MarketRegister.this, "Animal", Toast.LENGTH_SHORT).show();
            	 }
            	 finish();
                
             }
         })
         .setNegativeButton("취소", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
             }
         });
   
            dialog = builder.create();
            dialog.show();
			break;		

		case android.R.id.home:
			finish();
		default:
			break;

		}
		return super.onOptionsItemSelected(item);
	}
	
	EditText editTitle;
	
	View.OnClickListener OnClickTitle = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			View dialog = View.inflate(getApplicationContext(), R.layout.edittool_dialog, null);  
            final AlertDialog ad = new AlertDialog.Builder(MarketRegister.this).setView(dialog).create();  
            editTitle = (EditText) dialog.findViewById(R.id.Title);
            editTitle.setSingleLine();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(txtWorkTitle, InputMethodManager.SHOW_FORCED);
           
            
            
            dialog.findViewById(R.id.completeButton).setOnClickListener(new OnClickListener() {  
                  
                @Override  
                public void onClick(View v) {  
                	txtWorkTitle.setText(editTitle.getText().toString());        			
                    ad.dismiss();  
                      
                      
                }  
            });  
            
            ad.show();  
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
				HttpGet httpGet = new HttpGet("http://10.0.2.2:8888/media/mediaList");				
				HttpResponse response = httpclient.execute(httpGet);								


				responseStr = EntityUtils.toString(response.getEntity());
				Log.i("", "responseStr 1: " + responseStr);
				
				JSONObject resultJson = new JSONObject(responseStr);

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
				
				
				/**/
				
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
			// TODO Auto-generated method stub
		
			
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
				txtWorkTitle.setText(data.getStringExtra("data_title"));				
			}
			else if(requestCode == 20)
			{
				txtWorkCost.setText(data.getStringExtra("data_cost"));				
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
