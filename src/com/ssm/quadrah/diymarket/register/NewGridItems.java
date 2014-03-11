package com.ssm.quadrah.diymarket.register;

import android.widget.ImageView;

public class NewGridItems {
	 public int id;
	 public int flag = 1;	 
	 public ImageView image;
	 
	 public int TYPE; // 
	 public String strType; //경로 설정 (1_, 2_, 3_, 4_) + saveFilePath + _filename + .png or .spd
	 
	 public int width;
	 public int height;
	 
	 public String title;
	 public String saveFilePathPNG;
	 public String saveFilePathSPD;
	 
	 
	 public NewGridItems(int id, String title) {
		 this.id = id;
		 this.title = title;
	  
	 }
}
