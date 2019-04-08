package com.binhtt.truyentranhonline.ui.main.fragment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binhtt.truyentranhonline.R;
import com.binhtt.truyentranhonline.ui.base.BaseFragment;
import com.binhtt.truyentranhonline.ui.main.activities.Gui_Screen218PlusActivity_;
import com.binhtt.truyentranhonline.ui.main.adapter.Gui_Screen2Adapter;
import com.binhtt.truyentranhonline.ui.main.model.Story;
import com.binhtt.truyentranhonline.utils.ConnectionUtil;
import com.binhtt.truyentranhonline.utils.RecyclerViewUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import static com.binhtt.truyentranhonline.ui.main.InitParams.REFERRER;
import static com.binhtt.truyentranhonline.ui.main.InitParams.URL_ADULT_PAGE;
import static com.binhtt.truyentranhonline.ui.main.InitParams.USER_AGENT;

/**
 * @author binhtt <binhjdev@gmail.com>
 * @version 1.0.0
 * @since 18/08/2017
 */

public class Gui_Screen18PlusFragment extends BaseFragment {
    RecyclerView mRecycleView;
    SwipeRefreshLayout mSwipeRefreshLayout;

    private Gui_Screen2Adapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.gui_screen_main_fragment, container, false);

        setRecyclerView(layout);

        getListData();

        swipeRefresh(layout);

        return layout;
    }

    private void swipeRefresh(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.mSwipeRefreshLayout);

        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.main));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (ConnectionUtil.isConnected(getContext())) {
                    new Screen18PlusTask().execute(URL_ADULT_PAGE);
                } else {
                    getBaseActivity().showDialogInternet();
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getListData() {
        if (ConnectionUtil.isConnected(getContext())) {
            new Screen18PlusTask().execute(URL_ADULT_PAGE);
        } else {
            getBaseActivity().showDialogInternet();
        }
    }

    private void setRecyclerView(View view) {
        mRecycleView = (RecyclerView) view.findViewById(R.id.mRecycleView);
        RecyclerViewUtils.Create().setUpGrid(getContext(), mRecycleView, 3);
    }

    public class Screen18PlusTask extends AsyncTask<String, Void, ArrayList<Story>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getBaseActivity().showProgressDialog();

        }

        @Override
        protected ArrayList<Story> doInBackground(String... params) {
            Document mDocument;
            ArrayList<Story> mList = new ArrayList<>();

            try {
                mDocument = (Document) Jsoup.connect(params[0]).userAgent(USER_AGENT).referrer(REFERRER).get();
                if (mDocument != null) {
                    Elements sub = mDocument.select("div.row > div.item > figure.clearfix > div.image");

                    for (Element element : sub) {
                        Story story = new Story();
                        Element linkImageSubject = element.getElementsByTag("img").first();
                        Element linkSubject = element.getElementsByTag("a").first();

                        // Parse to object
                        if (linkSubject != null) {
                            String title = linkSubject.attr("title").substring(13);
                            story.setName(title);
                        }

                        if (linkImageSubject != null) {
                            String linkImage = linkImageSubject.attr("data-original");
                            story.setUrlImage(linkImage);
                        }

                        if (linkSubject != null) {
                            String urlLink = linkSubject.attr("href");
                            story.setUrlLink(urlLink);
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

            if (stories != null) {
                getBaseActivity().dismissProgressDialog();
            }

            mAdapter = new Gui_Screen2Adapter(getContext(), stories, new Gui_Screen2Adapter.OnClickStoryListener() {
                @Override
                public void onItemClick(Story story, int position) {
                    Gui_Screen218PlusActivity_.intent(Gui_Screen18PlusFragment.this).mStory(story).start();
                }
            });

            mRecycleView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
