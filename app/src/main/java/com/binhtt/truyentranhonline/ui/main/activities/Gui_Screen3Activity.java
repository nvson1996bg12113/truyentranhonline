package com.binhtt.truyentranhonline.ui.main.activities;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.widget.ImageView;
import android.widget.TextView;

import com.binhtt.truyentranhonline.R;
import com.binhtt.truyentranhonline.ui.base.BaseActivity;
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

import static com.binhtt.truyentranhonline.ui.main.InitParams.REFERRER;
import static com.binhtt.truyentranhonline.ui.main.InitParams.URL_HOME;
import static com.binhtt.truyentranhonline.ui.main.InitParams.USER_AGENT;

/**
 * @author binhtt <binhjdev@gmail.com>
 * @version bg.0.0
 * @since 08/08/2017
 */
@EActivity(R.layout.activity_gui_screen_3)
public class Gui_Screen3Activity extends BaseActivity {
    @ViewById
    Toolbar mToolbar;
    @ViewById
    RecyclerView mRecyclerView;
    @ViewById
    TextView mTvTimePost;
    @ViewById
    TextView mTvComment;

    @Extra
    Story mStory;
    private String mTimePost;
    private String mComment;

    private Gui_Screen3Adapter mAdapter;

    @Override
    protected void afterView() {
        setUpToolbar();

        setRecyclerView();

        getListChapter();
    }

    private void setRecyclerView() {
        RecyclerViewUtils.Create().setUpVertical(this, mRecyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    private void setUpToolbar() {
        // set toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(mStory.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getListChapter() {
        if (ConnectionUtil.isConnected(this)) {
            new StartPageTask().execute(mStory.getUrlLink());
        } else {
            showDialogInternet();
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
                    Element timePost = document.select("ul.Meta > li").first();
                    Element comment = document.select("ul.Meta > li > a").first();

                    if (timePost != null) {
                        mTimePost = timePost.text();
                    }

                    if (comment != null) {
                        mComment = comment.text();
                    }

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

            mAdapter = new Gui_Screen3Adapter(getApplicationContext(), stories, new Gui_Screen3Adapter.StartPageClickItemListener() {
                @Override
                public void onItemClick(Story story, int position) {
                    Gui_Screen4Activity_.intent(Gui_Screen3Activity.this).mStory(story).start();
                }
            });

            mTvTimePost.setText(mTimePost);
            mTvComment.setText(mComment);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
