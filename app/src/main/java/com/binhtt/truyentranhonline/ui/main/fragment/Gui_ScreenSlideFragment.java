package com.binhtt.truyentranhonline.ui.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.binhtt.truyentranhonline.R;
import com.binhtt.truyentranhonline.ui.base.BaseFragment;

/**
 * @author binhtt <binhjdev@gmail.com>
 * @version 1.0.0
 * @since 16/08/2017
 */

public class Gui_ScreenSlideFragment extends BaseFragment {

    private String urlImage;

    public static Gui_ScreenSlideFragment newInstance(String url) {
        Gui_ScreenSlideFragment guiScreenSlideFragment = new Gui_ScreenSlideFragment();
        Bundle bundle = new Bundle();
        bundle.putString("urlLink", url);
        guiScreenSlideFragment.setArguments(bundle);

        return guiScreenSlideFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urlImage = getArguments().getString("urlLink");
    }

    // this method returns the view containing the required which is set while creating instance of fragment
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gui_screen_slide_fragment, container, false);
        ImageView iv = (ImageView) view.findViewById(R.id.mPhotoView);

        syncImage(iv, urlImage, R.drawable.ic_avatar_profile);
        return view;
    }
}
