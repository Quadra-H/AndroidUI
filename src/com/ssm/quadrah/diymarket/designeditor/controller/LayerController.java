package com.ssm.quadrah.diymarket.designeditor.controller;
import java.util.ArrayList;
import java.util.Iterator;

import android.util.Log;

import com.samsung.android.sdk.pen.document.SpenObjectBase;
import com.samsung.android.sdk.pen.document.SpenObjectContainer;
import com.samsung.android.sdk.pen.document.SpenPageDoc;
import com.samsung.android.sdk.pen.engine.SpenSurfaceView;

public class LayerController {
	private SpenPageDoc spenPageDoc;
	private SpenSurfaceView spenSurfaceView;
	
	private int layerCount;
	private int currentLayer;
	
	private ArrayList<SpenObjectBase> clGroupList;
	
	public LayerController(SpenSurfaceView mSpenSurfaceView, SpenPageDoc mSpenPageDoc) {
		spenSurfaceView = mSpenSurfaceView;
		spenPageDoc = mSpenPageDoc;
		
		layerCount = 0;
		currentLayer = 0;
		
		clGroupList = new ArrayList<SpenObjectBase>();
	}
		
	public void appendLayer() {
		layerCount++;
	}
	
	public int setCurrentLayer(int sLayer) {
		if( sLayer > layerCount ) {
			appendLayer();
		}		
		else if ( sLayer < 0 ) {
			Log.e("LayerController", "invalid layer access");
			return -1;
		}

		ArrayList<SpenObjectBase> clObjList = spenPageDoc.getObjectList();
		Iterator<SpenObjectBase> clObjIter = clObjList.iterator();
		SpenObjectBase spObj;
		
		//fill 
		while( clObjIter.hasNext() ) {
			spObj = clObjIter.next();
			
			if( spObj.getExtraDataInt("layer") == currentLayer ) {
				spObj.setSelectable(false);
				clGroupList.add(spObj);
			}
		}
		
        if( clGroupList.size() > 1 ) {
			// Group the objects.
            SpenObjectContainer objContainer;
	        objContainer = spenPageDoc.groupObject(clGroupList, false);
	        spenSurfaceView.closeControl();
	        //spenPageDoc.selectObject(objContainer);
	        objContainer.setSelectable(false);
	        objContainer.setExtraDataInt("layer", currentLayer);
        }
        
        // Ungroup other grouped objects
        for (SpenObjectBase selectedObj : clObjList) {
            SpenObjectContainer objContainer;
			if( selectedObj.getExtraDataInt("layer") == sLayer ) {
				selectedObj.setSelectable(true);

	        	if (selectedObj.getType() == SpenObjectBase.TYPE_CONTAINER && selectedObj.getExtraDataInt("group") == 0 ) {
	        		objContainer = (SpenObjectContainer) selectedObj;
	        		objContainer.setSelectable(true);
					for (SpenObjectBase obj : objContainer.getObjectList()) {
						obj.setSelectable(true);
					}
	        		spenPageDoc.ungroupObject((SpenObjectContainer) selectedObj, false);
	        	}
			}
        }
        
        spenSurfaceView.closeControl();

        spenSurfaceView.update();
		currentLayer = sLayer;
		
		clGroupList.clear();
		
		return sLayer;
	}
	
	public int getCurrentLayer() {
		return currentLayer;
	}
	
	public int getLayerCount() {
		return layerCount;
	}
}
