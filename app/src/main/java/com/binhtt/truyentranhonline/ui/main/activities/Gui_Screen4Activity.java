package com.binhtt.truyentranhonline.ui.main.activities;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.binhtt.truyentranhonline.R;
import com.binhtt.truyentranhonline.ui.base.BaseActivity;
import com.binhtt.truyentranhonline.ui.main.fragment.Gui_ScreenSlideFragment;
import com.binhtt.truyentranhonline.ui.main.model.Story;
import com.binhtt.truyentranhonline.utils.ConnectionUtil;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.binhtt.truyentranhonline.ui.main.InitParams.REFERRER;
import static com.binhtt.truyentranhonline.ui.main.InitParams.USER_AGENT;

/**
 * @author binhtt <binhjdev@gmail.com>
 * @version bg.0.0
 * @since 14/08/2017
 */
@EActivity(R.layout.activity_gui_screen_4)
public class Gui_Screen4Activity extends BaseActivity {
    @ViewById
    ViewPager mViewPager;
    @Extra
    Story mStory;
    @Extra
    boolean isAdult;

    protected ArrayList<Story> mListImage;
    private List<Fragment> fragments;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void afterView() {
        getListData();
    }

    private void getListData() {
        if (isAdult) {
            if (ConnectionUtil.isConnected(this)) {
                new LoadAdultTask().execute(mStory.getUrlLink());
            } else {
                showDialogInternet();
            }
        } else {
            if (ConnectionUtil.isConnected(this)) {
                new LoadImageTask().execute(mStory.getUrlImage());
            } else {
                showDialogInternet();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public class LoadImageTask extends AsyncTask<String, Void, ArrayList<Story>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected ArrayList<Story> doInBackground(String... params) {
            Document document;
            mListImage = new ArrayList<>();

            try {
                document = (Document) Jsoup.connect(params[0]).userAgent(USER_AGENT).referrer(REFERRER).get();

                if (document != null) {
                    Elements subElements = document.select("div.TTCD > img.mg-img");

                    for (Element element : subElements) {
                        Story story = new Story();

                        Element linkImage = element.getElementsByTag("img").first();

                        if (linkImage != null) {
                            String link = linkImage.attr("src");
                            story.setUrlLink(link);
                        }

                        // Add to list
                        mListImage.add(story);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mListImage;
        }

        @Override
        protected void onPostExecute(ArrayList<Story> stories) {
            super.onPostExecute(stories);

            if (stories != null) {
                dismissProgressDialog();
            }

            fragments = getListFragments();

            mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
            mViewPager.setOffscreenPageLimit(3);
            mViewPager.setAdapter(mPagerAdapter);
        }
    }

    public class LoadAdultTask extends AsyncTask<String, Void, ArrayList<Story>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected ArrayList<Story> doInBackground(String... params) {
            Document document;
            mListImage = new ArrayList<>();

            try {
                document = (Document) Jsoup.connect(params[0]).userAgent(USER_AGENT).referrer(REFERRER).get();

                if (document != null) {
                    Elements subElements = document.select("div.reading-detail.box_doc > div.page-chapter");

                    for (Element element : subElements) {
                        Story story = new Story();

                        Element linkImage = element.getElementsByTag("img").first();

                        if (linkImage != null) {
                            String link = linkImage.attr("src");
                            story.setUrlLink(link);
                        }

                        // Add to list
                        mListImage.add(story);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mListImage;
        }

        @Override
        protected void onPostExecute(ArrayList<Story> stories) {
            super.onPostExecute(stories);
            dismissProgressDialog();

            fragments = getListFragments();

            mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
            mViewPager.setOffscreenPageLimit(3);
            mViewPager.setAdapter(mPagerAdapter);
        }
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> mFragments;

        public PagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments == null ? 0 : mFragments.size();
        }
    }

    private List<Fragment> getListFragments() {
        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < mListImage.size(); i++) {
            fragmentList.add(Gui_ScreenSlideFragment.newInstance(mListImage.get(i).getUrlLink()));
        }
        return fragmentList;
    }
}
