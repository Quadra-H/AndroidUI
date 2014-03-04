package com.ssm.quadrah.diymarket.content;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ssm.quadrah.diymarket.Constants;
import com.ssm.quadrah.diymarket.R;
import com.ssm.quadrah.diymarket.content.tab.IconPagerAdapter;
import com.ssm.quadrah.diymarket.content.tab.TabPageIndicator;
import com.ssm.quadrah.diymarket.entity.workpkginfoendpoint.Workpkginfoendpoint;
import com.ssm.quadrah.diymarket.entity.workpkginfoendpoint.model.CollectionResponseWorkPkgInfo;
import com.ssm.quadrah.diymarket.entity.workpkginfoendpoint.model.WorkPkgInfo;
import com.ssm.quadrah.diymarket.register.ActivitySplitAnimationUtil;
import com.ssm.quadrah.diymarket.register.MarketRegister;
//import com.ssm.quadrah.diymarket.content.tab.TabPageIndicator;

public class MarketMenuLayout extends FragmentActivity {

	private static final String[] CONTENT = new String[] { "Best", "New", "My", "Like" };
	private static final int[] ICONS = new int[]{ 
		R.drawable.perm_group_best,
		R.drawable.perm_group_like,
		R.drawable.perm_group_my,
		R.drawable.perm_group_new,
	};

	private MenuItem mSpinnerItem = null;
	private int type = 0;
	
	private SectionsPagerAdapter pagerAdapter;
	private Workpkginfoendpoint service;
	private Workpkginfoendpoint.Builder builder = new Workpkginfoendpoint.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_marketlayout);
		// TODO Auto-generated method stub

		Intent intent = getIntent();
		type = intent.getExtras().getInt(Constants.TYPE_KEY);
		
		
		
		ArrayList<Items> itemList = new ArrayList<Items>();
		
		pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),itemList);
		
	    ViewPager pager = (ViewPager)findViewById(R.id.pager);
	    pager.setAdapter(pagerAdapter);
	    
	    TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
	    indicator.setViewPager(pager);		
	    
	    
	    ActionBar bar = getActionBar();
		bar.setHomeButtonEnabled(true);	    
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f34022")));	    
	    
		
		////// get PkgInfoList 
//	    service = builder.build();
//		new WorkPkgInfoListAsyncTask(this).execute();
		////////////////////////////////////////////////
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		mSpinnerItem = menu.findItem( R.id.action_tag );
		setupSpinner( mSpinnerItem );



		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch(item.getItemId()){
		case R.id.action_register :
			ActivitySplitAnimationUtil.startActivity(MarketMenuLayout.this, new Intent(MarketMenuLayout.this, MarketRegister.class));
			break;		

		case android.R.id.home:
			finish();
		default:
			break;

		}
		return super.onOptionsItemSelected(item);
	}



	private class WorkPkgInfoListAsyncTask extends AsyncTask<Void, Void, CollectionResponseWorkPkgInfo>{
		Context context;
		private ProgressDialog pd;

		public WorkPkgInfoListAsyncTask(Context context) {
			this.context = context;
		}

		protected void onPreExecute(){ 
			super.onPreExecute();
			pd = new ProgressDialog(context);
			pd.setMessage("Retrieving WorkPkgInfo...");
			pd.show();    
		}

		protected CollectionResponseWorkPkgInfo doInBackground(Void... unused) {
			CollectionResponseWorkPkgInfo pkgInfo = null;
			try {
					
				pkgInfo = service.listWorkPkgInfo().execute();
			} catch (Exception e) {
				Log.d("Could not retrieve WorkPkg", e.getMessage(), e);
			}
			return pkgInfo;
		}

		protected void onPostExecute(CollectionResponseWorkPkgInfo pkgInfoArr) {
			pd.dismiss();
			// Do something with the result.			
			List<WorkPkgInfo> _list = pkgInfoArr.getItems();
			ArrayList<Items> itemList = new ArrayList<Items>();
			
			Log.d(MarketMenuLayout.class.getSimpleName(),"pkgInfoArr size : "+_list.size());
			
			for (WorkPkgInfo pkgInfo : _list) {				
				
				Log.d(MarketMenuLayout.class.getSimpleName(),"pkgInfo title : "+pkgInfo.getWorkPkgTitle());
				
				Items item = new Items(pkgInfo.getWorkPkgTitle(),pkgInfo.getDesignerId()+"",pkgInfo.getWorkPkgPrice()+"");
				
				itemList.add(item);
			}
			
			for(int i = 0; i < 25; i++){				
				Items item = new Items("Image title "+i,"DesignerID","Free");
				itemList.add(item);
			}
			pagerAdapter.getFragmentList().get(0).getListView().invalidate();
			pagerAdapter.setItemList(itemList);
			
			Log.d(MarketMenuLayout.class.getSimpleName(),"List upadte!!! ");
			
		}
	}

	private void setupSpinner( MenuItem item )
	{		
		View view = item.getActionView();
		if (view instanceof Spinner)
		{
			Spinner spinner = (Spinner) view;
			spinner.setAdapter( ArrayAdapter.createFromResource( this,
					R.array.spinner_data,
					android.R.layout.simple_spinner_dropdown_item ) );

			spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					if(arg2 == 1){
						Toast.makeText(MarketMenuLayout.this, "Love",Toast.LENGTH_SHORT).show();
						Log.d("Love", "Love");
					}else if(arg2 == 2){
						Toast.makeText(MarketMenuLayout.this, "Winter",Toast.LENGTH_SHORT).show();
						Log.d("Winter", "Winter");
					}else if(arg2 == 3){
						Toast.makeText(MarketMenuLayout.this, "Animal",Toast.LENGTH_SHORT).show();
						Log.d("Animal", "Animal");
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}

			});
		}
	}







	





	
	class SectionsPagerAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
		private ArrayList<Items> itemList;
		private ArrayList<BestSectionFragment> fragmentList = new ArrayList<BestSectionFragment>();
		
		
		
		
		public ArrayList<BestSectionFragment> getFragmentList() {
			return fragmentList;
		}
		public void setFragmentList(ArrayList<BestSectionFragment> fragmentList) {
			this.fragmentList = fragmentList;
		}
		public SectionsPagerAdapter(FragmentManager fm,ArrayList<Items> itemList) {
			super(fm);
			// TODO Auto-generated constructor stub
			
			
			for(int i=0;i<CONTENT.length;i++){
				fragmentList.add( new  BestSectionFragment(itemList));
			}
			this.itemList= itemList;
		}
		public void setItemList(ArrayList<Items> itemList){
			this.itemList= itemList;
			DetailItems adapter = fragmentList.get(0).getDetailItemAdapter();
			adapter.setListItems(itemList);
			adapter.notifyDataSetChanged();
		}

		
		
		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub			
			return fragmentList.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return CONTENT.length;
		}

		@Override
		public int getIconResId(int index) {
			// TODO Auto-generated method stub
			return ICONS[index];
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return CONTENT[position % CONTENT.length].toUpperCase();
		}
	}
	
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
//			mListItems.addFirst("Added after refresh...");
//			mAdapter.notifyDataSetChanged();
//
//			// Call onRefreshComplete when the list has been refreshed.
//			mPullRefreshListView.onRefreshComplete();

			super.onPostExecute(result);
		}
	}

	@SuppressLint("ValidFragment")
	public class BestSectionFragment extends Fragment{
		
		
		DetailItems detailItemAdapter = null;
		
		ListView listView;
		
		ArrayList<Items> itemList;
		
		private PullToRefreshListView mPullRefreshListView;
		
		public ListView getListView() {
			return listView;
		}
		public void setListView(ListView listView) {
			this.listView = listView;
		}


		public BestSectionFragment(ArrayList<Items> list){
			this.itemList = list;
		}



		public DetailItems getDetailItemAdapter() {
			return detailItemAdapter;
		}



		public void setDetailItemAdapter(DetailItems detailItemAdapter) {
			this.detailItemAdapter = detailItemAdapter;
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			
			View rootView = inflater.inflate(R.layout.fragment_best, container, false);
			
		
			 
			mPullRefreshListView = (PullToRefreshListView) rootView.findViewById(R.id.listview);
			mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
				@Override
				public void onRefresh(PullToRefreshBase<ListView> refreshView) {
					String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
							DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

					// Update the LastUpdatedLabel
					refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

					// Do work to refresh the list here.
					new GetDataTask().execute();
				}
			});
			
			//DemoAdapter adapter=(DemoAdapter)getLastNonConfigurationInstance();
			
			// Add an end-of-list listener
			mPullRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

				@Override
				public void onLastItemVisible() {
					//Toast.makeText(PullToRefreshListActivity.this, "End of List!", Toast.LENGTH_SHORT).show();
				}
			});
			listView = mPullRefreshListView.getRefreshableView();
			
			registerForContextMenu(listView);
			
			itemList = new ArrayList<Items>();
			
			
			
			if(detailItemAdapter == null){
			for(int i = 0; i < 25; i++){				
				Items item = new Items("Image title 01","DesignerID","Free");
				itemList.add(item);
			}
			//detailItem_adapter = new DetailItems(inflater.getContext(), R.layout.listview_row, listItems);
			detailItemAdapter = new DetailItems(inflater.getContext(),  itemList);
			
			}
			else{
				detailItemAdapter.startProgressAnimation();	
			}
			
			
			
			
			
			mPullRefreshListView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					
					Intent i = new Intent(getActivity(), ItemsPackage.class);
					startActivity(i);
					getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
				}
				
			});			
			mPullRefreshListView.setAdapter(detailItemAdapter);
	        
	      
		    
		    
			
			return rootView;
		}
	}
}

