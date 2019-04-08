package com.binhtt.truyentranhonline.ui.main.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.binhtt.truyentranhonline.R;
import com.binhtt.truyentranhonline.ui.base.BaseAdapter;
import com.binhtt.truyentranhonline.ui.base.BaseViewHolder;
import com.binhtt.truyentranhonline.ui.main.model.Story;

import java.util.ArrayList;

/**
 * @author binhtt <binhjdev@gmail.com>
 * @version bg.0.0
 * @since 12/08/2017
 */

public class Gui_Screen3Adapter extends BaseAdapter<BaseViewHolder> {

    private ArrayList<Story> mStoryList;
    private StartPageClickItemListener mListener;

    public Gui_Screen3Adapter(Context context, ArrayList<Story> list, StartPageClickItemListener listener) {
        super(context);
        mStoryList = list;
        mListener = listener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_gui_screen_3, parent, false);
        return new GuiScreen3ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        ((GuiScreen3ViewHolder) holder).bindData(mStoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return mStoryList == null ? 0 : mStoryList.size();
    }

    public interface StartPageClickItemListener extends OnBaseItemClickListener<Story> {

    }

    public class GuiScreen3ViewHolder extends BaseViewHolder<Story> {
        private TextView mTvTitle;

        public GuiScreen3ViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.mTvTitle);

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
            mTvTitle.setText(story.getName());
        }
    }
}
