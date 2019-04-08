package com.binhtt.truyentranhonline.ui.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.binhtt.truyentranhonline.R;
import com.binhtt.truyentranhonline.utils.KeyboardUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;


/**
 * @author binhtt <binhjdev@gmail.com>
 * @version bg.0.0
 * @since 31/07/2017
 */
@EActivity
public abstract class BaseActivity extends AppCompatActivity implements View.OnFocusChangeListener {
    public static final String TAG = BaseActivity.class.getSimpleName();

    protected abstract void afterView();

    private ProgressDialog mProgressDialog;
    private ViewGroup mRootViewGroup;

    @AfterViews
    public void initViews() {
        // define animation for all subclass
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

        mRootViewGroup = (ViewGroup) findViewById(android.R.id.content);
        mRootViewGroup.setFocusableInTouchMode(true);
        mRootViewGroup.setClickable(true);
        mRootViewGroup.setOnFocusChangeListener(this);
        initProgressBar();
        afterView();
    }

    /**
     * register a progress bar contain notify message
     */
    private void initProgressBar() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.message_waiting));
        mProgressDialog.setCancelable(false);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            KeyboardUtil.hideKeyboard(v);
        }
    }

    /**
     * @param image        set image for control
     * @param imageUrl     url get from link image
     * @param defaultImage image default when error get link url
     */
    public void syncImage(ImageView image, int imageUrl, int defaultImage) {
        Glide
                .with(this)
                .load(imageUrl)
                .asBitmap()
                .atMost()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(defaultImage)
                .centerCrop()
                .into(image);
    }

    /**
     * @param image        set image for control
     * @param url          url get from link image
     * @param defaultImage image default when error get link url
     */
    public void syncImage(ImageView image, String url, int defaultImage) {
        Glide
                .with(this)
                .load(url)
                .asBitmap()
                .atMost()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(defaultImage)
                .centerCrop()
                .into(image);
    }

    public void showSnackbar(View view, String message, int duration) {
        Snackbar snackbar = Snackbar.make(view, message, duration);
        view = snackbar.getView();
        TextView textView = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    public void showDialogInternet() {
        String message = getResources().getString(R.string.error_network_content);
        showDialogMessage("", message);
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
    public void showDialogMessage(String title, String message) {
        if (!isFinishing()) {
            new AlertDialog.Builder(this)
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
