package com.binhtt.truyentranhonline.ui.base;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public abstract class BaseAdapter<VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {
    protected final String TAG = this.getClass().getSimpleName();

    private final Context mContext;

    protected BaseAdapter(Context context) {
        mContext = context;
    }

    protected Context getContext() {
        return mContext;
    }

    protected Resources getResources() {
        return mContext.getResources();
    }

    protected String getString(int resId) {
        return mContext.getString(resId);
    }

    protected String getString(int resId, Object... objects) {
        return mContext.getString(resId, objects);
    }

    public interface OnBaseItemClickListener<Item> {
        void onItemClick(Item item, int position);
    }
}
