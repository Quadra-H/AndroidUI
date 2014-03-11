package com.ssm.quadrah.diymarket.example;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.pen.Spen;
import com.samsung.android.sdk.pen.document.SpenNoteDoc;
import com.samsung.android.sdk.pen.document.SpenObjectBase;
import com.samsung.android.sdk.pen.document.SpenObjectImage;
import com.samsung.android.sdk.pen.document.SpenPageDoc;
import com.samsung.android.sdk.pen.document.SpenPageDoc.ObjectListener;
import com.samsung.android.sdk.pen.engine.SpenSurfaceView;
import com.samsung.android.sdk.pen.engine.SpenTouchListener;
import com.ssm.quadrah.diymarket.R;

public class QHSample_Layout extends Activity {
    private final int REQUEST_CODE_ATTACH_IMAGE = 100;

    private final String ATTACH_IMAGE_KEY = "Attach Image Key";

    private Context mContext;
    private SpenNoteDoc mSpenNoteDoc;
    private SpenPageDoc mSpenPageDoc;
    private SpenSurfaceView mSpenSurfaceView;

    private ImageView mLoadStickerBtn;
    
    private Rect mScreenRect;
    private File mFilePath;

	private boolean isDiscard = false;
	
	private MagicWandTool mwt;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_sticker);
        mContext = this;

		// Initialize Spen
        boolean isSpenFeatureEnabled = false;
        Spen spenPackage = new Spen();
        try {
            spenPackage.initialize(this);
            isSpenFeatureEnabled = spenPackage.isFeatureEnabled(Spen.DEVICE_PEN);
        } catch (SsdkUnsupportedException e) {
            if( processUnsupportedException(e) == true) {
                return;
            }
        } catch (Exception e1) {
            Toast.makeText(mContext, "Cannot initialize Spen.", Toast.LENGTH_SHORT).show();
            e1.printStackTrace();
            finish();
        }

		// Create Spen View
        RelativeLayout spenViewLayout = (RelativeLayout) findViewById(R.id.spenViewLayout);
        mSpenSurfaceView = new SpenSurfaceView(mContext);
        if (mSpenSurfaceView == null) {
            Toast.makeText(mContext, "Cannot create new SpenView.", Toast.LENGTH_SHORT).show();
            finish();
        }
        spenViewLayout.addView(mSpenSurfaceView);

		// Get the dimension of the device screen.
        Display display = getWindowManager().getDefaultDisplay();
        mScreenRect = new Rect();
        display.getRectSize(mScreenRect);
        
		// Create SpenNoteDoc
        try {
            mSpenNoteDoc = new SpenNoteDoc(mContext, mScreenRect.width(), mScreenRect.height());
        } catch (IOException e) {
            Toast.makeText(mContext, "Cannot create new NoteDoc.",  Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
        
		// Add a Page to NoteDoc, get an instance, and set it to the member variable.
        mSpenPageDoc = mSpenNoteDoc.appendPage();
        mSpenPageDoc.setBackgroundColor(0xFFD6E6F5);
        mSpenPageDoc.clearHistory();
		// Set PageDoc to View.
        mSpenSurfaceView.setPageDoc(mSpenPageDoc, true);

        mSpenSurfaceView.setTouchListener(mPenTouchListener);

		mSpenPageDoc.setObjectListener(mObjectListener);
		
        mLoadStickerBtn = (ImageView) findViewById(R.id.stickerAddBtn);
        mLoadStickerBtn.setOnClickListener(mLoadStickerBtnClickListener);

		String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/quadrah/designeditor/layout";
        mFilePath = new File(filePath);
        if (!mFilePath.exists()) {
            if (!mFilePath.mkdirs()) {
                Toast.makeText(mContext, "Save Path Creation Error", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if(isSpenFeatureEnabled == false) {
            mSpenSurfaceView.setToolTypeAction(SpenSurfaceView.TOOL_FINGER, SpenSurfaceView.ACTION_STROKE);
            Toast.makeText(mContext, "Device does not support Spen. \n You can draw stroke by finger.", Toast.LENGTH_SHORT).show();
        }

        mSpenSurfaceView.setToolTypeAction(5, SpenSurfaceView.ACTION_NONE);
        
        mwt = new MagicWandTool(mSpenSurfaceView, mSpenPageDoc);
    }

    private boolean processUnsupportedException(SsdkUnsupportedException e) {
        e.printStackTrace();
        int errType = e.getType();
		// If the device is not a Samsung device or if the device does not support Pen.
        if (errType == SsdkUnsupportedException.VENDOR_NOT_SUPPORTED || errType == SsdkUnsupportedException.DEVICE_NOT_SUPPORTED) {
            Toast.makeText(mContext, "This device does not support Spen.", Toast.LENGTH_SHORT).show();
            finish();
        }
        else if (errType == SsdkUnsupportedException.LIBRARY_NOT_INSTALLED) {
			// If SpenSDK APK is not installed.
            showAlertDialog( "You need to install additional Spen software to use this application."
                + "You will be taken to the installation screen. Restart this application after the software has been installed.", true);
        } else if (errType
                == SsdkUnsupportedException.LIBRARY_UPDATE_IS_REQUIRED) {
			// SpenSDK APK must be updated.
            showAlertDialog( "You need to update your Spen software to use this application."
                + " You will be taken to the installation screen. Restart this application after the software has been updated.", true);
        } else if (errType
                == SsdkUnsupportedException.LIBRARY_UPDATE_IS_RECOMMENDED) {
			// Update of SpenSDK APK to an available new version is recommended.
            showAlertDialog( "We recommend that you update your Spen software before using this application."
                + " You will be taken to the installation screen. Restart this application after the software has been updated.", false);
            return false;
        }
        return true;
    }

	private final ObjectListener mObjectListener = new ObjectListener() {
		@Override
		public void onObjectAdded(SpenPageDoc spenPageDoc, ArrayList<SpenObjectBase> objectList, int arg2) {
		}

		@Override
		public void onObjectChanged(SpenPageDoc arg0, SpenObjectBase arg1, int arg2) {
		}

		@Override
		public void onObjectRemoved(SpenPageDoc spenPageDoc, ArrayList<SpenObjectBase> objectList, int arg2) {
		}
	};

    private float cx, cy;
    
    private final SpenTouchListener mPenTouchListener = new SpenTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP ) {
                cx = event.getX(); 
                cy = event.getY();
                
                AlertDialog.Builder dlg = new AlertDialog.Builder(mContext);
                dlg.setIcon(mContext.getResources().getDrawable(android.R.drawable.ic_dialog_alert));
                dlg.setTitle(mContext.getResources().getString(R.string.app_name))
                        .setMessage("Change the object image. Continue?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                changeImgObj();
                                // finish dialog
                                dialog.dismiss();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                dlg = null;
                
                
        		//Thread2 t2 = new Thread2();
        		//t2.start();

        		
            }
            return false;
        }
    };

    
    class Thread2 extends Thread{
    	public void run(){
            while(true) {

                if (mSpenNoteDoc.hasAttachedFile(ATTACH_IMAGE_KEY)) {

                    mSpenNoteDoc.detachFile(ATTACH_IMAGE_KEY);
                    break;
                }
            }
    	}
    }

    private void changeImgObj() {
        // Set warning messages.
        AlertDialog.Builder dlg = new AlertDialog.Builder(mContext);
        dlg.setIcon(mContext.getResources().getDrawable(android.R.drawable.ic_dialog_alert));
        dlg.setTitle(mContext.getResources().getString(R.string.app_name))
                .setMessage(
                        "When you select an image, copy the image in NoteDoc data. \n" + "If the image is large,"
                                + " the function is slow and it takes a long time to save/load.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        callGalleryForInputImage(REQUEST_CODE_ATTACH_IMAGE);
                        // Close the dialog.
                        dialog.dismiss();
                    }
                }).show();
        dlg = null;
    }

    private void callGalleryForInputImage(int nRequestCode) {
        // Get an image from Gallery
        try {
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, nRequestCode);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(mContext, "Cannot find gallery.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
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


                Bitmap imageBitmap;
                imageBitmap = BitmapFactory.decodeFile(mSpenNoteDoc.getAttachedFile(ATTACH_IMAGE_KEY));
                mwt.selectArea(cx, cy, imageBitmap);
		        mSpenSurfaceView.update();  
            }
        }
    }

    private final OnClickListener mLoadStickerBtnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            mSpenSurfaceView.closeControl();

            loadStickerFile();
            
    		Thread1 t1 = new Thread1();
    		t1.start();
        }
    };
    
    private SpenObjectImage imgObj;
    
    class Thread1 extends Thread{
    	public void run(){
            while(true) {

                if (mSpenNoteDoc.hasAttachedFile(ATTACH_IMAGE_KEY)) {
                	if( imgObj != null ) {
                		mSpenPageDoc.removeObject(imgObj);
                	}	
                	
                    imgObj = new SpenObjectImage();
                    Bitmap imageBitmap;
                    // Set a bitmap file to ObjectImage.
                    // If there is a file attached, set it to ObjectImage.
                    imageBitmap = BitmapFactory.decodeFile(mSpenNoteDoc.getAttachedFile(ATTACH_IMAGE_KEY));
                    
                    imgObj.setImage(imageBitmap);

                    // Set the location to insert ObjectImage and add to PageDoc.
                    float imgWidth = imageBitmap.getWidth();
                    float imgHeight = imageBitmap.getHeight();
                    RectF rect = getRealPoint(imgWidth/2, imgHeight/2, imgWidth, imgHeight);
                    imgObj.setRect(rect, true);
                    mSpenPageDoc.appendObject(imgObj);
                    mSpenSurfaceView.update();

                    imageBitmap.recycle();
                    
                    mSpenNoteDoc.detachFile(ATTACH_IMAGE_KEY);
                    
                    break;
                }
            }
    	}
    }

    private RectF getRealPoint(float x, float y, float width, float height) {
        float panX = mSpenSurfaceView.getPan().x;
        float panY = mSpenSurfaceView.getPan().y;
        float zoom = mSpenSurfaceView.getZoomRatio();
        width *= zoom;
        height *= zoom;
        RectF realRect = new RectF();
        realRect.set((x - width / 2) / zoom + panX, (y - height / 2) / zoom + panY, (x + width / 2) / zoom + panX,
                (y + height / 2) / zoom + panY);
        return realRect;
    }
    
    private void loadStickerFile() {
        // Load the file list.
        final String fileList[] = setFileList();
        if (fileList == null) {
            return;
        }
        
        // Prompt Load File dialog.
        new AlertDialog.Builder(mContext).setTitle("Select file").setItems(fileList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strFilePath = mFilePath.getPath() + '/' + fileList[which];

                        mSpenNoteDoc.attachFile(ATTACH_IMAGE_KEY, strFilePath);                        
                    }
                }).show();
    }

    private String[] setFileList() {
        // Call the file list under the directory in mFilePath.
        if (!mFilePath.exists()) {
            if (!mFilePath.mkdirs()) {
                Toast.makeText(mContext, "Sticker Path Creation Error", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        // Filter in spd and png files.
        File[] fileList = mFilePath.listFiles(new txtFileFilter());
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

    private void showAlertDialog(String msg, final boolean closeActivity) {

        AlertDialog.Builder dlg = new AlertDialog.Builder(mContext);
        dlg.setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_alert));
        dlg.setTitle("Upgrade Notification").setMessage(msg).setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                        DialogInterface dialog, int which) {
						// Go to the market website and install/update APK.
                        Uri uri = Uri.parse("market://details?id=" + Spen.SPEN_NATIVE_PACKAGE_NAME);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        dialog.dismiss();
                        finish();
                    }
                }).setNegativeButton(android.R.string.no,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                        DialogInterface dialog, int which) {
                        if(closeActivity == true) {
							// Terminate the activity if APK is not installed.
                            finish();
                        }
                        dialog.dismiss();
                    }
            }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if(closeActivity == true) {
						// Terminate the activity if APK is not installed.
                        finish();
                    }
                }
            }).show();
        dlg = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mSpenSurfaceView != null) {
            mSpenSurfaceView.closeControl();
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
    };
}
