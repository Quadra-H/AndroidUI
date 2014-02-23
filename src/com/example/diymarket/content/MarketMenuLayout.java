package com.example.diymarket.content;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.diymarket.R;
import com.example.diymarket.content.tab.IconPagerAdapter;
import com.example.diymarket.content.tab.TabPageIndicator;
import com.example.diymarket.register.ActivitySplitAnimationUtil;
import com.example.diymarket.register.MarketRegister;

public class MarketMenuLayout extends FragmentActivity {
	
	private static final String[] CONTENT = new String[] { "Best", "New", "My", "Like" };
	private static final int[] ICONS = new int[]{ 
		R.drawable.perm_group_best,
		R.drawable.perm_group_like,
		R.drawable.perm_group_my,
		R.drawable.perm_group_new,
	};
	
	private MenuItem mSpinnerItem = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_marketlayout);
	    // TODO Auto-generated method stub
	    
	    FragmentPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
	    ViewPager pager = (ViewPager)findViewById(R.id.pager);
	    pager.setAdapter(adapter);
	    
	    TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
	    indicator.setViewPager(pager);	    
	    
	    ActionBar bar = getActionBar();
	    bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f34022")));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		mSpinnerItem = menu.findItem( R.id.action_tag );
		setupSpinner( mSpinnerItem );
		
		 
		
		return true;
	}
	
	private void setupSpinner( MenuItem item )
	{
		//item.setVisible( getActionBar().getNavigationMode() == ActionBar.NAVIGATION_MODE_LIST );
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
	

	
	



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch(item.getItemId()){
		case R.id.action_register :
			ActivitySplitAnimationUtil.startActivity(MarketMenuLayout.this, new Intent(MarketMenuLayout.this, MarketRegister.class));
			break;		
		default:
			break;
		
		}
		return super.onOptionsItemSelected(item);
	}





	Fragment fragment;
	class SectionsPagerAdapter extends FragmentPagerAdapter implements IconPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			if(position == 0){
				fragment = new  BestSectionFragment();				
			}
			else if(position == 1){
				fragment = new  BestSectionFragment();			
			}
			else if(position == 2){
				fragment = new  BestSectionFragment();			
			}
			else{
				fragment = new  BestSectionFragment();			
			}
			return fragment;
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
	
	public static class BestSectionFragment extends Fragment{
		
		
		DetailItems detailItem_adapter = null;
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			
			View rootView = inflater.inflate(R.layout.fragment_best, container, false);
			
			ListView listView = (ListView)rootView.findViewById(R.id.listview);
			ArrayList<Items> listItems = new ArrayList<Items>();  
			
			
			if(detailItem_adapter == null){
			for(int i = 0; i < 25; i++){				
				Items item = new Items("111","222","Love");
				listItems.add(item);
			}
			detailItem_adapter = new DetailItems(inflater.getContext(), R.layout.listview_row, listItems);
			
			}
			else{
				detailItem_adapter.startProgressAnimation();	
			}
			
			
			
			
			
			listView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					
					Intent i = new Intent(getActivity(), ItemsPackage.class);
					startActivity(i);
					getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
				}
				
			});
			
			
	        listView.setAdapter(detailItem_adapter);
	        
			
			return rootView;
		}
	}
}
