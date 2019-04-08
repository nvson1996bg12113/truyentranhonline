package com.binhtt.truyentranhonline.ui.main.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.binhtt.truyentranhonline.R;
import com.binhtt.truyentranhonline.ui.base.BaseAdapter;
import com.binhtt.truyentranhonline.ui.base.BaseViewHolder;
import com.binhtt.truyentranhonline.ui.main.model.Story;

import java.util.ArrayList;
import java.util.Locale;

/**
 * @author binhtt <binhjdev@gmail.com>
 * @version bg.0.0
 * @since 07/08/2017
 */

public class Gui_Screen2Adapter extends BaseAdapter<BaseViewHolder> {

    private ArrayList<Story> mStories;
    private ArrayList<Story> mSearchList;
    private OnClickStoryListener mListener;

    public Gui_Screen2Adapter(Context context, ArrayList<Story> list, OnClickStoryListener listener) {
        super(context);
        mStories = list;
        mSearchList = new ArrayList<>();
        mSearchList.addAll(mStories);
        mListener = listener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gui_screen_2, parent, false);
        return new GuiScreen2ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        ((GuiScreen2ViewHolder) holder).bindData(mStories.get(position));
    }

    @Override
    public int getItemCount() {
        return mStories == null ? 0 : mStories.size();
    }

    public interface OnClickStoryListener extends OnBaseItemClickListener<Story> {

    }

    public class GuiScreen2ViewHolder extends BaseViewHolder<Story> {
        private TextView mTvTap;
        private ImageView mImgAvatar;

        public GuiScreen2ViewHolder(View itemView) {
            super(itemView);
            mTvTap = (TextView) itemView.findViewById(R.id.mTvTap);
            mImgAvatar = (ImageView) itemView.findViewById(R.id.mImgAvatar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(getItem(), getAdapterPosition());
                }
            });
        }

        @Override
        public void bindData(Story story) {
            super.bindData(story);

            mTvTap.setText(story.getName());
            syncImage(mImgAvatar, story.getUrlImage(), R.drawable.ic_avatar_profile);
        }
    }
}