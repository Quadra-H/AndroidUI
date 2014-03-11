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
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ssm.quadrah.diymarket.Constants;
import com.ssm.quadrah.diymarket.DesignerAccount;
import com.ssm.quadrah.diymarket.R;
import com.ssm.quadrah.diymarket.content.circle.CirclePageIndicator;
import com.ssm.quadrah.diymarket.content.tab.PageIndicator;
import com.ssm.quadrah.diymarket.designeditor.DesignEditorTool;
import com.ssm.quadrah.diymarket.profile.DesignerProfileName;

public class MarketRegister extends FragmentActivity {
	
	private static final int IMAGE_DPI_1280_800 = 0;
	private static final int IMAGE_DPI_1920_1200 = 1;
	private static final int IMAGE_DPI_1280_720 = 2;
	private static final int IMAGE_DPI_1920_1080 = 3;

	protected NewGridAdapter mAdapter;
	
    protected ViewPager mPager;
    protected PageIndicator mIndicator;
    public static PagerAdapter pm;
    
    private TextView txtWorkTitle;
    private TextView txtWorkCost;
    private TextView txtDesingerID;
    
    
    private ImageView representiveImageView;
    private String saveFilePathPNG;
    private String saveFilePathSPD;
    private String representiveTitle;
    private int dpi_width;
    private int dpi_height;
    
    
    private EditText editTitle;
  
    //private ArrayList<NewGridItems> newGridItems;
    private LinkedList<NewGridItems> newGridItems;
    public static List<NewGridFragment> gridFragments;
    
    private MenuItem mSpinnerItem = null;
    
    int type = 0;
    public static String strType = null;
    
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
	    
	    Intent intent = getIntent();
		type = intent.getIntExtra("type", type);
		
		ActionBar bar = getActionBar();
		bar.setHomeButtonEnabled(true);	    
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f34022")));	   
		
		int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		if (actionBarTitleId > 0) {
		    TextView title = (TextView) findViewById(actionBarTitleId);
		    if (title != null) {
		        title.setTextColor(Color.WHITE);
		    }
		}
		
		switch(type)
		{
		case Constants.TYPE_LAYOUT:
			bar.setTitle("LAYOUT");
			strType = "1_";
			break;
		case Constants.TYPE_BACKGROUND:
			bar.setTitle("BACKGROUND");
			strType = "2_";
			break;
		case Constants.TYPE_STICKER:
			bar.setTitle("STICKER");
			strType = "3_";
			break;
		case Constants.TYPE_FRAME:
			bar.setTitle("FRAME");
			strType = "4_";
			break;
		}
	    
	    txtWorkTitle = (TextView)findViewById(R.id.textViewTitle);
	    txtDesingerID = (TextView)findViewById(R.id.desingerID);
	    txtDesingerID.setText(((DesignerAccount)getApplication()).getAccount());
	    txtWorkTitle.setOnClickListener(OnClickTitle);
	    
	    representiveImageView = (ImageView)findViewById(R.id.imageViewProfile);
	    representiveImageView.setOnClickListener(OnClickImage);
        
        mPager = (ViewPager)findViewById(R.id.pager2);        
        mIndicator = (CirclePageIndicator)findViewById(R.id.pagerIndicator1);
        
        newGridItems = new LinkedList<NewGridItems>();
        
        NewGridItems basicItem = new NewGridItems(0, "registerBasic");    
        newGridItems.add(basicItem);
        newGridItems.get(0).strType = strType;
        
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
		if(item.getItemId() == R.id.action_register_edit)
		{
			Drawable temp = representiveImageView.getDrawable();
			Drawable temp1 = getResources().getDrawable(R.drawable.register_add_btn);

			Bitmap tmpBitmap = ((BitmapDrawable)temp).getBitmap();
			Bitmap tmpBitmap1 = ((BitmapDrawable)temp1).getBitmap();
			
			if(txtWorkTitle.toString().equals(""))
			{
				Toast.makeText(MarketRegister.this, "제목을 입력하세요.", Toast.LENGTH_LONG).show();
				return false;
			}
			else if(tmpBitmap.equals(tmpBitmap1) )
			{
				Toast.makeText(MarketRegister.this, "대표 이미지를 그려주세요.", Toast.LENGTH_LONG).show();
				return false;
			}
			else if(newGridItems.get(position).title == "registerBasic")
			{
				Toast.makeText(MarketRegister.this, "한 개 이상의 그림을 그려주세요.", Toast.LENGTH_LONG).show();
				return false;
			}
			else 
			{
				finish();
			}
		}
		else if(item.getItemId() == android.R.id.home)
		{
			finish();
		}
//		finish();
		return super.onOptionsItemSelected(item);
	}
	
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// TODO Auto-generated method stub
//
//		switch(item.getItemId()){
//		case R.id.action_register_edit :
//			final CharSequence[] items = {"Love","Winter","Animal"};
//            // arraylist to keep the selected items
//            final ArrayList seletedItems=new ArrayList();
//           
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("태그를 선택하세요.");
//            builder.setMultiChoiceItems(items, null,
//                    new DialogInterface.OnMultiChoiceClickListener() {
//           
//             @Override
//             public void onClick(DialogInterface dialog, int indexSelected,
//                     boolean isChecked) {
//                 if (isChecked) {
//                     seletedItems.add(indexSelected);
//                 } else if (seletedItems.contains(indexSelected)) {
//           
//                     seletedItems.remove(Integer.valueOf(indexSelected));
//                 }
//             }
//         })
//         .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//             @Override
//             public void onClick(DialogInterface dialog, int id) {
//            	 SparseBooleanArray CheCked = ((AlertDialog)dialog).getListView().getCheckedItemPositions();
//            	 if(CheCked.get(0) == true){
//            		 Toast.makeText(MarketRegister.this, "Love", Toast.LENGTH_SHORT).show();
//            	 }
//            	 if(CheCked.get(1) == true){
//            		 Toast.makeText(MarketRegister.this, "Winter", Toast.LENGTH_SHORT).show();
//            	 }
//            	 if(CheCked.get(2) == true){
//            		 Toast.makeText(MarketRegister.this, "Animal", Toast.LENGTH_SHORT).show();
//            	 }
//            	 finish();
//                
//             }
//         })
//         .setNegativeButton("취소", new DialogInterface.OnClickListener() {
//             @Override
//             public void onClick(DialogInterface dialog, int id) {
//                dialog.dismiss();
//             }
//         });
//   
//            dialog = builder.create();
//            dialog.show();
//			break;		
//
//		case android.R.id.home:
//			finish();
//		default:
//			break;
//
//		}
//		return super.onOptionsItemSelected(item);
//	}
	
	
	
	View.OnClickListener OnClickTitle = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			View dialog = View.inflate(getApplicationContext(), R.layout.edittool_dialog, null);  
            final AlertDialog ad = new AlertDialog.Builder(MarketRegister.this).setView(dialog).create();
            
            
            editTitle = (EditText) dialog.findViewById(R.id.input_path);
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
	
	DialogInterface mPopupDlg = null;

	int position = 0;;
	View.OnClickListener OnClickImage = new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			
			if(saveFilePathSPD != null)
			{		
				Intent i = new Intent(MarketRegister.this, DesignEditorTool.class);
				
				i.putExtra("title", representiveTitle);
        		i.putExtra("dpi_width", dpi_width);
        	    i.putExtra("dpi_height", dpi_height);
        	    i.putExtra("SPD", saveFilePathSPD);
        	    
        	    startActivityForResult(i, 30);
			}
			
			else
			{
				Intent i = new Intent(MarketRegister.this, DesignEditorTool.class);
				
				String dpi[] ={"1280*800","1920*1200","1280*720","1920*1080"};
		        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MarketRegister.this);
		        LayoutInflater inflater = getLayoutInflater();
		        View convertView = (View) inflater.inflate(R.layout.dialog_work_list, null);
		        alertDialog.setView(convertView);		        
		        ListView lv = (ListView) convertView.findViewById(R.id.listview_profile);
		        
		        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MarketRegister.this,android.R.layout.simple_list_item_1, dpi);
		        lv.setAdapter(adapter);
		        
		                
		        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener()
			    {
			      @Override
			      public void onClick(DialogInterface dialog, int which)
			      {
			        dialog.dismiss();
			      }
			    };
			    
			    lv.setOnItemClickListener(new OnItemClickListener(){
		        	@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
		        		
		        		Intent i = new Intent(MarketRegister.this, DesignEditorTool.class);	
		        		switch(position)
		        		{
		        		case IMAGE_DPI_1280_800:	
		        			dpi_width = 1280;
		        			dpi_height = 800;
		        			break;
		        		case IMAGE_DPI_1920_1200:
		        			dpi_width = 1920;
		        			dpi_height = 1200;   		
		        			break;
		        		case IMAGE_DPI_1280_720:
		        			dpi_width = 1280;
		        			dpi_height = 720;     				        			
		        			break;
		        		case IMAGE_DPI_1920_1080:
		        			dpi_width = 1920;
		        			dpi_height = 1080;	
		        			break;
		        		}
		        		
		        		
		        		
		        		i.putExtra("dpi_width", dpi_width);
		        	    i.putExtra("dpi_height", dpi_height);
		        	    
			        	
			        	saveFilePathPNG += strType.toString();
			        	
			        	startActivityForResult(i, 30);
		        		mPopupDlg.dismiss();
		        	}
		        });
		        
		        mPopupDlg = alertDialog.setTitle("해상도를 선택하세요.").setNegativeButton("취소", cancelListener).show();
				
				 
			}
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
				saveFilePathPNG = data.getStringExtra("PNG");
				saveFilePathSPD = data.getStringExtra("SPD");				
				representiveTitle = data.getStringExtra("title");
				dpi_width = data.getIntExtra("dpi_width", 0);
				dpi_height = data.getIntExtra("dpi_height", 0);
				
				
				
				File imgFile = new  File(saveFilePathPNG);
				Bitmap myBitmap = null;
				if(imgFile.exists()){

				    myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
				}
								
				representiveImageView.setImageBitmap(myBitmap);
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
