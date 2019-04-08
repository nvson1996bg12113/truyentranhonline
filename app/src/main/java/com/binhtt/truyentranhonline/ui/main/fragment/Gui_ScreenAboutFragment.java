package com.binhtt.truyentranhonline.ui.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binhtt.truyentranhonline.R;
import com.binhtt.truyentranhonline.ui.base.BaseFragment;
import com.binhtt.truyentranhonline.utils.EmailIntentBuilder;


/**
 * @author binhtt <binhjdev@gmail.com>
 * @version 1.0.0
 * @since 21/08/2017
 */
public class Gui_ScreenAboutFragment extends BaseFragment {
    TextView mTvSendEmail;
    LinearLayout mMainContent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.gui_screen_about_fragment, container, false);
        mTvSendEmail = (TextView) layout.findViewById(R.id.mTvSendMail);
        mMainContent = (LinearLayout) layout.findViewById(R.id.mainContent);

        mTvSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFeedback();
            }
        });
        return layout;
    }

    private void sendFeedback() {
        boolean success = EmailIntentBuilder.from(getActivity())
                .to("binhjdev@gmail.com")
                .body(getString(R.string.feedback_body))
                .start();

        if (!success) {
            Snackbar.make(mMainContent, R.string.error_no_email_app, Snackbar.LENGTH_LONG).show();
        }
    }
}
