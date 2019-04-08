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
import static com.binhtt.truyentranhonline.ui.main.InitParams.URL_HOME;
import static com.binhtt.truyentranhonline.ui.main.InitParams.USER_AGENT;

/**
 * @author binhtt <binhjdev@gmail.com>
 * @version 1.0.0
 * @since 17/08/2017
 */
@EActivity(R.layout.activity_gui_screen_2)
public class Gui_Screen2HotActivity extends BaseActivity {
    @ViewById
    RecyclerView mRecycleView;
    @ViewById
    SwipeRefreshLayout mSwipeRefreshLayout;
    @ViewById
    Toolbar mToolbar;

    private Gui_Screen2Adapter mAdapterImage;
    private Gui_Screen3Adapter mAdapterLink;

    @Extra
    Story mStory;
    @Extra
    boolean imageOrLink;

    @Override
    protected void afterView() {
        setUpToolbar();

        setRecyclerView();

        getListData();

        swipeRefresh();
    }

    private void setUpToolbar() {
        // set toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(mStory.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void swipeRefresh() {
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getApplicationContext(), R.color.main));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (imageOrLink) {
                    if (ConnectionUtil.isConnected(getApplicationContext())) {
                        new StartPageTask().execute(mStory.getUrlLink());
                    } else {
                        showDialogInternet();
                    }
                } else {
                    if (ConnectionUtil.isConnected(getApplicationContext())) {
                        new GuiScreen2ImageTask().execute(mStory.getUrlLink());
                    } else {
                        showDialogInternet();
                    }
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getListData() {
        if (imageOrLink) {
            if (ConnectionUtil.isConnected(getApplicationContext())) {
                new StartPageTask().execute(mStory.getUrlLink());
            } else {
                showDialogInternet();
            }
        } else {
            if (ConnectionUtil.isConnected(getApplicationContext())) {
                new GuiScreen2ImageTask().execute(mStory.getUrlLink());
            } else {
                showDialogInternet();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setRecyclerView() {
        if (imageOrLink) {
            RecyclerViewUtils.Create().setUpVertical(getApplicationContext(), mRecycleView);
        } else {
            RecyclerViewUtils.Create().setUpGrid(getApplicationContext(), mRecycleView, 3);
        }
    }

    private class GuiScreen2ImageTask extends AsyncTask<String, Void, ArrayList<Story>> {

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
                    Elements sub = mDocument.select("ul.unstyled.clearfix > li.VI > div.VII");

                    for (Element element : sub) {
                        Story story = new Story();

                        Element titleSubject = element.getElementsByTag("img").first();
                        Element linkSubject = element.getElementsByTag("a").first();

                        // Parse to object
                        if (titleSubject != null) {
                            String title = titleSubject.attr("title");
                            story.setName(title);
                        }

                        if (titleSubject != null) {
                            String linkImage = titleSubject.attr("src");
                            story.setUrlImage(linkImage);
                        }

                        if (linkSubject != null) {
                            String urlLink = linkSubject.attr("href");
                            story.setUrlLink(URL_HOME + urlLink);
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
        protected void onPostExecute(final ArrayList<Story> stories) {
            super.onPostExecute(stories);
            dismissProgressDialog();
            mAdapterImage = new Gui_Screen2Adapter(getApplicationContext(), stories, new Gui_Screen2Adapter.OnClickStoryListener() {
                @Override
                public void onItemClick(Story story, int position) {
                    Gui_Screen3Activity_.intent(getApplicationContext()).flags(FLAG_ACTIVITY_NEW_TASK).mStory(story).start();
                }
            });

            mRecycleView.setAdapter(mAdapterImage);
        }
    }

    public class StartPageTask extends AsyncTask<String, Void, ArrayList<Story>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected ArrayList<Story> doInBackground(String... params) {
            Document document;

            ArrayList<Story> storyList = new ArrayList<>();

            try {
                document = (Document) Jsoup.connect(params[0]).userAgent(USER_AGENT).referrer(REFERRER).get();

                if (document != null) {
                    Elements sub = document.select("tbody > tr > td.ChI > a");

                    for (Element element : sub) {
                        Story story = new Story();

                        Element nameSubject = element.getElementsByTag("a").first();

                        // Parse object
                        if (nameSubject != null) {
                            String name = nameSubject.text();
                            story.setName(name);
                        }

                        if (nameSubject != null) {
                            String linkImage = nameSubject.attr("href");
                            story.setUrlImage(URL_HOME + linkImage);
                        }

                        storyList.add(story);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return storyList;
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
                    Gui_Screen4Activity_.intent(getApplicationContext()).flags(FLAG_ACTIVITY_NEW_TASK).mStory(story).start();
                }
            });

            mRecycleView.setAdapter(mAdapterLink);
        }
    }
}
