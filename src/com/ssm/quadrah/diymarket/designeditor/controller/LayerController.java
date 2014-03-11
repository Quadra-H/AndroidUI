package com.ssm.quadrah.diymarket.designeditor.controller;


public class LayerController {/*
	private SpenPageDoc spenPageDoc;
	private SpenSurfaceView spenSurfaceView;
	
	private ImageView layerImageView;
	
	private int layerCount;
	private int currentLayer;

	private ArrayList<Bitmap> layerBitmap;
	
	private int maxLayer;

    private Bitmap frontLayersBitmap;
    private Bitmap behindLayersBitmap;
    
    private Rect dstRect;
    private Rect srcRect;
    
	public LayerController(SpenSurfaceView mSpenSurfaceView, SpenPageDoc mSpenPageDoc, ImageView mLayerImageView) {
		spenSurfaceView = mSpenSurfaceView;
		spenPageDoc = mSpenPageDoc;

        mSpenSurfaceView.setPreDrawListener(mPreDrawListener);
        mSpenSurfaceView.setPostDrawListener(mPosteDrawListener);
		
		layerBitmap = new ArrayList<Bitmap>();
		
		layerCount = 0;
		currentLayer = 0;
		
		layerImageView = mLayerImageView;
		
		//TODO : set maxLayer
		maxLayer = 12;

		
		int screenSize = canvas.getWidth() > canvas.getHeight() ? canvas.getWidth() : canvas.getHeight();
		frontLayersBitmap = Bitmap.createScaledBitmap(backgroundBitmap, screenSize, screenSize, true);
		
		srcRect = new Rect(0, 0, frontLayersBitmap.getWidth(), frontLayersBitmap.getHeight());
        dstRect = new Rect(0, 0, frontLayersBitmap.getWidth(), frontLayersBitmap.getHeight());
	}
		
	public int appendLayer() {
		if( layerCount == maxLayer) {
			Log.e("LayerController", "cannot append layer");
			return -1;
		}
		
		layerBitmap.add(Bitmap.createBitmap(spenSurfaceView.getWidth(), spenSurfaceView.getHeight(), Bitmap.Config.ARGB_8888));
		maxLayer++;
		
		return 0;
	}
	
	public int deleteLayer(int layerNum) {
		if( maxLayer == 0 || layerNum > maxLayer ) {
			Log.e("LayerController", "cannot delete layer");
			return -1;
		}
		
        // mSpenPageDoc.removeSelectedObject();
        for (SpenObjectBase obj : layeredSpenObjectArrayList.get(layerNum)) {
            spenPageDoc.removeObject(obj);
        }
        spenSurfaceView.closeControl();
        spenSurfaceView.update();

		layeredSpenObjectArrayList.get(layerNum).clear();
		layeredSpenObjectArrayList.remove(layerNum);
		
		layerBitmap.get(layerNum).recycle();
		layerBitmap.remove(layerNum);
		layerCount--;
		
		return 0;
	}
	
	public void refreshLayerMinimap() {
    	//update screen and drawing chache
    	spenSurfaceView.update();
    	spenSurfaceView.updateScreen();
    	
    	 if( layerBitmap.size() <= currentLayer ) {
    		layerBitmap.add(Bitmap.createBitmap(spenSurfaceView.getWidth()/5, spenSurfaceView.getHeight()/5, Bitmap.Config.ARGB_8888));
    	}
    	else
    		layerBitmap.get(currentLayer).recycle();
    	
		//capture view
    	layerBitmap.set(currentLayer, spenSurfaceView.capturePage(0.2f));
    	layerImageView.setImageBitmap(layerBitmap.get(currentLayer));
	}
	
	public int setCurrentLayer(int sLayer) {
		if( sLayer > layerCount || sLayer < 0 ) {
			Log.e("LayerController", "invalid layer access");
			return -1;
		}

		refreshLayerMinimap();

		spenSurfaceView.closeControl();
    	spenSurfaceView.update();
    	spenSurfaceView.updateScreen();
		
		//change current layer
		currentLayer = sLayer;
		
		return sLayer;
	}

	//frontLayer Drawer
    private final SpenDrawListener mPreDrawListener = new SpenDrawListener() {
        @Override
        public void onDraw(Canvas canvas, float x, float y, float ratio, float frameStartX, float frameStartY, RectF updateRect) {
            canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
            
            //set dst and draw bitmap
            dstRect.set(left, top, right, bottom);
            canvas.drawBitmap(behindLayersBitmap, src, dst, null);
        }
    };

    //behindLayer Drawer
    private final SpenDrawListener mPosteDrawListener = new SpenDrawListener() {
        @Override
        public void onDraw(Canvas canvas, float x, float y, float ratio, float frameStartX, float frameStartY, RectF updateRect) {
            canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);

            //set dst and draw bitmap
            dstRect.set(left, top, right, bottom);
            canvas.drawBitmap(behindLayersBitmap, src, dst, null);
        }
    };
	
	public int getCurrentLayer() {
		return currentLayer;
	}
	
	public int getLayerCount() {
		return layerCount;
	}*/
}