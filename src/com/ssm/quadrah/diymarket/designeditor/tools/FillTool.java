package com.ssm.quadrah.diymarket.designeditor.tools;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.samsung.android.sdk.pen.document.SpenObjectImage;
import com.samsung.android.sdk.pen.document.SpenPageDoc;
import com.samsung.android.sdk.pen.engine.SpenSurfaceView;
import com.ssm.quadrah.diymarket.designeditor.util.SimpleStack;

public class FillTool {
	private SpenSurfaceView spenSurfaceView;
	private SpenPageDoc spenPageDoc;
    private FrameLayout fillToolSettingView;
	private ImageView fillToolSettingSlider;

	private int color;
	
	private int thresholdRangeStart;
	private int thresholdRangeEnd;
	private int threshold;
	
	private SimpleStack stack;
	private int labelTable[][];
	
	public FillTool(SpenSurfaceView mSpenSurfaceView, SpenPageDoc mSpenPageDoc, FrameLayout mFillToolSettingView, ImageView mFillToolSettingSlider) {
		spenSurfaceView = mSpenSurfaceView;
		spenPageDoc = mSpenPageDoc;
		fillToolSettingView = mFillToolSettingView;
		fillToolSettingSlider = mFillToolSettingSlider;

		fillToolSettingView.setOnTouchListener(fillToolTouchListener);
		
		thresholdRangeStart = 30;
		thresholdRangeEnd = 260;
		threshold = 1;
		
		color = Color.BLACK;

		fillToolSettingSlider.setX(thresholdRangeStart);
		fillToolSettingSlider.setY(80);

	}

	private final OnTouchListener fillToolTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			float x = event.getX();

			if( x < thresholdRangeStart )
				x = thresholdRangeStart;
			if( x > thresholdRangeEnd )
				x = thresholdRangeEnd;

			fillToolSettingSlider.setX(x);

			threshold = ((int)x - thresholdRangeStart)*36/(thresholdRangeEnd - thresholdRangeStart);
			
			return true;
		}
	};

	public void setColor(int mColor) {
		color = mColor;
	}
	
	public int getColor() {
		return color;
	}
	
	public void fillArea(float mx, float my) {
			
		int x = (int)((mx / spenSurfaceView.getZoomRatio()) + spenSurfaceView.getPan().x);
		int y = (int)((my / spenSurfaceView.getZoomRatio()) + spenSurfaceView.getPan().y);

		//exception handle
		if( x < 0 ) x = 0;
		if( y < 0 ) y = 0;
		if( x >= spenSurfaceView.getWidth()) x = spenSurfaceView.getWidth() - 1;
		if( y >= spenSurfaceView.getHeight()) y = spenSurfaceView.getHeight() - 1; 
		
        SpenObjectImage fillImgObj = new SpenObjectImage();
        Bitmap originImageBitmap;
        Bitmap fillImageBitmap;

        Rect rect;

        originImageBitmap = spenSurfaceView.capturePage(1.0f);

        
        //return pixel position
        rect = doGrassFire(originImageBitmap, x, y);
        
        if( rect.width() < 1 )
        	return;
        if( rect.height() < 1 )
        	return;
        
        //create Bitmap size fit fill area
        //fillImageBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888););
        fillImageBitmap = Bitmap.createBitmap(originImageBitmap, rect.left, rect.top, rect.width(), rect.height());
        
        // Set the location to insert ObjectImage and add it to PageDoc.
        fillImgObj.setImage(fillImageBitmap);

        //rect to rectF
        RectF rectF = new RectF();
        rectF.set(rect.left, rect.top, rect.right, rect.bottom);
        
        fillImgObj.setRect(rectF, true);
        spenPageDoc.appendObject(fillImgObj);
        spenSurfaceView.update();

        fillImageBitmap.recycle();
        originImageBitmap.recycle();
        
	}
	
	private Rect doGrassFire(Bitmap originImageBitmap, int startX, int startY) {
		Rect fillAreaSize = new Rect();

		int curColor, preColor;
		int curR, curG, curB, curA;
		int preR, preG, preB, preA;
		
		int preX, preY;
		int curX, curY;
		
		//total width, height
		int width, height;
		width = originImageBitmap.getWidth();
		height = originImageBitmap.getHeight();
		
		//for check fill area
		int top, bottom, right, left;
		top = startY;
		bottom = startY;
		right = startX;
		left = startX;
		
		//init stack and label table
		if( stack == null )
			stack = new SimpleStack(originImageBitmap.getWidth()*originImageBitmap.getHeight()*2);
		if( labelTable == null )
			labelTable = new int[originImageBitmap.getHeight()][originImageBitmap.getWidth()];
		else {
			for( int i = 0 ; i < originImageBitmap.getHeight() ; i++ ) {
				for( int j = 0 ; j < originImageBitmap.getWidth() ; j++ ) {
					labelTable[i][j] = 0;
				}
			}
		}
		
		//push first redpoint
		stack.push(startX);
		stack.push(startY);

		//mark 1(maybe) to labelTable
		labelTable[startY][startX] = 1;
		
		//while until stack empty
		while(true) {		
			preY = stack.pop();
			preX = stack.pop();

			// if SimpleStack is empty pop -1
			if( preX == -1 || preY == -1 )
				break;
			
			//set before RGB vals
			preColor = originImageBitmap.getPixel(preX, preY); 
			preR = (preColor >> 16) & 0xFF;
			preG = (preColor >> 8) & 0xFF;
			preB = preColor & 0xFF;
			preA = preColor >>> 24;

			//right
			if( preX + 1 < width ) {
				//Coordinates of right side
				curX = preX + 1;
				curY = preY;
								
				//if not marking
				if( labelTable[curY][curX] == 0 ) {
					//set current RGB vals
					curColor = originImageBitmap.getPixel(curX, curY); 
					curR = (curColor >> 16) & 0xFF;
					curG = (curColor >> 8) & 0xFF;
					curB = curColor & 0xFF;
					curA = curColor >>> 24; 

					//if similar
					if( (-threshold < (curR - preR) && (curR - preR) < threshold)
						&& (-threshold < (curG - preG) && (curG - preG) < threshold)
						&& (-threshold < (curB - preB) && (curB - preB) < threshold)
						&& (-threshold < (curA - preA) && (curA - preA) < threshold) ) {
						stack.push(curX);
						stack.push(curY);
						
						if( curX > right )
							right = curX;
						
						labelTable[curY][curX] = 1;
					}
					else {
						labelTable[curY][curX] = 2;
					}
				}
			}
			//up
			if( preY > 0 ) {
				//Coordinates of up side
				curX = preX;
				curY = preY - 1;
				
				//if not marking
				if( labelTable[curY][curX] == 0 ) {
					//set current RGB vals
					curColor = originImageBitmap.getPixel(curX, curY); 
					curR = (curColor >> 16) & 0xFF;
					curG = (curColor >> 8) & 0xFF;
					curB = curColor & 0xFF;
					curA = curColor >>> 24; 

					//if similar
					if( (-threshold < (curR - preR) && (curR - preR) < threshold)
						&& (-threshold < (curG - preG) && (curG - preG) < threshold)
						&& (-threshold < (curB - preB) && (curB - preB) < threshold)
						&& (-threshold < (curA - preA) && (curA - preA) < threshold) ) {
						stack.push(curX);
						stack.push(curY);
						
						if( curY < top )
							top = curY;
						
						labelTable[curY][curX] = 1;
					}
					else {
						labelTable[curY][curX] = 2;
					}
				}
			}
			//left
			if( preX > 0 ) {
				//Coordinates of left side
				curX = preX - 1;
				curY = preY;
				
				//if not marking
				if( labelTable[curY][curX] == 0 ) {
					//set current RGB vals
					curColor = originImageBitmap.getPixel(curX, curY); 
					curR = (curColor >> 16) & 0xFF;
					curG = (curColor >> 8) & 0xFF;
					curB = curColor & 0xFF;
					curA = curColor >>> 24; 

					//if similar
					if( (-threshold < (curR - preR) && (curR - preR) < threshold)
						&& (-threshold < (curG - preG) && (curG - preG) < threshold)
						&& (-threshold < (curB - preB) && (curB - preB) < threshold)
						&& (-threshold < (curA - preA) && (curA - preA) < threshold) ) {
						stack.push(curX);
						stack.push(curY);
						
						if( curX < left )
							left = curX;
						
						labelTable[curY][curX] = 1;
					}
					else {
						labelTable[curY][curX] = 2;
					}
				}
			}
			//down
			if( preY < height -1 )	{
				//coordinates of up side
				curX = preX;
				curY = preY + 1;

				//if not marking
				if( labelTable[curY][curX] == 0 ) {
					//set current RGB vals
					curColor = originImageBitmap.getPixel(curX, curY); 
					curR = (curColor >> 16) & 0xFF;
					curG = (curColor >> 8) & 0xFF;
					curB = curColor & 0xFF;
					curA = curColor >>> 24; 

					//if similar
					if( (-threshold < (curR - preR) && (curR - preR) < threshold)
						&& (-threshold < (curG - preG) && (curG - preG) < threshold)
						&& (-threshold < (curB - preB) && (curB - preB) < threshold)
						&& (-threshold < (curA - preA) && (curA - preA) < threshold) ) {
						stack.push(curX);
						stack.push(curY);
						
						if( curY > bottom )
							bottom = curY;
						
						labelTable[curY][curX] = 1;
					}
					else {
						labelTable[curY][curX] = 2;
					}
				}
			}
		}//end while(!queempty)
	
		//TODO : after sharpening?

		right++;
		bottom++;

		int temp;
		for(int y = top + 2 ; y < bottom - 2 ; y++ ) {
			for(int x = left + 2 ; x < right - 2 ; x++ ) {
				temp = 0;
				if( labelTable[y][x - 2] == 1 ) temp++;
				if( labelTable[y][x - 1] == 1 ) temp++;
				if( labelTable[y][x + 1] == 1 ) temp++;
				if( labelTable[y][x + 2] == 1 ) temp++;
				if( labelTable[y - 2][x] == 1 ) temp++;
				if( labelTable[y - 1][x] == 1 ) temp++;
				if( labelTable[y + 1][x] == 1 ) temp++;
				if( labelTable[y + 2][x] == 1 ) temp++;
				
				if( temp > 2 )
					labelTable[y][x] = 3;
			}
		}
		
		for(int y = top ; y < bottom ; y++ ) {
			for(int x = left ; x < right ; x++ ) {
				temp = labelTable[y][x];
				if( temp != 0 && temp != 2 ) {
					originImageBitmap.setPixel(x, y, color); 
				}
				else {
					originImageBitmap.setPixel(x, y, Color.TRANSPARENT);
				}
			}
		}

		fillAreaSize.set(left, top, right, bottom);
		return fillAreaSize;
	}
}
