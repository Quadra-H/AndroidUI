package com.ssm.quadrah.diymarket.content;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.AlertDialog;
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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

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
	
	private RatingBar ratingBar;
	private float ratingBarValue;
	
	 Button btnExit;
	 Button btnLike;
	 Button btnBuy;
	
	public float getRatingBarValue()
	{
		return this.ratingBarValue;
	}
	
	public void setRatingBarValue(float value)
	{
		this.ratingBarValue = value;
		ratingBar.setRating(ratingBarValue);
		ratingBar.refreshDrawableState();
	}
	 
	
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
	
	private static String strID;
	private String strCompare;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_itempackage);
	    // TODO Auto-generated method stub
	    
	    btnExit = (Button)findViewById(R.id.btnExit);
	    btnLike = (Button)findViewById(R.id.btnLike);
	    btnBuy = (Button)findViewById(R.id.btnBuy);
	    txtDesignerID = (TextView)findViewById(R.id.txtDesignerID);
	    //txtDesignerID.setTextColor(Color.RED);
	    SpannableString content = new SpannableString("DesignerID"); 
	    content.setSpan(new UnderlineSpan(), 0, content.length(), 0); 
	    
	    LinearLayout wrapRTBlayout = (LinearLayout)findViewById(R.id.wrapRTBlayout);
	    wrapRTBlayout.setOnClickListener(OnClickRatingBar);
	    
	    ratingBar = (RatingBar)findViewById(R.id.rtbProductRating);	
	    ratingBar.setOnClickListener(OnClickRatingBar);
	    ratingBar.setClickable(true);
	    ratingBar.setStepSize((float) 0.5); 
	    ratingBar.setEnabled(true);
	    ratingBar.setRating(0);
	    
	    
	    
//	    ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
//	    	 
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating,
//                    boolean fromUser) {
//            	
//            	ratingBar.setRating(rating);
//            	ratingBarValue = rating;
//            	Toast.makeText(ItemsPackage.this, String.valueOf("�����մϴ�." + ratingBar.getRating()), Toast.LENGTH_SHORT).show();                   
// 
//            }
//        });
//
//	    
	    
	    Bundle getUserID = getIntent().getExtras();	    
	    
	    if(getUserID != null){
	    	strID = getIntent().getStringExtra("Account");
	    }
	    
	    txtDesignerID.setText(content);
	    txtDesignerID.setOnClickListener(OnClickDesignerID);
	    
	    strCompare = txtDesignerID.getText().toString();
	    Log.d("ID", strCompare + " : " + strID);
	    if(strID != null){
		    if(strCompare.toString().equals(strID.toString()))
		    {
		    	txtDesignerID.setClickable(false);
		    	txtDesignerID.setFocusable(false);
		    	txtDesignerID.setTextColor(Color.BLACK);
		    }
	    }

	    

	    
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
	RatingBar tmpRating;
	static float tmpRatingBarValue;
	
	View.OnClickListener OnClickRatingBar = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			View dialog = View.inflate(getApplicationContext(), R.layout.ratingbar_dialog, null);  
            final AlertDialog ad = new AlertDialog.Builder(ItemsPackage.this).setView(dialog).create();
            tmpRating = (RatingBar)dialog.findViewById(R.id.rtbSet);
            
            tmpRating.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
   	    	 
               @Override
               public void onRatingChanged(RatingBar ratingBar, float rating,
                       boolean fromUser) {
               	
            	tmpRating.setRating(rating);
            	setRatingBarValue(rating);
               	
               	                   
    
               }
           });
            
            dialog.findViewById(R.id.completeButton).setOnClickListener(new OnClickListener() {  
                
                @Override  
                public void onClick(View v) {
                	
                	ad.dismiss();
                      
                      
                }  
            });  
            
            ad.show(); 
		}
	};
	
	View.OnClickListener OnClickDesignerID = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
					    
			Intent i = new Intent(ItemsPackage.this, DesignerProfile.class);					
			i.putExtra("Account", txtDesignerID.getText().toString());
			startActivity(i);
			
		}
	};
	
	View.OnClickListener OnClickLike = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Toast.makeText(ItemsPackage.this, "Like", Toast.LENGTH_LONG).show();
			btnBuy.setPressed(false);
			finish();
		}
	};
	
	View.OnClickListener OnClickBuy = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Toast.makeText(ItemsPackage.this, "Buy", Toast.LENGTH_LONG).show();
			btnBuy.setPressed(false);
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

