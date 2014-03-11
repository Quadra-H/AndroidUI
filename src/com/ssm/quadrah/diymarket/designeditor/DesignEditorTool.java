package com.ssm.quadrah.diymarket.designeditor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.pen.Spen;
import com.samsung.android.sdk.pen.SpenSettingEraserInfo;
import com.samsung.android.sdk.pen.SpenSettingPenInfo;
import com.samsung.android.sdk.pen.SpenSettingSelectionInfo;
import com.samsung.android.sdk.pen.SpenSettingTextInfo;
import com.samsung.android.sdk.pen.document.SpenInvalidPasswordException;
import com.samsung.android.sdk.pen.document.SpenNoteDoc;
import com.samsung.android.sdk.pen.document.SpenObjectBase;
import com.samsung.android.sdk.pen.document.SpenObjectContainer;
import com.samsung.android.sdk.pen.document.SpenObjectImage;
import com.samsung.android.sdk.pen.document.SpenObjectTextBox;
import com.samsung.android.sdk.pen.document.SpenPageDoc;
import com.samsung.android.sdk.pen.document.SpenPageDoc.HistoryListener;
import com.samsung.android.sdk.pen.document.SpenPageDoc.HistoryUpdateInfo;
import com.samsung.android.sdk.pen.document.SpenPageDoc.ObjectListener;
import com.samsung.android.sdk.pen.document.SpenUnsupportedTypeException;
import com.samsung.android.sdk.pen.document.SpenUnsupportedVersionException;
import com.samsung.android.sdk.pen.engine.SpenColorPickerListener;
import com.samsung.android.sdk.pen.engine.SpenContextMenuItemInfo;
import com.samsung.android.sdk.pen.engine.SpenControlBase;
import com.samsung.android.sdk.pen.engine.SpenControlList;
import com.samsung.android.sdk.pen.engine.SpenControlListener;
import com.samsung.android.sdk.pen.engine.SpenPenChangeListener;
import com.samsung.android.sdk.pen.engine.SpenSelectionChangeListener;
import com.samsung.android.sdk.pen.engine.SpenSurfaceView;
import com.samsung.android.sdk.pen.engine.SpenTextChangeListener;
import com.samsung.android.sdk.pen.engine.SpenTouchListener;
import com.samsung.android.sdk.pen.engine.SpenZoomListener;
import com.samsung.android.sdk.pen.pen.SpenPenInfo;
import com.samsung.android.sdk.pen.pen.SpenPenManager;
import com.samsung.android.sdk.pen.settingui.SpenSettingEraserLayout;
import com.samsung.android.sdk.pen.settingui.SpenSettingEraserLayout.EventListener;
import com.samsung.android.sdk.pen.settingui.SpenSettingPenLayout;
import com.samsung.android.sdk.pen.settingui.SpenSettingSelectionLayout;
import com.samsung.android.sdk.pen.settingui.SpenSettingTextLayout;
import com.ssm.quadrah.diymarket.R;
import com.ssm.quadrah.diymarket.designeditor.controller.LayerController;
import com.ssm.quadrah.diymarket.designeditor.controller.NavigatorController;
import com.ssm.quadrah.diymarket.designeditor.tools.FillTool;
import com.ssm.quadrah.diymarket.designeditor.tools.MagicWandTool;
import com.ssm.quadrah.diymarket.designeditor.tools.StrokeTool;
import com.ssm.quadrah.diymarket.designeditor.util.SDKUtils;

public class DesignEditorTool extends Activity {
    private final int CONTEXT_MENU_DELETE_ID = 10;
    private final int CONTEXT_MENU_GROUP_ID = 20;
    private final int CONTEXT_MENU_UNGROUP_ID = 21;
    //private final int CONTEXT_MENU_MOVE_TO_BOTTOM_ID = 30;
    private final int CONTEXT_MENU_MOVE_TO_BACKWARD_ID = 31;
    private final int CONTEXT_MENU_MOVE_TO_FORWARD_ID = 32;
    //private final int CONTEXT_MENU_MOVE_TO_TOP_ID = 33;
    
    private final int REQUEST_CODE_ATTACH_IMAGE = 100;

    private final String ATTACH_IMAGE_KEY = "Attach Image Key";

    private final int MODE_PEN = 0;
    private final int MODE_ERASER = 1;
    private final int MODE_FILLTOOL = 2;
    private final int MODE_OBJSEC = 3;
    private final int MODE_STROKE_OBJ = 4;
    private final int MODE_IMG_OBJ = 5;
    private final int MODE_TEXT_OBJ = 6;
    private final int MODE_MAGICWAND = 7;
    private final int MODE_SELECTION = 8;
    
	private Context mContext;
	private SpenNoteDoc mSpenNoteDoc;
	private SpenPageDoc mSpenPageDoc;
	private SpenSurfaceView mSpenSurfaceView;
	private SpenSettingPenLayout mPenSettingView;
	private SpenSettingEraserLayout mEraserSettingView;
	private SpenSettingTextLayout mTextSettingView;
    private SpenSettingSelectionLayout mSelectionSettingView;
    private FrameLayout mFillToolSettingView;
    private FrameLayout mStrokeToolSettingView;
    private FrameLayout mMagicWandToolSettingView;
    private FrameLayout mLayerSettingView;
    private FrameLayout mColorSettingView;
    private ImageView mColorSelectionView;
    private ImageView mColorSelectorView;    
	private LinearLayout mSettingView;
	
	private FrameLayout mNavigatorView;
	private LinearLayout mToolBoxView;
	
	private ImageView mCurrentToolBtn;
	private ImageView mCurrentColorBtn;
	private ImageView mPenBtn;
	private ImageView mEraserBtn;
	private ImageView mFillToolBtn;
    private ImageView mStrokeObjBtn;
	private ImageView mImgObjBtn;
	private ImageView mTextObjBtn;
	private ImageView mMagicWandToolBtn;
    private ImageView mSelectionBtn;
    private ImageView mSettingBtn;
    private ImageView mSaveBtn;
    private ImageView mLayerBtn;
	private ImageView mUndoBtn;
	private ImageView mRedoBtn;

	private ImageView mInitZoomBtn;
	private ImageView mNavigatorBtn;
	private ImageView mToolBoxBtn;
    
	/*private int[] mPenBtnLoc;
	private int[] mEraserBtnLoc;*/

    /*private Rect mScreenRect;
    private boolean isDiscard = false;*/
    private int mMode = MODE_PEN;
    private int mColor = Color.BLACK;
	private int mToolType = SpenSurfaceView.TOOL_SPEN;
	
	private FillTool fillTool;
	private StrokeTool strokeTool;
	private MagicWandTool magicWandTool;
	
	private LayerController layerController;
	private NavigatorController navigatorController;

    private Rect mScreenRect;
    private File staticFilePath;
	
    private boolean isDiscard = false;
    
    
    private Intent retIntent;
	private int position = 0;
    private String saveFilePathSPD;
    private String title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_design_editor);
		mContext = this;
				
		// Initialize Spen
		boolean isSpenFeatureEnabled = false;
		Spen spenPackage = new Spen();
		try {
			spenPackage.initialize(this);
			isSpenFeatureEnabled = spenPackage.isFeatureEnabled(Spen.DEVICE_PEN);
		} catch (SsdkUnsupportedException e) {
			if (SDKUtils.processUnsupportedException(this, e) == true) {
				return;
			}
		} catch (Exception e1) {
			Toast.makeText(mContext, "Cannot initialize Spen.", Toast.LENGTH_SHORT).show();
			e1.printStackTrace();
			finish();
		}

		FrameLayout spenViewContainer = (FrameLayout) findViewById(R.id.spenViewContainer);
		RelativeLayout spenViewLayout = (RelativeLayout) findViewById(R.id.spenViewLayout);

		
		// Create PenSettingView
		mPenSettingView = new SpenSettingPenLayout(mContext, new String(), spenViewLayout);
		if (mPenSettingView == null) {
			Toast.makeText(mContext, "Cannot create new PenSettingView.", Toast.LENGTH_SHORT).show();
			finish();
		}
		// Create EraserSettingView
		mEraserSettingView = new SpenSettingEraserLayout(mContext, new String(), spenViewLayout);
		if (mEraserSettingView == null) {
			Toast.makeText(mContext, "Cannot create new EraserSettingView.", Toast.LENGTH_SHORT).show();
			finish();
		}
		// Create EraserSettingView
		mTextSettingView = new SpenSettingTextLayout(mContext, new String(), new HashMap<String, String>(), spenViewLayout);
		if (mTextSettingView == null) {
			Toast.makeText(mContext, "Cannot create new TextSettingView.", Toast.LENGTH_SHORT).show();
			finish();
		}
		mSelectionSettingView = new SpenSettingSelectionLayout(mContext, new String(), spenViewLayout);
        if (mSelectionSettingView == null) {
            Toast.makeText(mContext, "Cannot create new SelectionSettingView.", Toast.LENGTH_SHORT).show();
            finish();
        }
		spenViewContainer.addView(mPenSettingView);
		spenViewContainer.addView(mEraserSettingView);
		spenViewContainer.addView(mTextSettingView);
		spenViewContainer.addView(mSelectionSettingView);

		mColorSettingView = (FrameLayout)findViewById(R.id.colorSettingView);
		mColorSettingView.setVisibility(View.GONE);
		mColorSettingView.setOnTouchListener(mColorSettingViewClickListener);
		mColorSelectionView = (ImageView)findViewById(R.id.colorSelectionView);
		mColorSelectorView = (ImageView)findViewById(R.id.colorSelectorView);
		mFillToolSettingView = (FrameLayout)findViewById(R.id.fillToolSettingView);
		mStrokeToolSettingView = (FrameLayout)findViewById(R.id.strokeToolSettingView);
		mMagicWandToolSettingView = (FrameLayout)findViewById(R.id.magicWandToolSettingView);
		mLayerSettingView = (FrameLayout)findViewById(R.id.layerSettingView);
		
		mColorSelectorView.setX(-22.0f);
		mColorSelectorView.setY(70.0f);
		
		// Create SpenSurfaceView
		mSpenSurfaceView = new SpenSurfaceView(mContext);
		if (mSpenSurfaceView == null) {
			Toast.makeText(mContext, "Cannot create new SpenSurfaceView.", Toast.LENGTH_SHORT).show();
			finish();
		}
		
		spenViewLayout.addView(mSpenSurfaceView);
		mPenSettingView.setCanvasView(mSpenSurfaceView);
		mEraserSettingView.setCanvasView(mSpenSurfaceView);
		mTextSettingView.setCanvasView(mSpenSurfaceView);
        mSelectionSettingView.setCanvasView(mSpenSurfaceView);
        mSettingView = (LinearLayout)findViewById(R.id.settingIconList);
		mToolBoxView = (LinearLayout)findViewById(R.id.toolBox);
		
		// Get the dimension of the device screen.
		Display display = getWindowManager().getDefaultDisplay();		
        mScreenRect = new Rect();
        display.getRectSize(mScreenRect);

		// set file path
		String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/quadrah/designeditor/";
		staticFilePath = new File(filePath);
		if (!staticFilePath.exists()) {
			if (!staticFilePath.mkdirs()) {
				Log.e("Image_File_Controller", "Save Path Creation Error");
				return;
			}
		}
        
		retIntent = getIntent();
	    
	    Bundle extras = getIntent().getExtras();
	    
	    if(extras != null){
	    	int dpi_width = 0;
	    	int dpi_height = 0;
	    	
	    	position = retIntent.getIntExtra("position", position);
	    	
	    	title = retIntent.getStringExtra("title");	    	
	    	dpi_width = retIntent.getIntExtra("dpi_width", dpi_width);
	    	dpi_height = retIntent.getIntExtra("dpi_height", dpi_height);
	    	saveFilePathSPD = retIntent.getStringExtra("SPD");
	    		    	
	    	if( dpi_height != 0 && dpi_height != 0 ) {
	    		mScreenRect.set(0, 0, dpi_width, dpi_height);
	    	}
	    		
	    	
	    	
	    	
	    	Log.d("saveFilePathSPD", " : " + saveFilePathSPD);
	    }
	    
	    if( saveFilePathSPD == null ) {
			// Create SpenNoteDoc
			try {
				mSpenNoteDoc = new SpenNoteDoc(mContext, mScreenRect.width(), mScreenRect.height());
			} catch (IOException e) {
				Toast.makeText(mContext, "Cannot create new NoteDoc", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
				finish();
			} catch (Exception e) {
				e.printStackTrace();
				finish();
			}		
			mSpenPageDoc = mSpenNoteDoc.appendPage();
	    
	    }
	    else {
    		try {
    			// Create NoteDoc with the selected file.
    			mSpenNoteDoc = new SpenNoteDoc(mContext, saveFilePathSPD, mScreenRect.width(), SpenNoteDoc.MODE_WRITABLE);
    			if (mSpenNoteDoc.getPageCount() == 0) {
    				mSpenPageDoc = mSpenNoteDoc.appendPage();
    			} else {
    				mSpenPageDoc = mSpenNoteDoc.getPage(mSpenNoteDoc.getLastEditedPageIndex());
    			}
    			//mSpenSurfaceView.update();
    			Toast.makeText(mContext, "Successfully loaded noteFile.", Toast.LENGTH_SHORT).show();
    		} catch (IOException e) {
    			Toast.makeText(mContext, "Cannot open this file.", Toast.LENGTH_LONG).show();
    		} catch (SpenUnsupportedTypeException e) {
    			Toast.makeText(mContext, "This file is not supported.", Toast.LENGTH_LONG).show();
    		} catch (SpenInvalidPasswordException e) {
    			Toast.makeText(mContext, "This file is locked by a password.", Toast.LENGTH_LONG).show();
    		} catch (SpenUnsupportedVersionException e) {
    			Toast.makeText( mContext, "This file is the version that does not support.", Toast.LENGTH_LONG).show();
    		} catch (Exception e) {
    			Toast.makeText(mContext, "Failed to load noteDoc.", Toast.LENGTH_LONG).show();
    		}
	    }
		
		// Add a Page to NoteDoc, get an instance, and set it to the member
		// variable.
		mSpenPageDoc.setBackgroundColor(0xFFFFFFFF);
		mSpenSurfaceView.setMaxZoomRatio(4.0f);
		mSpenSurfaceView.setMinZoomRatio(0.7f);
		
		mSpenPageDoc.clearHistory();
		// Set PageDoc to View
		mSpenSurfaceView.setPageDoc(mSpenPageDoc, true);

		initSettingInfo();
		// Register the listener
		mSpenSurfaceView.setTouchListener(mPenTouchListener);
		mSpenSurfaceView.setColorPickerListener(mColorPickerListener);
		mSpenPageDoc.setHistoryListener(mHistoryListener);
		mSpenPageDoc.setObjectListener(mObjectListener);
		mEraserSettingView.setEraserListener(mEraserListener);
				
        mSpenSurfaceView.setTextChangeListener(mTextChangeListener);
        mSpenSurfaceView.setControlListener(mControlListener);
        mSpenSurfaceView.setSelectionChangeListener(mSelectionListener);
        mSpenSurfaceView.setPenChangeListener(mPenChangeListener);
        
        mSpenSurfaceView.setZoomListener(mZoomListener);

		// Set a button
        mCurrentToolBtn = (ImageView) findViewById(R.id.currentTool);
        mCurrentToolBtn.setOnClickListener(mCurrentToolBtnClickListener);
        
        mCurrentColorBtn = (ImageView) findViewById(R.id.currentColor);
        mCurrentColorBtn.setOnClickListener(mCurrentColorBtnClickListener);
        mCurrentColorBtn.setBackgroundColor(mColor);
        
		mPenBtn = (ImageView) findViewById(R.id.penBtn);
		mPenBtn.setOnClickListener(mPenBtnClickListener);

		mEraserBtn = (ImageView) findViewById(R.id.eraserBtn);
		mEraserBtn.setOnClickListener(mEraserBtnClickListener);
		
		mFillToolBtn = (ImageView) findViewById(R.id.fillToolBtn);
		mFillToolBtn.setOnClickListener(mFillToolBtnClickListener);
		
		mStrokeObjBtn = (ImageView) findViewById(R.id.strokeObjBtn);
		mStrokeObjBtn.setOnClickListener(mStrokeObjBtnClickListener);
		
		mImgObjBtn = (ImageView) findViewById(R.id.imgObjBtn);
		mImgObjBtn.setOnClickListener(mImgObjBtnClickListener);
		
		mTextObjBtn = (ImageView) findViewById(R.id.textObjBtn);
		mTextObjBtn.setOnClickListener(mTextObjBtnClickListener);

		mMagicWandToolBtn = (ImageView) findViewById(R.id.magicWandToolBtn);
		mMagicWandToolBtn.setOnClickListener(mMagicWandToolBtnClickListener);
		
		mSelectionBtn = (ImageView) findViewById(R.id.selectionBtn);
		mSelectionBtn.setOnClickListener(mSelectionBtnClickListener);

		mLayerBtn = (ImageView) findViewById(R.id.layerBtn);
		mLayerBtn.setOnClickListener(mLayerBtnClickListener);
		
		mSettingBtn = (ImageView) findViewById(R.id.settingBtn);
		mSettingBtn.setOnClickListener(mSettingBtnClickListener);
		
		mSaveBtn = (ImageView)findViewById(R.id.saveBtn);
		mSaveBtn.setOnClickListener(saveBtnClickListener);
		
		mUndoBtn = (ImageView) findViewById(R.id.undoBtn);
		mUndoBtn.setOnClickListener(undoNredoBtnClickListener);
		mUndoBtn.setEnabled(mSpenPageDoc.isUndoable());

		mRedoBtn = (ImageView) findViewById(R.id.redoBtn);
		mRedoBtn.setOnClickListener(undoNredoBtnClickListener);
		mRedoBtn.setEnabled(mSpenPageDoc.isRedoable());

		mInitZoomBtn = (ImageView) findViewById(R.id.initZoomBtn);
		mInitZoomBtn.setOnClickListener(initZoomBtnClickListener);

		mNavigatorBtn = (ImageView) findViewById(R.id.navigatorBtn);
		mNavigatorBtn.setOnClickListener(navigatorBtnClickListener);

		mToolBoxBtn = (ImageView) findViewById(R.id.toolBoxBtn);
		mToolBoxBtn.setOnClickListener(toolBoxBtnClickListener);
				
		selectButton(mPenBtn);
		
		fillTool = new FillTool(mSpenSurfaceView, mSpenPageDoc, mFillToolSettingView, (ImageView) findViewById(R.id.fillToolSlider));
		
		strokeTool = new StrokeTool(mSpenSurfaceView, mSpenPageDoc, mPenSettingView, mStrokeToolSettingView);
		
		magicWandTool = new MagicWandTool(mSpenSurfaceView, mSpenPageDoc, mMagicWandToolSettingView, (ImageView) findViewById(R.id.magicWandToolSlider));
		
		//layerController = new LayerController(mSpenSurfaceView, mSpenPageDoc, (ImageView) findViewById(R.id.layerImage));

		mNavigatorView = (FrameLayout) findViewById(R.id.navigatorView);
		mNavigatorView.setOnTouchListener(navigatorClickListener);
		navigatorController = new NavigatorController(mSpenSurfaceView, mNavigatorView, (ImageView) findViewById(R.id.navigatorImage), (ImageView) findViewById(R.id.navigatorPan));
    	
		if (isSpenFeatureEnabled == false) {
			mToolType = SpenSurfaceView.TOOL_FINGER;
			mSpenSurfaceView.setToolTypeAction(mToolType, SpenSurfaceView.ACTION_STROKE);
			Toast.makeText(mContext, "Device does not support Spen. \n You can draw stroke by finger.", Toast.LENGTH_SHORT).show();
		}
		
	}
		
	private void initSettingInfo() {
		/*com.samsung.android.sdk.pen.pen.preload.Beautify;
        com.samsung.android.sdk.pen.pen.preload.NativePen;
        com.samsung.android.sdk.pen.pen.preload.RedPen;*/
        // Initialize Pen settings
        List<SpenPenInfo> penList = new ArrayList<SpenPenInfo>();
        SpenPenManager penManager = new SpenPenManager(mContext);
        penList = penManager.getPenInfoList();
        SpenSettingPenInfo penInfo = new SpenSettingPenInfo();
        
        /////// can save last tool and color
        // set basic tool "Brush"
        for (SpenPenInfo info : penList) {
            if (info.name.equalsIgnoreCase("Brush")) {
                penInfo.name = info.className;
                break;
            }
        }
        
		penInfo.color = Color.BLACK;
		mColor = Color.BLACK;
		penInfo.size = 10;
		mSpenSurfaceView.setPenSettingInfo(penInfo);
		mPenSettingView.setInfo(penInfo);

		// Initialize Eraser settings
		SpenSettingEraserInfo eraserInfo = new SpenSettingEraserInfo();
		eraserInfo.size = 30;
		mSpenSurfaceView.setEraserSettingInfo(eraserInfo);
		mEraserSettingView.setInfo(eraserInfo);
		
        // Initialize text settings
        SpenSettingTextInfo textInfo = new SpenSettingTextInfo();
        textInfo.size = Math.round(8.5 * mSpenSurfaceView.getCanvasWidth() / 360);
        mSpenSurfaceView.setTextSettingInfo(textInfo);
        mTextSettingView.setInfo(textInfo);
	}

    private final SpenTouchListener mPenTouchListener = new SpenTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
        	//mSpenSurfaceView.update();
        	//mSpenSurfaceView.updateScreen();
        	
        	//Log.e("onTouchListener", "event " + event.getX() + "  " + event.getY());
        	//Log.e("onTouchListener", "pan   " + mSpenSurfaceView.getPan().x + "  " + mSpenSurfaceView.getPan().y);
        	
        	navigatorController.refreshHandle(event.getAction());
        	
            if (event.getAction() == MotionEvent.ACTION_UP && event.getToolType(0) == mToolType) {
                // Check if the control is created.
                SpenControlBase control = mSpenSurfaceView.getControl();
                if (control == null) {
                    // When Pen touches the display while it is in Add ObjectImage mode
                    if (mMode == MODE_IMG_OBJ) {
                        addImgObject(event.getX(), event.getY(), 1);

                        return true;

                        // When Pen touches the display while it is in Add ObjectTextBox mode
                    } else if (mSpenSurfaceView.getToolTypeAction(mToolType) == SpenSurfaceView.ACTION_TEXT) {
                        SpenObjectTextBox obj = addTextObject(event.getX(), event.getY(), null);
                        mSpenPageDoc.selectObject(obj);
                        mSpenSurfaceView.update();

                        return true;

                        // When Pen touches the display while it is in Add ObjectStroke mode
                    } else if (mMode == MODE_STROKE_OBJ) {
                    	strokeTool.drawLine(event.getX(), event.getY());
                        
                        return true;
                    } else if (mMode == MODE_FILLTOOL) {
                    	fillTool.fillArea(event.getX(), event.getY());
                    	                        
                        return true;
                    } else if (mMode == MODE_MAGICWAND) {
                    	magicWandTool.selectArea(event.getX(), event.getY());
                    	                        
                        return true;
                    }
                }
            }
            return false;
        }
    };
	
    private final ObjectListener mObjectListener = new ObjectListener() {
		@Override
		public void onObjectAdded(SpenPageDoc spenPageDoc, ArrayList<SpenObjectBase> objectList, int arg2) {
			//set int extra data in mlayer number 
			/*for(int i = 0 ; i < objectList.size() ; i++ ) {
				layerController.addObject(objectList.get(i));
			}*/
		}

		@Override
		public void onObjectChanged(SpenPageDoc arg0, SpenObjectBase arg1, int arg2) {
			//Log.e("onObjectChanged", "----------------" + (arg1.getType() == SpenObjectBase.TYPE_CONTAINER));
		}

		@Override
		public void onObjectRemoved(SpenPageDoc spenPageDoc, ArrayList<SpenObjectBase> objectList, int arg2) {
			//set int extra data in mlayer number 
			/*for(int i = 0 ; i < objectList.size() ; i++ ) {
				layerController.removeObject(objectList.get(i));
			}*/
		}
	};
    	
	private final OnClickListener mCurrentToolBtnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			closeSettingView();
			switch(mMode) {
				case MODE_PEN:
					mPenSettingView.setPosition(33, 128);
					// If PenSettingView is open, close it.
					if ( mPenSettingView.getVisibility() == View.VISIBLE ) {
						mPenSettingView.setVisibility(View.GONE);
						// If PenSettingView is not open, open it.
					} else {
						mPenSettingView.setViewMode(SpenSettingPenLayout.VIEW_MODE_EXTENSION);
						mPenSettingView.setVisibility(View.VISIBLE);
					}
				break;
				case MODE_ERASER:
					mEraserSettingView.setPosition(33, 128);
					// If EraserSettingView is open, close it.
					if (mEraserSettingView.getVisibility() == View.VISIBLE ) {
						mEraserSettingView.setVisibility(View.GONE);
						// If EraserSettingView is not open, open it.
					} else {
						mEraserSettingView.setViewMode(SpenSettingEraserLayout.VIEW_MODE_NORMAL);
						mEraserSettingView.setVisibility(View.VISIBLE);
					}
				break;
				case MODE_FILLTOOL:
					mFillToolSettingView.setX(33);
					mFillToolSettingView.setY(128);
		            fillTool.setColor(mColor);
					if (mFillToolSettingView.getVisibility() == View.VISIBLE ) {
						mFillToolSettingView.setVisibility(View.GONE);
					} else {
						mFillToolSettingView.setVisibility(View.VISIBLE);
					}
				break;
				case MODE_OBJSEC:
					mSelectionSettingView.setX(33);
					mSelectionSettingView.setY(128);
					if (mSelectionSettingView.getVisibility() == View.VISIBLE ) {
						mSelectionSettingView.setVisibility(View.GONE);
					} else {
						mSelectionSettingView.setVisibility(View.VISIBLE);
					}
				break;
				case MODE_STROKE_OBJ:
		            strokeTool.setColor(mColor);
				break;
				case MODE_IMG_OBJ:
	                changeImgObj();
				break;
				case MODE_TEXT_OBJ:
					mTextSettingView.setPosition(33, 128);
					if (mTextSettingView.getVisibility() == View.VISIBLE ) {
						mTextSettingView.setVisibility(View.GONE);
					} else {
						mTextSettingView.setViewMode(SpenSettingEraserLayout.VIEW_MODE_NORMAL);
						mTextSettingView.setVisibility(View.VISIBLE);
					}
				break;
				case MODE_SELECTION:
					mSelectionSettingView.setPosition(33, 128);
					if (mSelectionSettingView.getVisibility() == View.VISIBLE ) {
						mSelectionSettingView.setVisibility(View.GONE);
					} else {
						mSelectionSettingView.setViewMode(SpenSettingEraserLayout.VIEW_MODE_NORMAL);
						mSelectionSettingView.setVisibility(View.VISIBLE);
					}
				break;
				case MODE_MAGICWAND:
					mMagicWandToolSettingView.setX(33);
					mMagicWandToolSettingView.setY(128);
					if (mMagicWandToolSettingView.getVisibility() == View.VISIBLE ) {
						mMagicWandToolSettingView.setVisibility(View.GONE);
					} else {
						mMagicWandToolSettingView.setVisibility(View.VISIBLE);
					}
				break;
			}
		}
	};
	
	private final OnClickListener mCurrentColorBtnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if( mColorSettingView.getVisibility() == View.VISIBLE ) {
				closeSettingView();
				mColorSettingView.setVisibility(View.GONE);
			}
			else {
				closeSettingView();
				mColorSettingView.setVisibility(View.VISIBLE);
			}
		}
	};
	
	private final OnTouchListener mColorSettingViewClickListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			float x, y;
			
			x = event.getX();
			y = event.getY();

			if( x < 3 ) x = 3;
			if( y < 3 ) y = 3;
			if( x > mColorSelectionView.getWidth() - 4 ) x = mColorSelectionView.getWidth() - 3;
			if( y > mColorSelectionView.getHeight() - 4 ) y = mColorSelectionView.getHeight() - 3;
			
			//get color of image view
			mColorSelectionView.buildDrawingCache();
			Bitmap csvbitmap = mColorSelectionView.getDrawingCache(); 

			mColorSelectorView.setX(x - 28);
			mColorSelectorView.setY(y - 28);

			//get pointed pixel color
			int pixel = csvbitmap.getPixel((int)x, (int)y);

			mColor = pixel;
			//change current collor btn
			mCurrentColorBtn.setBackgroundColor(mColor);

			//if( mMode == MODE_PEN ) {
				//set pen color
				SpenSettingPenInfo penInfo = mPenSettingView.getInfo();
				penInfo.color = mColor;	
				mPenSettingView.setInfo(penInfo);
			//}
			//else if( mMode == MODE_FILLTOOL ) {
				fillTool.setColor(mColor);
			//}
			//else if( mMode == MODE_STROKE_OBJ ) {
				strokeTool.setColor(mColor);
			//}

			//TODO : save old color
				
			return true;
		}
	};

	private final OnClickListener mPenBtnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			mPenSettingView.setPosition((int)(mPenBtn.getX() + mPenBtn.getWidth()*2 + 8), (int)(mPenBtn.getY() + 128));
            mCurrentColorBtn.setVisibility(View.VISIBLE);
            mSpenSurfaceView.closeControl();

			// When Spen is in stroke (pen) mode
			if (mSpenSurfaceView.getToolTypeAction(mToolType) == SpenSurfaceView.ACTION_STROKE) {
				// If PenSettingView is open, close it.
				if (mPenSettingView.isShown()) {
					mPenSettingView.setVisibility(View.GONE);
					// If PenSettingView is not open, open it.
				} else {
					closeSettingView();
					mPenSettingView.setViewMode(SpenSettingPenLayout.VIEW_MODE_EXTENSION);
					mPenSettingView.setVisibility(View.VISIBLE);
				}
				// If Spen is not in stroke (pen) mode, change it to stroke
				// mode.
			} else {
                mMode = MODE_PEN;
				selectButton(mPenBtn);
				mSpenSurfaceView.setToolTypeAction(mToolType, SpenSurfaceView.ACTION_STROKE);
				mCurrentToolBtn.setImageResource(R.drawable.selector_pen);
			}
		}
	};
	
	private final SpenPenChangeListener mPenChangeListener = new SpenPenChangeListener() {
		@Override
		public void onChanged(SpenSettingPenInfo arg0) {
			SpenSettingPenInfo penInfo = mPenSettingView.getInfo();
			mColor = penInfo.color;
			mCurrentColorBtn.setBackgroundColor(mColor);
			
			fillTool.setColor(mColor);
			strokeTool.setColor(mColor);
		}
	};
	
	private final OnClickListener mEraserBtnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
            mCurrentColorBtn.setVisibility(View.GONE);
            mSpenSurfaceView.closeControl();

            mEraserSettingView.setPosition((int)(mEraserBtn.getX() + mEraserBtn.getWidth()*2 + 8), (int)(mEraserBtn.getY() + 144));
			// When Spen is in eraser mode
			if (mSpenSurfaceView.getToolTypeAction(mToolType) == SpenSurfaceView.ACTION_ERASER) {
				// If EraserSettingView is open, close it.
				if (mEraserSettingView.isShown()) {
					mEraserSettingView.setVisibility(View.GONE);
					// If EraserSettingView is not open, open it.
				} else {
					mEraserSettingView.setViewMode(SpenSettingEraserLayout.VIEW_MODE_NORMAL);
					closeSettingView();
					mEraserSettingView.setVisibility(View.VISIBLE);
				}
				// If Spen is not in eraser mode, change it to eraser mode.
			} else {
				mMode = MODE_ERASER;
				selectButton(mEraserBtn);
				mSpenSurfaceView.setToolTypeAction(mToolType, SpenSurfaceView.ACTION_ERASER);
				mCurrentToolBtn.setImageResource(R.drawable.selector_eraser);
			}
		}
	};

	private final OnClickListener mFillToolBtnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			mFillToolSettingView.setX(mFillToolBtn.getX() + mFillToolBtn.getWidth()*2 + 8);
			mFillToolSettingView.setY(mFillToolBtn.getY() + 200);
            mSpenSurfaceView.closeControl();

            mCurrentColorBtn.setVisibility(View.VISIBLE);
            strokeTool.setColor(mColor);
            
			// When Spen is in Fill Tool mode
			if (mMode == MODE_FILLTOOL) {
				// If FillToolSettingView is open, close it.
				if (mFillToolSettingView.isShown()) {
					mFillToolSettingView.setVisibility(View.GONE);
					// If FillToolSettingView is not open, open it.
				} else {
					closeSettingView();
					mFillToolSettingView.setVisibility(View.VISIBLE);
				}
				// If Spen is not in fill tool mode, change it to fill tool mode.
			} else {
				closeSettingView();
				mMode = MODE_FILLTOOL;
				selectButton(mFillToolBtn);
				mCurrentToolBtn.setImageResource(R.drawable.selector_filltool);
	            mSpenSurfaceView.setToolTypeAction(mToolType, SpenSurfaceView.ACTION_NONE);
			}
		}
	};
	
    private final OnClickListener mStrokeObjBtnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            mSpenSurfaceView.closeControl();

            mCurrentColorBtn.setVisibility(View.VISIBLE);
            strokeTool.setColor(mColor);
            
			closeSettingView();
            mMode = MODE_STROKE_OBJ;
            selectButton(mStrokeObjBtn);
			mCurrentToolBtn.setImageResource(R.drawable.selector_gesture);
            mSpenSurfaceView.setToolTypeAction(mToolType, SpenSurfaceView.ACTION_NONE);
        }
    };
	
    private final OnClickListener mImgObjBtnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            mSpenSurfaceView.closeControl();

            mCurrentColorBtn.setVisibility(View.GONE);
            if (mMode == MODE_IMG_OBJ) {
                closeSettingView();
                changeImgObj();
            } else {
                mMode = MODE_IMG_OBJ;
                selectButton(mImgObjBtn);
                mSpenSurfaceView.setToolTypeAction(mToolType, SpenSurfaceView.ACTION_NONE);
    			mCurrentToolBtn.setImageResource(R.drawable.selector_image);
            }
        }
    };

	private final OnClickListener mTextObjBtnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			mTextSettingView.setPosition((int)(mTextObjBtn.getX() + mTextObjBtn.getWidth()*2 + 8), (int)(mTextObjBtn.getY()));
            
            mCurrentColorBtn.setVisibility(View.VISIBLE);
            strokeTool.setColor(mColor);
            
            mSpenSurfaceView.closeControl();
			// When Spen is in text mode
			if (mSpenSurfaceView.getToolTypeAction(mToolType) == SpenSurfaceView.ACTION_TEXT) {
				// If other SettingView is open, close it.
				 if (mPenSettingView.isShown()) {
					mPenSettingView.setVisibility(View.GONE);
				} else if (mEraserSettingView.isShown()) {
					mEraserSettingView.setVisibility(View.GONE);
				} else if (mTextSettingView.isShown()) {
                    mTextSettingView.setVisibility(View.GONE);
                } else {
					mTextSettingView.setViewMode(SpenSettingTextLayout.VIEW_MODE_NORMAL);
	                closeSettingView();
					mTextSettingView.setVisibility(View.VISIBLE);
				}
			} else {
				// If Spen is not in text mode, change it to text mode.
                mMode = MODE_TEXT_OBJ;
				selectButton(mTextObjBtn);
				mSpenSurfaceView.setToolTypeAction(mToolType, SpenSurfaceView.ACTION_TEXT);
    			mCurrentToolBtn.setImageResource(R.drawable.selector_text);
			}
		}
	};

    private final OnClickListener mSelectionBtnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
        	mSelectionSettingView.setPosition((int)(mSelectionBtn.getX() + mSelectionBtn.getWidth()*2 + 8), (int)(mSelectionBtn.getY() + 200));
            mSpenSurfaceView.closeControl();
            mCurrentColorBtn.setVisibility(View.GONE);
            // When Spen is in selection mode
            if (mSpenSurfaceView.getToolTypeAction(mToolType) == SpenSurfaceView.ACTION_SELECTION) {
                // If SelectionSettingView is open, close it.
                if (mSelectionSettingView.isShown()) {
                    mSelectionSettingView.setVisibility(View.GONE);
                    // If SelectionSettingView is not open, open it.
                } else {
                    mSelectionSettingView.setViewMode(SpenSettingSelectionLayout.VIEW_MODE_NORMAL);
                    closeSettingView();
                    mSelectionSettingView.setVisibility(View.VISIBLE);
                }
                // If Spen is not in selection mode, change it to selection mode.
            } else {
                mMode = MODE_SELECTION;
                selectButton(mSelectionBtn);
                mSpenSurfaceView.setToolTypeAction(mToolType, SpenSurfaceView.ACTION_SELECTION);
    			mCurrentToolBtn.setImageResource(R.drawable.selector_singleselect);
            }
        }
    };

	private final OnClickListener mMagicWandToolBtnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			mMagicWandToolSettingView.setX(mMagicWandToolBtn.getX() + mMagicWandToolBtn.getWidth()*2 + 8);
			mMagicWandToolSettingView.setY(mMagicWandToolBtn.getY() + 200);
            mSpenSurfaceView.closeControl();

            mCurrentColorBtn.setVisibility(View.GONE);
            
			// When Spen is in Fill Tool mode
			if (mMode == MODE_MAGICWAND) {
				// If FillToolSettingView is open, close it.
				if (mMagicWandToolSettingView.isShown()) {
					mMagicWandToolSettingView.setVisibility(View.GONE);
					// If FillToolSettingView is not open, open it.
				} else {
					closeSettingView();
					mMagicWandToolSettingView.setVisibility(View.VISIBLE);
				}
				// If Spen is not in fill tool mode, change it to fill tool mode.
			} else {
				closeSettingView();
				mMode = MODE_MAGICWAND;
				selectButton(mMagicWandToolBtn);
				mCurrentToolBtn.setImageResource(R.drawable.selector_magic_wand);
	            mSpenSurfaceView.setToolTypeAction(mToolType, SpenSurfaceView.ACTION_NONE);
			}
		}
	};
	
    private final OnClickListener mLayerBtnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
        	mLayerSettingView.setX(mLayerBtn.getX() + mLayerBtn.getWidth()*2 + 8);
        	mLayerSettingView.setY(mLayerBtn.getY() + 200);
            mSpenSurfaceView.closeControl();

            mCurrentColorBtn.setVisibility(View.GONE);
        	
        	navigatorController.refreshNavigator();
        	//layerController.refreshLayerMinimap();
        	
        }
    };

	private final OnClickListener mSettingBtnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			closeSettingView();
			mSettingView.setX(mSettingBtn.getX() + mSettingBtn.getWidth()*2 + 8);
			mSettingView.setY(mSettingBtn.getY() + 250);
			
            mSpenSurfaceView.closeControl();
			selectButton(mSettingBtn);

			if( mSettingView.getVisibility() == View.GONE) {
				mSettingView.setVisibility(View.VISIBLE);
			}
			else {
				mSettingView.setVisibility(View.GONE);
			}
		}
	};
	
	private final OnClickListener undoNredoBtnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (mSpenPageDoc == null) {
				return;
			}
			// Undo button is clicked.
			if (v.equals(mUndoBtn)) {
				if (mSpenPageDoc.isUndoable()) {
					HistoryUpdateInfo[] userData = mSpenPageDoc.undo();
					mSpenSurfaceView.updateUndo(userData);
				}
				// Redo button is clicked.
			} else if (v.equals(mRedoBtn)) {
				if (mSpenPageDoc.isRedoable()) {
					HistoryUpdateInfo[] userData = mSpenPageDoc.redo();
					mSpenSurfaceView.updateRedo(userData);
				}
			}

        	navigatorController.refreshNavigator();
        	//layerController.refreshLayerMinimap();
		}
	};

	private final OnClickListener initZoomBtnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			mSpenSurfaceView.setZoom(mSpenSurfaceView.getWidth() / 2, mSpenSurfaceView.getHeight() / 2, 1.0f);
		}
	};

	private final OnClickListener navigatorBtnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if( mNavigatorView.getVisibility() == View.GONE)
				mNavigatorView.setVisibility(View.VISIBLE);
			else
				mNavigatorView.setVisibility(View.GONE);
		}
	};

	private final OnClickListener toolBoxBtnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			closeSettingView();

			if( mToolBoxView.getVisibility() == View.GONE) {
				mToolBoxView.setVisibility(View.VISIBLE);
			}
			else {
				mToolBoxView.setVisibility(View.GONE);
			}
		}
	};
	
	private final OnTouchListener navigatorClickListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			navigatorController.setPanPosition((int)event.getX(), (int)event.getY());
			return true;
		}
	};
	
	private SpenColorPickerListener mColorPickerListener = new SpenColorPickerListener() {
		@Override
		public void onChanged(int color, int x, int y) {
			// Set the color from the Color Picker to the setting view.
			if (mPenSettingView != null) {
				SpenSettingPenInfo penInfo = mPenSettingView.getInfo();
				penInfo.color = color;
				mColor = color;
				mCurrentColorBtn.setBackgroundColor(mColor);
				mPenSettingView.setInfo(penInfo);
				strokeTool.setColor(mColor);
			}
		}
	};

	private EventListener mEraserListener = new EventListener() {
		@Override
		public void onClearAll() {
			//TODO : layer clear or all docs clear?????
			
			// ClearAll button action routines of EraserSettingView
			mSpenPageDoc.removeAllObject();
			mSpenSurfaceView.update();
        	navigatorController.refreshNavigator();
        	//layerController.refreshLayerMinimap();
		}
	};

	private HistoryListener mHistoryListener = new HistoryListener() {
		@Override
		public void onCommit(SpenPageDoc page) {
		}

		@Override
		public void onUndoable(SpenPageDoc page, boolean undoable) {
			// Enable or disable the button according to the availability of
			// undo.
			mUndoBtn.setEnabled(undoable);
		}

		@Override
		public void onRedoable(SpenPageDoc page, boolean redoable) {
			// Enable or disable the button according to the availability of
			// redo.
			mRedoBtn.setEnabled(redoable);
		}
	};

    private void changeImgObj() {
    	callGalleryForInputImage(REQUEST_CODE_ATTACH_IMAGE);
    }

    private void callGalleryForInputImage(int nRequestCode) {
        // Get an image from Gallery.
        try {
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, nRequestCode);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(mContext, "Cannot find gallery.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void addImgObject(float x, float y, float scale) {
        SpenObjectImage imgObj = new SpenObjectImage();
        Bitmap imageBitmap;
        // Set a bitmap file to ObjectImage.
        // If there is a file attached, set it to ObjectImage.
        if (mSpenNoteDoc.hasAttachedFile(ATTACH_IMAGE_KEY)) {
            imageBitmap = BitmapFactory.decodeFile(mSpenNoteDoc.getAttachedFile(ATTACH_IMAGE_KEY));
            // If there is no file attached, set the launcher icon to ObjectImage.
        } else {
            imageBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
        }
        imgObj.setImage(imageBitmap);

        // Set the location to insert ObjectImage and add it to PageDoc.
        float imgWidth = imageBitmap.getWidth();
        float imgHeight = imageBitmap.getHeight();
        RectF rect = getRealPoint(x, y, imgWidth * scale, imgHeight * scale);
        imgObj.setRect(rect, true);
        mSpenPageDoc.appendObject(imgObj);
        mSpenSurfaceView.update();

        imageBitmap.recycle();
    }

    private SpenObjectTextBox addTextObject(float x, float y, String str) {
        // Set the location to insert ObjectTextBox and add it to PageDoc.
        SpenObjectTextBox textObj = new SpenObjectTextBox();
        RectF rect = getRealPoint(x, y, 0, 0);
        rect.right += 350;
        rect.bottom += 150;
        textObj.setRect(rect, true);
        textObj.setText(str);
        mSpenPageDoc.appendObject(textObj);
        mSpenSurfaceView.update();

        return textObj;
    }

    private RectF getRealPoint(float x, float y, float width, float height) {
        float panX = mSpenSurfaceView.getPan().x;
        float panY = mSpenSurfaceView.getPan().y;
        float zoom = mSpenSurfaceView.getZoomRatio();
        width *= zoom;
        height *= zoom;
        RectF realRect = new RectF();
        realRect.set((x - width / 2) / zoom + panX, (y - height / 2) / zoom + panY, (x + width / 2) / zoom + panX, (y + height / 2) / zoom + panY);
        return realRect;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(mContext, "Cannot find the image", Toast.LENGTH_SHORT).show();
                return;
            }

            // Process a request to attach an image.
            if (requestCode == REQUEST_CODE_ATTACH_IMAGE) {
                // Get the image's URI and get the file path to attach it.
                Uri imageFileUri = data.getData();
                Cursor cursor = getContentResolver().query(Uri.parse(imageFileUri.toString()), null, null, null, null);
                cursor.moveToNext();
                String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));

                mSpenNoteDoc.attachFile(ATTACH_IMAGE_KEY, imagePath);
            }
        }
    }

	private void selectButton(View v) {
		// Enable or disable the button according to the current mode.
		mPenBtn.setSelected(false);
		mEraserBtn.setSelected(false);
        mFillToolBtn.setSelected(false);
        mStrokeObjBtn.setSelected(false);
        mImgObjBtn.setSelected(false);
        mTextObjBtn.setSelected(false);
        mSelectionBtn.setSelected(false);
        mMagicWandToolBtn.setSelected(false);
        
		v.setSelected(true);

		closeSettingView();
	}

    SpenTextChangeListener mTextChangeListener = new SpenTextChangeListener() {

        @Override
        public boolean onSelectionChanged(int arg0, int arg1) {
            return false;
        }

        @Override
        public void onMoreButtonDown(SpenObjectTextBox arg0) {
        }

        @Override
        public void onChanged(SpenSettingTextInfo info, int state) {
            if (mTextSettingView != null) {
                if (state == CONTROL_STATE_SELECTED) {
                    mTextSettingView.setInfo(info);
                }
            }
        }

        @Override
        public void onFocusChanged(boolean arg0) {

        }
    };

    private final SpenControlListener mControlListener = new SpenControlListener() {
        @Override
        public void onRotationChanged(float arg0, SpenObjectBase arg1) {
        }

        @Override
        public void onRectChanged(RectF arg0, SpenObjectBase arg1) {
        }

        @Override
        public void onObjectChanged(ArrayList<SpenObjectBase> arg0) {
        }

        @Override
        public boolean onMenuSelected(ArrayList<SpenObjectBase> objectList, int itemId) {
            SpenObjectContainer objContainer;
            SpenObjectBase object = objectList.get(0);
            switch (itemId) {
            // Remove the selected object.
            case CONTEXT_MENU_DELETE_ID:
                // mSpenPageDoc.removeSelectedObject();
                for (SpenObjectBase obj : objectList) {
                    mSpenPageDoc.removeObject(obj);
                }
                mSpenSurfaceView.closeControl();
                mSpenSurfaceView.update();
                break;

            // Group the objects.
            case CONTEXT_MENU_GROUP_ID:
                objContainer = mSpenPageDoc.groupObject(objectList, false);
                mSpenSurfaceView.closeControl();
                mSpenPageDoc.selectObject(objContainer);
                mSpenSurfaceView.update();
                break;

            // Ungroup the grouped objects.
            case CONTEXT_MENU_UNGROUP_ID:
                ArrayList<SpenObjectBase> objList = new ArrayList<SpenObjectBase>();
                for (SpenObjectBase selectedObj : objectList) {
                    if (selectedObj.getType() == SpenObjectBase.TYPE_CONTAINER) {
                        objContainer = (SpenObjectContainer) selectedObj;
                        for (SpenObjectBase obj : objContainer.getObjectList()) {
                            objList.add(obj);
                        }
                        mSpenPageDoc.ungroupObject((SpenObjectContainer) selectedObj, false);
                    }
                }
                mSpenSurfaceView.closeControl();
                mSpenPageDoc.selectObject(objList);
                mSpenSurfaceView.update();
                break;

            // Send the selected object to the back.
            /*case CONTEXT_MENU_MOVE_TO_BOTTOM_ID:
                mSpenPageDoc.moveObjectIndex(object, -mSpenPageDoc.getObjectIndex(object), true);
                mSpenSurfaceView.update();
                break;*/

            // Send the selected object backward by an index.
            case CONTEXT_MENU_MOVE_TO_BACKWARD_ID:
                if (mSpenPageDoc.getObjectIndex(object) > 0) {
                    mSpenPageDoc.moveObjectIndex(object, -1, true);
                    mSpenSurfaceView.update();
                }
                break;

            // Bring the selected object forward by an index.
            case CONTEXT_MENU_MOVE_TO_FORWARD_ID:
                if (mSpenPageDoc.getObjectIndex(object) < mSpenPageDoc.getObjectCount(true) - 1) {
                    mSpenPageDoc.moveObjectIndex(object, 1, true);
                    mSpenSurfaceView.update();
                }
                break;

            // Bring the selected object to the front.
            /*case CONTEXT_MENU_MOVE_TO_TOP_ID:
                mSpenPageDoc.moveObjectIndex(object, mSpenPageDoc.getObjectCount(true) - 1 - mSpenPageDoc.getObjectIndex(object), true);
                mSpenSurfaceView.update();
                break;*/
            }

            return true;
        }

        @Override
        public boolean onCreated(ArrayList<SpenObjectBase> objectList, ArrayList<Rect> relativeRectList, ArrayList<SpenContextMenuItemInfo> menu, ArrayList<Integer> styleList, int pressType, PointF point) {
        	// Set the Context menu
            menu.add(new SpenContextMenuItemInfo(CONTEXT_MENU_DELETE_ID, getResources().getDrawable(R.drawable.object_delete), "Delete", true));
            // Display Group menu when more than one object is selected.
            if (objectList.size() > 1) {
            	menu.add(new SpenContextMenuItemInfo(CONTEXT_MENU_GROUP_ID, getResources().getDrawable(R.drawable.object_group), "Group", true));
            }
            // Display Ungroup menu if the selected objects include one or more ObjectContainers.
            for (SpenObjectBase obj : objectList) {
                if (obj.getType() == SpenObjectBase.TYPE_CONTAINER) {
                    menu.add(new SpenContextMenuItemInfo(CONTEXT_MENU_UNGROUP_ID, getResources().getDrawable(R.drawable.object_ungroup), "Ungroup", true));
                    break;
                }
            }
            // Display Arrange menu if only one object is selected.
            if (objectList.size() == 1) {
//				menu.add(new SpenContextMenuItemInfo(CONTEXT_MENU_MOVE_TO_BOTTOM_ID, mPenBtn.getDrawable(), "Bottom", true));
                menu.add(new SpenContextMenuItemInfo(CONTEXT_MENU_MOVE_TO_BACKWARD_ID, getResources().getDrawable(R.drawable.object_move_backward), "Backward", true));
                menu.add(new SpenContextMenuItemInfo(CONTEXT_MENU_MOVE_TO_FORWARD_ID, getResources().getDrawable(R.drawable.object_move_forward), "Forward", true));
//				menu.add(new SpenContextMenuItemInfo(CONTEXT_MENU_MOVE_TO_TOP_ID, mPenBtn.getDrawable(), "Top", true));
                return true;
            }
            // Attach an individual control for each object.
            SpenControlList controlList = new SpenControlList(mContext, mSpenPageDoc);
            controlList.setObject(objectList);
            controlList.setGroup(false);
            mSpenSurfaceView.setControl(controlList);
            controlList.setContextMenu(menu);

            return false;
        }

        @Override
        public boolean onClosed(ArrayList<SpenObjectBase> arg0) {
            return false;
        }
    };

    private final SpenSelectionChangeListener mSelectionListener = new SpenSelectionChangeListener() {

        @Override
        public void onChanged(SpenSettingSelectionInfo info) {
            // Close Setting view if selection type is changed.
            mSelectionSettingView.setVisibility(SpenSurfaceView.GONE);
        }
    };

    private final SpenZoomListener mZoomListener = new SpenZoomListener() {
		@Override
		public void onZoom(float zoomPosX, float zoomPosY, float zoomRatio) {
			navigatorController.zoomListener(zoomPosX, zoomPosY, zoomRatio);
		}
    };

	private void closeSettingView() {
		// Close all the setting views.
		mPenSettingView.setVisibility(SpenSurfaceView.GONE);
		mEraserSettingView.setVisibility(SpenSurfaceView.GONE);
		mTextSettingView.setVisibility(SpenSurfaceView.GONE);
		mFillToolSettingView.setVisibility(View.GONE);
		mMagicWandToolSettingView.setVisibility(SpenSurfaceView.GONE);
		mSelectionSettingView.setVisibility(SpenSurfaceView.GONE);
		mColorSettingView.setVisibility(View.GONE);
	}

    
	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (mPenSettingView != null) {
			mPenSettingView.close();
		}
		if (mEraserSettingView != null) {
			mEraserSettingView.close();
		}
		if (mTextSettingView != null) {
			mTextSettingView.close();
		}
		if (mSelectionSettingView != null) {
			mSelectionSettingView.close();
		}

		if (mSpenSurfaceView != null) {
			mSpenSurfaceView.close();
			mSpenSurfaceView = null;
		}

        if (mSpenNoteDoc != null) {
            try {
                if (isDiscard) {
                    mSpenNoteDoc.discard();
                } else {
                    mSpenNoteDoc.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            mSpenNoteDoc = null;
        }

		navigatorController.releaseHandle();
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.design_editor, menu);
		return true;
	}

    @Override
    public void onBackPressed() {
        if (mSpenPageDoc.getObjectCount(true) > 0 && mSpenPageDoc.isChanged()) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(mContext);
            dlg.setIcon(mContext.getResources().getDrawable(android.R.drawable.ic_dialog_alert));
            dlg.setTitle(mContext.getResources().getString(R.string.app_name))
                    .setMessage("Do you want to exit after save?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saveNoteFile(true);
                            dialog.dismiss();
                        }
                    }).setNeutralButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            isDiscard = true;
                            finish();
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            dlg = null;
        } else {
            super.onBackPressed();
        }
    }
    


	private final OnClickListener saveBtnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			saveNoteFile(false);
		}
	};

    private boolean saveNoteFile(final boolean isClose) {
        // Prompt Save File dialog to get the file name
        // and get its save format option (note file or image).
    	LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.edittool_dialog, (ViewGroup)findViewById(R.id.layout_root));    
        
        
        final AlertDialog ad = new AlertDialog.Builder(DesignEditorTool.this).setView(layout).create();  
        final EditText inputPath = (EditText) layout.findViewById(R.id.input_path);
        
        if(title != null)
        {
        	inputPath.setText(title.toString());
        }
        
        ad.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        inputPath.setSingleLine();
			
        	
    	layout.findViewById(R.id.completeButton).setOnClickListener(new OnClickListener() {  
                
              @Override  
              public void onClick(View v) {  
            	String saveFilePath = staticFilePath.getPath() + '/';
  		        String fileName = inputPath.getText().toString();
  		        if(fileName.equals(""))
  		        {
  		        	Toast.makeText(mContext, "제목을 입력하세요.", Toast.LENGTH_LONG).show();
  		        	return;
  		        }
  		        if (!fileName.equals("")) {  		        	
  		           saveFilePath += "_" + fileName;
  		           saveNoteFile(saveFilePath + ".spd");
  		           retIntent.putExtra("SPD", saveFilePath + ".spd");
  		           captureSpenSurfaceView(saveFilePath + ".png");
  		           retIntent.putExtra("PNG", saveFilePath + ".png");
  		        }
  		       else {
                  Toast.makeText(mContext, "다시 입력해주세요.", Toast.LENGTH_LONG).show();
               }
  		        
  		        retIntent.putExtra("position", position);
  		        
  		        Log.d("position", " : " + position);
  				retIntent.putExtra("title", inputPath.getText().toString());
  				Log.d("saveFilePath", saveFilePath.toString());
  				
  			
  				setResult(Activity.RESULT_OK, retIntent);
  				
  		        finish();  
                    
              }  
          });  
		
        
        ad.show();        

        return true;
    }

    private boolean saveNoteFile(String strFileName) {
        try {
            // Save NoteDoc
            mSpenNoteDoc.save(strFileName);
            Toast.makeText(mContext, "Save success to " + strFileName, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(mContext, "Cannot save design file.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void captureSpenSurfaceView(String strFileName) {

        // Capture the view
        Bitmap imgBitmap = mSpenSurfaceView.captureCurrentView(true);
        if (imgBitmap == null) {
            Toast.makeText(mContext, "Capture failed." + strFileName, Toast.LENGTH_SHORT).show();
            return;
        }

        OutputStream out = null;
        try {
            // Create FileOutputStream and save the captured image.
            out = new FileOutputStream(strFileName);
            imgBitmap.compress(CompressFormat.PNG, 100, out);
            // Save the note information.
            mSpenNoteDoc.save(out);
            out.close();
            Toast.makeText(mContext, "Captured images were stored in the file" + strFileName, Toast.LENGTH_SHORT)
                    .show();
        } catch (IOException e) {
            File tmpFile = new File(strFileName);
            if (tmpFile.exists()) {
                tmpFile.delete();
            }
            Toast.makeText(mContext, "Failed to save the file.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (Exception e) {
            File tmpFile = new File(strFileName);
            if (tmpFile.exists()) {
                tmpFile.delete();
            }
            Toast.makeText(mContext, "Failed to save the file.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        imgBitmap.recycle();
    }

    private void loadNoteFile() {
        // Load the file list.
        final String fileList[] = setFileList();
        if (fileList == null) {
            return;
        }

        //TODO : Load File from static positioni
        
        // Prompt Load File dialog.
        new AlertDialog.Builder(mContext).setTitle("Select file")
                .setItems(fileList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strFilePath = staticFilePath.getPath() + '/' + fileList[which];

                        try {
                            // Create NoteDoc with the selected file.
                            SpenNoteDoc tmpSpenNoteDoc = new SpenNoteDoc(mContext, strFilePath, mScreenRect.width(),
                                    SpenNoteDoc.MODE_WRITABLE);
                            mSpenNoteDoc.close();
                            mSpenNoteDoc = tmpSpenNoteDoc;
                            if (mSpenNoteDoc.getPageCount() == 0) {
                                mSpenPageDoc = mSpenNoteDoc.appendPage();
                            } else {
                                mSpenPageDoc = mSpenNoteDoc.getPage(mSpenNoteDoc.getLastEditedPageIndex());
                            }
                            mSpenSurfaceView.setPageDoc(mSpenPageDoc, true);
                            mSpenSurfaceView.update();
                            Toast.makeText(mContext, "Successfully loaded noteFile.", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            Toast.makeText(mContext, "Cannot open this file.", Toast.LENGTH_LONG).show();
                        } catch (SpenUnsupportedTypeException e) {
                            Toast.makeText(mContext, "This file is not supported.", Toast.LENGTH_LONG).show();
                        } catch (SpenInvalidPasswordException e) {
                            Toast.makeText(mContext, "This file is locked by a password.", Toast.LENGTH_LONG).show();
                        } catch (SpenUnsupportedVersionException e) {
                            Toast.makeText(mContext, "This file is the version that does not support.",
                                    Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(mContext, "Failed to load noteDoc.", Toast.LENGTH_LONG).show();
                        }
                    }
                }).show();
    }

    private String[] setFileList() {
        // Call the file list under the directory in mFilePath.
        if (!staticFilePath.exists()) {
            if (!staticFilePath.mkdirs()) {
                Toast.makeText(mContext, "Save Path Creation Error", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        // Filter in spd and png files.
        File[] fileList = staticFilePath.listFiles(new txtFileFilter());
        if (fileList == null) {
            Toast.makeText(mContext, "File does not exist.", Toast.LENGTH_SHORT).show();
            return null;
        }

        int i = 0;
        String[] strFileList = new String[fileList.length];
        for (File file : fileList) {
            strFileList[i++] = file.getName();
        }

        return strFileList;
    }

    class txtFileFilter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) {
            return (name.endsWith(".spd") || name.endsWith(".png"));
        }
    }

}