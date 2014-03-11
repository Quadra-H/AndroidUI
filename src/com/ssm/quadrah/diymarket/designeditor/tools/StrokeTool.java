package com.ssm.quadrah.diymarket.designeditor.tools;

import java.util.ArrayList;

import com.samsung.android.sdk.pen.document.SpenObjectStroke;
import com.samsung.android.sdk.pen.document.SpenPageDoc;
import com.samsung.android.sdk.pen.engine.SpenSurfaceView;
import com.samsung.android.sdk.pen.settingui.SpenSettingPenLayout;

import android.graphics.Color;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;

public class StrokeTool {
	private SpenSurfaceView spenSurfaceView;
	private SpenPageDoc spenPageDoc;
	private SpenSettingPenLayout penSettingView;
    private FrameLayout strokeToolSettingView;

	private int color;
	
	private ArrayList<PointF> pointArray;
		
	public StrokeTool(SpenSurfaceView mSpenSurfaceView, SpenPageDoc mSpenPageDoc, SpenSettingPenLayout mPenSettingView, FrameLayout mStrokeToolSettingView) {
		spenSurfaceView = mSpenSurfaceView;
		spenPageDoc = mSpenPageDoc;
		penSettingView = mPenSettingView;
		
		strokeToolSettingView = mStrokeToolSettingView;
		strokeToolSettingView.setOnTouchListener(strokeToolTouchListener);
		
		color = Color.BLACK;
		
		pointArray = new ArrayList<PointF>();
	}

	private final OnTouchListener strokeToolTouchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			
			return true;
		}
	};

	public void setColor(int mColor) {
		color = mColor;
	}
	
	public int getColor() {
		return color;
	}

	public void endDraw() {
		pointArray.clear();
	}
	
	public void drawLine(float mx, float my) {
		//pan coordinate to pixel coordinate
		float x = (mx / spenSurfaceView.getZoomRatio()) + spenSurfaceView.getPan().x;
		float y = (my / spenSurfaceView.getZoomRatio()) + spenSurfaceView.getPan().y;
		
		//exception handle
		if( x < 0 ) x = 0;
		if( y < 0 ) y = 0;
		if( x >= spenSurfaceView.getWidth()) x = spenSurfaceView.getWidth() - 1;
		if( y >= spenSurfaceView.getHeight()) y = spenSurfaceView.getHeight() - 1; 

		//if empty == start
		if( pointArray.isEmpty() ) {
			pointArray.add(new PointF(x, y));
		}
		else {
			//get start position
			float x1, y1;
			x1 = pointArray.get(0).x;
			y1 = pointArray.get(0).y;
			
			//get line length
			double length;
			length = Math.sqrt((double)((x-x1)*(x-x1)+(y-y1)*(y-y1)));

			//get unit length
			float unitX;
			float unitY;
			unitX = (x1-x)/(float)length;
			unitY = (y1-y)/(float)length;
			
			//fill stroke point array
	        float[][] strokePoint = new float[(int)length][2];
	        for (int i = 0; i < (int)length; i++) {
	            strokePoint[i][0] = x + i*unitX;
	            strokePoint[i][1] = y + i*unitY;
	        }
	        
	        PointF[] points = new PointF[(int)length];
	        float[] pressures = new float[(int)length];
	        int[] timestamps = new int[(int)length];

	        //stroke point to PointF array
	        for (int i = 0; i < (int)length; i++) {
	            points[i] = new PointF();
	            points[i].x = strokePoint[i][0];
	            points[i].y = strokePoint[i][1];
	            pressures[i] = 1;
	            timestamps[i] = (int) android.os.SystemClock.uptimeMillis();
	        }

	        SpenObjectStroke strokeObj = new SpenObjectStroke(penSettingView.getInfo().name, points, pressures, timestamps);
	        strokeObj.setPenSize(penSettingView.getInfo().size);
	        strokeObj.setColor(color);
	        spenPageDoc.appendObject(strokeObj);
	        spenSurfaceView.update();
	        
	        //clear point array list
	        pointArray.clear();
		}
	}
	
	public void drawPolygon(float mx, float my) {
	}
}
