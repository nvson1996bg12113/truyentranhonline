package com.binhtt.truyentranhonline.ui.base;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

@EFragment
public abstract class BaseFragment extends Fragment {
    protected final String TAG = this.getClass().getSimpleName();

    public void onRefresh() {
    }

    @AfterViews
    protected void initViews() {
        getView().setFocusableInTouchMode(true);
        getView().setClickable(true);
        getView().setOnFocusChangeListener((BaseActivity) getActivity());

        onRefresh();
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) super.getActivity();
    }

    public void showSnackbar(View view, String message, int duration) {
        getBaseActivity().showSnackbar(view, message, duration);
    }

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
}
