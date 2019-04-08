package com.binhtt.truyentranhonline.ui.main.activities;

import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.binhtt.truyentranhonline.R;
import com.binhtt.truyentranhonline.ui.base.BaseActivity;
import com.binhtt.truyentranhonline.ui.main.adapter.Gui_Screen2Adapter;
import com.binhtt.truyentranhonline.ui.main.adapter.Gui_Screen3Adapter;
import com.binhtt.truyentranhonline.ui.main.fragment.Gui_Screen18PlusFragment;
import com.binhtt.truyentranhonline.ui.main.model.Story;
import com.binhtt.truyentranhonline.utils.ConnectionUtil;
import com.binhtt.truyentranhonline.utils.RecyclerViewUtils;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.binhtt.truyentranhonline.ui.main.InitParams.REFERRER;
import static com.binhtt.truyentranhonline.ui.main.InitParams.USER_AGENT;

/**
 * @author binhtt <binhjdev@gmail.com>
 * @version 1.0.0
 * @since 18/08/2017
 */
@EActivity(R.layout.activity_gui_screen_2)
public class Gui_Screen218PlusActivity extends BaseActivity {
    @ViewById
    RecyclerView mRecycleView;
    @ViewById
    SwipeRefreshLayout mSwipeRefreshLayout;
    @ViewById
    Toolbar mToolbar;

    private Gui_Screen3Adapter mAdapterLink;

    @Extra
    Story mStory;

    @Override
    protected void afterView() {
        setUpToolbar();

        setRecyclerView();

        getListData();

        swipeRefresh();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void swipeRefresh() {
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getApplicationContext(), R.color.main));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (ConnectionUtil.isConnected(getApplicationContext())) {
                    new GuiScreen218PlusTask().execute(mStory.getUrlLink());
                } else {
                    showDialogInternet();
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getListData() {
        if (ConnectionUtil.isConnected(this)) {
            new GuiScreen218PlusTask().execute(mStory.getUrlLink());
        } else {
            showDialogInternet();
        }
    }

    private void setRecyclerView() {
        RecyclerViewUtils.Create().setUpVertical(getApplicationContext(), mRecycleView);
    }

    private void setUpToolbar() {
        // set toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(mStory.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public class GuiScreen218PlusTask extends AsyncTask<String, Void, ArrayList<Story>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected ArrayList<Story> doInBackground(String... params) {
            Document mDocument;
            ArrayList<Story> mList = new ArrayList<>();
            try {
                mDocument = (Document) Jsoup.connect(params[0]).userAgent(USER_AGENT).referrer(REFERRER).get();

                if (mDocument != null) {
                    Elements sub = mDocument.select("ul > li.row > div.col-xs-5.chapter");

                    for (Element element : sub) {
                        Story story = new Story();
                        Element nameSubject = element.getElementsByTag("a").first();

                        if (nameSubject != null) {
                            String name = nameSubject.text();
                            story.setName(name);
                        }

                        if (nameSubject != null) {
                            String linkUrl = nameSubject.attr("href");
                            story.setUrlLink(linkUrl);
                        }

                        // Add to list
                        mList.add(story);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return mList;
        }

        @Override
        protected void onPostExecute(ArrayList<Story> stories) {
            super.onPostExecute(stories);

            if (stories != null) {
                dismissProgressDialog();
            }

            mAdapterLink = new Gui_Screen3Adapter(getApplicationContext(), stories, new Gui_Screen3Adapter.StartPageClickItemListener() {
                @Override
                public void onItemClick(Story story, int position) {
                    Gui_Screen4Activity_.intent(Gui_Screen218PlusActivity.this).flags(FLAG_ACTIVITY_NEW_TASK).isAdult(true).mStory(story).start();
                }
            });

            mRecycleView.setAdapter(mAdapterLink);
        }
    }
}
