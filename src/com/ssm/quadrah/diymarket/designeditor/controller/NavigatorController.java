package com.ssm.quadrah.diymarket.designeditor.controller;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.samsung.android.sdk.pen.engine.SpenSurfaceView;

public class NavigatorController {
	private SpenSurfaceView spenSurfaceView;

	//views which are show navigator image and pan
	private FrameLayout navigatorView;
    private ImageView navigatorImage;
    private ImageView navigatorPan;
    
	private Handler navigatorRefreshHandler;
	
    private Bitmap navigatorImageBitmap;
    private Bitmap navigatorPanBitmap;
    
    private Paint panPaint;
    private Canvas panOffscreen;
    private PointF position;
    
    private int spenSurfaceViewWidth;
    private int spenSurfaceViewHeight;
    private int navigatorImageWidth;
    private int navigatorImageHeight;
    
    
	public NavigatorController(SpenSurfaceView mSpenSurfaceView, FrameLayout mNavigatorView, ImageView mNavigatorImage, ImageView mNavigatorPan) {
		spenSurfaceView = mSpenSurfaceView;
		navigatorView = mNavigatorView;
		navigatorImage = mNavigatorImage;
		navigatorPan = mNavigatorPan;
		
		//re usable object init
    	navigatorImageBitmap = spenSurfaceView.capturePage(0.2f);
    	navigatorImageWidth = navigatorImageBitmap.getWidth();
    	navigatorImageHeight = navigatorImageBitmap.getHeight();
    	
		navigatorPanBitmap = Bitmap.createBitmap(navigatorImageWidth, navigatorImageHeight, Bitmap.Config.ARGB_8888);
		panOffscreen = new Canvas(navigatorPanBitmap);
		panOffscreen.drawColor(Color.TRANSPARENT);
		panPaint = new Paint();
		position = new PointF();
	}
	
    public void refreshHandle(int action) {
    	//for delayed callbacks
        switch (action) { 
	        case MotionEvent.ACTION_DOWN:
				// If ACTION_DOWN happens before mNavigatorRefresh is in the queue,
				// remove the existing mNavigatorRefresh from the queue.
	            if (navigatorRefreshHandler != null) {
	            	navigatorRefreshHandler.removeCallbacks(mNavigatorRefresh);
	            	navigatorRefreshHandler = null;
	            }
	            break;           
	        case MotionEvent.ACTION_UP:
				// If 512 milliseconds passes after ACTION_UP,
				// add mNavigatorRefresh to the queue.
	        	navigatorRefreshHandler = new Handler();
	        	navigatorRefreshHandler.postDelayed(mNavigatorRefresh, 512);
	            break;
        }   	
    }
    
    public void releaseHandle() {
        if (navigatorRefreshHandler != null) {
        	navigatorRefreshHandler.removeCallbacks(mNavigatorRefresh);
        	navigatorRefreshHandler = null;
        }    	
    }
    
    private final Runnable mNavigatorRefresh = new Runnable() {
        @Override
        public void run() {
        	refreshNavigator();
        }
    };
    
    public void refreshNavigator() {
    	//update screen and drawing chache
    	spenSurfaceView.update();
    	spenSurfaceView.updateScreen();
    	
    	//capture view
    	navigatorImageBitmap = spenSurfaceView.capturePage(0.2f);
		
        if(navigatorImageBitmap != null) {
        	navigatorImage.setImageBitmap(navigatorImageBitmap);
        }
    }
    
    public void zoomListener(float zoomPosX, float zoomPosY, float zoomRatio) {
		//exception
		if( navigatorImageBitmap == null || navigatorPan == null )
			return ;

		//recycle bitmap which is will be craete
		if( navigatorPanBitmap != null )
			navigatorPanBitmap.recycle();
		
		navigatorPanBitmap = Bitmap.createBitmap(navigatorImageWidth, navigatorImageHeight, Bitmap.Config.ARGB_8888);
		panOffscreen = new Canvas(navigatorPanBitmap);

		//transform ZoomPos to navigator pos 
		zoomPosX/=5;
		zoomPosY/=5;
		float right = zoomPosX + navigatorImageWidth/zoomRatio;
		float bottom = zoomPosY + navigatorImageHeight/zoomRatio;
		if ( right >= navigatorImageWidth ) {
			right  = navigatorImageWidth - 1;
		}
		if ( bottom >= navigatorImageHeight ) {
			bottom = navigatorImageHeight - 1;
		}

		panPaint.setColor(Color.BLACK);
		panOffscreen.drawLine(zoomPosX, zoomPosY, right, zoomPosY, panPaint);
		panOffscreen.drawLine(zoomPosX, zoomPosY, zoomPosX, bottom, panPaint);
		panOffscreen.drawLine(zoomPosX, bottom, right, bottom, panPaint);
		panOffscreen.drawLine(right, zoomPosY, right, bottom, panPaint);
		panPaint.setColor(Color.WHITE);
		panOffscreen.drawLine(zoomPosX, zoomPosY+1, right, zoomPosY+1, panPaint);
		panOffscreen.drawLine(zoomPosX+1, zoomPosY, zoomPosX+1, bottom, panPaint);
		panOffscreen.drawLine(zoomPosX, bottom-1, right, bottom-1, panPaint);
		panOffscreen.drawLine(right-1, zoomPosY, right-1, bottom, panPaint);
		panPaint.setColor(Color.BLACK);
		panOffscreen.drawLine(zoomPosX, zoomPosY+2, right, zoomPosY+2, panPaint);
		panOffscreen.drawLine(zoomPosX+2, zoomPosY, zoomPosX+2, bottom, panPaint);
		panOffscreen.drawLine(zoomPosX, bottom-2, right, bottom-2, panPaint);
		panOffscreen.drawLine(right-2, zoomPosY, right-2, bottom, panPaint);

		navigatorPan.setImageBitmap(navigatorPanBitmap);
    }
    
    public void setPanPosition(int x, int y) {
    	spenSurfaceViewWidth = spenSurfaceView.getWidth();
    	spenSurfaceViewHeight = spenSurfaceView.getHeight();
    	
		//transform navigator cordinate to spensurfaceview cordinate 
		x = (x - ((navigatorView.getWidth() - navigatorImageWidth) / 2)) * spenSurfaceViewWidth / navigatorImageWidth;
		y = (y - ((navigatorView.getHeight() - navigatorImageHeight) / 2)) * spenSurfaceViewHeight / navigatorImageHeight;

		//out of screen exception handle
		if ( x < 0 ) x = 0;
		if ( y < 0 ) y = 0;
		if ( x >= spenSurfaceViewWidth ) x = spenSurfaceViewWidth;
		if ( y >= spenSurfaceViewHeight ) y = spenSurfaceViewHeight;

		//move half of pan
		x -= spenSurfaceViewWidth / spenSurfaceView.getZoomRatio() / 2;
		y -= spenSurfaceViewHeight / spenSurfaceView.getZoomRatio() / 2;

		//set pan touch position
		position.set(x, y);
		spenSurfaceView.setPan(position);

		////////////////?????????????
		spenSurfaceView.setZoomPadPosition(position);
	    	
		//TODO : set zoom..... getRealPoint Î•?Ï∞∏Ï°∞?òÏûê
		//spenSurfaceView.setZoom(x, y, spenSurfaceView.getZoomRatio() - 0.001f);
	}
}
