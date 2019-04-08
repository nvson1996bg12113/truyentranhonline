package com.binhtt.truyentranhonline.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;

/**
 * @author binhtt <binhjdev@gmail.com>
 * @version bg.0.0
 * @since 09/08/2017
 */

public class DialogUtil {
    private static DialogUtil mNewInstance;
    private Context mContext;
    private ProgressDialog mProgressDialog;

    /**
     * single ton
     *
     * @return
     */
    public static DialogUtil Create(Context context) {
        if (mNewInstance == null) {
            mNewInstance = new DialogUtil(context);
        }
        return mNewInstance;
    }

    private DialogUtil(Context context) {
        mContext = context.getApplicationContext();
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Xin đợi...");
        mProgressDialog.setCancelable(false);
    }

    public void showDialogInternet(Activity activity) {
        String message = "Không có kết nối mạng";
        showDialogMessage(activity, "", message);
    }

    /**
     * show progress bar when register API
     */
    public void showProgressDialog() {
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissProgressDialog();
            }
        }, 10000);
    }

    /**
     * dismiss progress bar
     */
    public void dismissProgressDialog() {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
    }

    /**
     * show message and title
     *
     * @param title
     * @param message
     */
    public void showDialogMessage(Activity activity, String title, String message) {
        if (!activity.isFinishing()) {
            new AlertDialog.Builder(mContext)
                    .setTitle(title)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
        }
    }
}
