package com.binhtt.truyentranhonline.ui.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public abstract class BaseViewHolder<Item> extends RecyclerView.ViewHolder {
    protected final String TAG = this.getClass().getSimpleName();

    private Item mItem;
    private Context mContext;

    protected Context getContext() {
        return mContext;
    }

    public Item getItem() {
        return mItem;
    }

    public BaseViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
    }

    public void bindData(Item item) {
        mItem = item;
    }

    protected void syncImage(ImageView imageView, String urlImage, int defaultImageId) {
        Glide.with(mContext).load(urlImage).asBitmap().centerCrop().atMost().diskCacheStrategy(DiskCacheStrategy.ALL).error(defaultImageId).into(imageView);
    }
}
