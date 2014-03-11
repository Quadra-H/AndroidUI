package com.ssm.quadrah.diymarket.designeditor.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.pen.Spen;

public class SDKUtils {
    
    public static boolean processUnsupportedException(final Activity activity, 
            SsdkUnsupportedException e) {
        e.printStackTrace();
        int errType = e.getType();
		// If the device is not a Samsung device or the device does not support Pen.
        if(errType == SsdkUnsupportedException.VENDOR_NOT_SUPPORTED ||
                errType == SsdkUnsupportedException.DEVICE_NOT_SUPPORTED ) {
            Toast.makeText(activity, "This device does not support Spen.",
                    Toast.LENGTH_SHORT).show();
            activity.finish();
        } 
        else if( errType == SsdkUnsupportedException.LIBRARY_NOT_INSTALLED ) {
			// If SpenSDK APK is not installed.
            showAlertDialog( activity, 
                "You need to install additional Spen software"
                +" to use this application."
                + "You will be taken to the installation screen."
                + "Restart this application after the software has been installed."
                , true);
        } else if( errType == SsdkUnsupportedException.LIBRARY_UPDATE_IS_REQUIRED ) {
			// SpenSDK APK must be updated.
            showAlertDialog( activity,
                "You need to update your Spen software to use this application."
                + " You will be taken to the installation screen." 
                + " Restart this application after the software has been updated."
                , true);
        } else if( errType == SsdkUnsupportedException.LIBRARY_UPDATE_IS_RECOMMENDED ) {
			// Recommended to update SpenSDK APK to a new version available.
            showAlertDialog( activity,
                "We recommend that you update your Spen software"
                + " before using this application."
                + " You will be taken to the installation screen."
                + " Restart this application after the software has been updated."
                , false);
            return false; // Procceed to the normal activity process if it is not updated.
        }
        return true;
    }

    private static void showAlertDialog(final Activity activity, String msg,
            final boolean closeActivity) {

        AlertDialog.Builder dlg = new AlertDialog.Builder(activity);
        dlg.setIcon(activity.getResources().getDrawable(
            android.R.drawable.ic_dialog_alert));
        dlg.setTitle("Upgrade Notification")
            .setMessage(msg)
            .setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                        DialogInterface dialog, int which) {
						// Go to the market site and install or update APK.
                        Uri uri = Uri.parse("market://details?id="
                                + Spen.SPEN_NATIVE_PACKAGE_NAME);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        activity.startActivity(intent);

                        dialog.dismiss();
                        activity.finish();
                    }
                })
            .setNegativeButton(android.R.string.no,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                        DialogInterface dialog, int which) {
                        if(closeActivity == true) {
							// Terminate the activity if APK is not installed.
                            activity.finish();
                        }
                        dialog.dismiss();
                    }
            })
            .setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if(closeActivity == true) {
						// Terminate the activity if APK is not installed.
                        activity.finish();
                    }
                }
            })
            .show();
        dlg = null;
    }
}
