package com.ssm.quadrah.diymarket.content;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ssm.quadrah.diymarket.DesignerAccount;
import com.ssm.quadrah.diymarket.R;
import com.ssm.quadrah.diymarket.content.circle.BaseSampleActivity;
import com.ssm.quadrah.diymarket.content.circle.CirclePageIndicator;
import com.ssm.quadrah.diymarket.content.circle.TestFragmentAdapter;
import com.ssm.quadrah.diymarket.content.tab.PageIndicator;
import com.ssm.quadrah.diymarket.profile.DesignerProfile;

public class ItemsPackage extends BaseSampleActivity {

	public PageIndicator mIndicator;
	private ViewPager awesomePager;
	static public PagerAdapter pm;
	
	private TextView txtDesignerID;

	 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.menu, menu);
	        return true;
    }
	 
	 View.OnClickListener OnClickExit = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
			overridePendingTransition(R.anim.stay, R.anim.slide_out_down);	
			
			}
	};
	 
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_itempackage);
	    // TODO Auto-generated method stub
	    
	    Button btnExit = (Button)findViewById(R.id.btnExit);
	    Button btnLike = (Button)findViewById(R.id.btnLike);
	    Button btnBuy = (Button)findViewById(R.id.btnBuy);
	    txtDesignerID = (TextView)findViewById(R.id.txtDesignerID);
	    txtDesignerID.setTextColor(Color.RED);
	    SpannableString content = new SpannableString("DesignerID"); 
	    content.setSpan(new UnderlineSpan(), 0, content.length(), 0); 
	    
	    txtDesignerID.setText(content);
	    txtDesignerID.setOnClickListener(OnClickDesignerID);

	    
	    btnLike.setOnClickListener(OnClickLike);
	    btnBuy.setOnClickListener(OnClickBuy);
	    btnExit.setOnClickListener(OnClickExit);
	    
	    mAdapter = new TestFragmentAdapter(getSupportFragmentManager());
	    
        mPager = (ViewPager)findViewById(R.id.pager1);
        mPager.setAdapter(mAdapter);

        mIndicator = (CirclePageIndicator)findViewById(R.id.pagerIndicator);
        mIndicator.setViewPager(mPager);
        
              
        ArrayList<String> a = new ArrayList<String>();

        for (int i = 0; i < 26; i++) {
         a.add(i, "");
        
        } 
       
        Iterator<String> it = a.iterator();
       
        List<GridFragment> gridFragments = new ArrayList<GridFragment>();        
       
        int i = 0;
        while (it.hasNext()) {
         ArrayList<GridItems> itmLst = new ArrayList<GridItems>();
       
         GridItems itm = new GridItems(0, it.next());
         itmLst.add(itm);
         i = i + 1;
       
         if (it.hasNext()) {
          GridItems itm1 = new GridItems(1, it.next());
          itmLst.add(itm1);
          i = i + 1;
         }
       
         if (it.hasNext()) {
          GridItems itm2 = new GridItems(2, it.next());
          itmLst.add(itm2);
          i = i + 1;
         }
       
         if (it.hasNext()) {
          GridItems itm3 = new GridItems(3, it.next());
          itmLst.add(itm3);
          i = i + 1;
         }
       
         if (it.hasNext()) {
          GridItems itm4 = new GridItems(4, it.next());
          itmLst.add(itm4);
          i = i + 1;
         }
       
         if (it.hasNext()) {
          GridItems itm5 = new GridItems(5, it.next());
          itmLst.add(itm5);
          i = i + 1;
         }
       
         if (it.hasNext()) {
          GridItems itm6 = new GridItems(6, it.next());
          itmLst.add(itm6);
          i = i + 1;
         }
       
         if (it.hasNext()) {
          GridItems itm7 = new GridItems(7, it.next());
          itmLst.add(itm7);
          i = i + 1;
         }
       
         if (it.hasNext()) {
          GridItems itm8 = new GridItems(8, it.next());
          itmLst.add(itm8);
          i = i + 1;
         }
         
         if (it.hasNext()) {
             GridItems itm9 = new GridItems(9, it.next());
             itmLst.add(itm9);
             i = i + 1;
            }
         
         if (it.hasNext()) {
             GridItems itm10 = new GridItems(10, it.next());
             itmLst.add(itm10);
             i = i + 1;
            }
         
         if (it.hasNext()) {
             GridItems itm11 = new GridItems(11, it.next());
             itmLst.add(itm11);
             i = i + 1;
            }
         
         
       
         GridItems[] gp = {};
         GridItems[] gridPage = itmLst.toArray(gp);
         gridFragments.add(new GridFragment(gridPage, ItemsPackage.this));
        }
       
        pm = new PagerAdapter(getSupportFragmentManager(), gridFragments);
        mPager.setAdapter(pm);
        mIndicator.setViewPager(mPager);
        
        
	}
	
	View.OnClickListener OnClickDesignerID = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(ItemsPackage.this, DesignerProfile.class);
			TextView txtDesignerID = (TextView)v.findViewById(R.id.txtDesignerID);			
			i.putExtra("Account", txtDesignerID.getText().toString());
			startActivity(i);
			
		}
	};
	
	View.OnClickListener OnClickLike = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Toast.makeText(ItemsPackage.this, "Like", Toast.LENGTH_LONG).show();
			finish();
		}
	};
	
	View.OnClickListener OnClickBuy = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Toast.makeText(ItemsPackage.this, "Buy", Toast.LENGTH_LONG).show();
			finish();
		}
	};

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.stay, R.anim.slide_out_down);
	}
	
	private class PagerAdapter extends FragmentStatePagerAdapter {
		  private List<GridFragment> fragments;
		 
		  public PagerAdapter(FragmentManager fm, List<GridFragment> fragments) {
		   super(fm);
		   this.fragments = fragments;
		  }
		 
		  @Override
		  public Fragment getItem(int position) {
		   return this.fragments.get(position);
		  }
		 
		  @Override
		  public int getCount() {
			  Log.d("fragments", "fragments : " + fragments.size());
		   return this.fragments.size();
		  }
		 }
}

