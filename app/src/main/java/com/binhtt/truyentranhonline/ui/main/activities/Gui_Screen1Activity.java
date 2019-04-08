package com.binhtt.truyentranhonline.ui.main.activities;


import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.binhtt.truyentranhonline.R;
import com.binhtt.truyentranhonline.ui.base.BaseActivity;
import com.binhtt.truyentranhonline.ui.main.fragment.Gui_Screen18PlusFragment;
import com.binhtt.truyentranhonline.ui.main.fragment.Gui_ScreenAboutFragment;
import com.binhtt.truyentranhonline.ui.main.fragment.Gui_ScreenHotFragment;
import com.binhtt.truyentranhonline.ui.main.fragment.Gui_ScreenMainFragment;
import com.binhtt.truyentranhonline.ui.main.fragment.Gui_ScreenManhwaFragment;
import com.binhtt.truyentranhonline.ui.main.fragment.Gui_ScreenNewFragment;
import com.binhtt.truyentranhonline.ui.main.fragment.Gui_ScreenSportFragment;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * @author binhtt <binhjdev@gmail.com>
 * @version bg.0.0
 * @since 31/07/2017
 */
@EActivity(R.layout.activity_gui_screen_1)
public class Gui_Screen1Activity extends BaseActivity {
    @ViewById
    Toolbar mToolbar;
    @ViewById
    DrawerLayout mDrawerLayout;
    @ViewById
    NavigationView mNavigationView;

    ActionBarDrawerToggle mActionBarDrawerToggle;

    private Snackbar mToastExit;

    @Override
    protected void afterView() {
        setUpSnackbar();

        setUpToolbar();

        setUpDrawerContent(mNavigationView);

        mActionBarDrawerToggle = setUpDrawerToggle();

        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);

        setFirstItemNavigationView();
    }

    private void setUpSnackbar() {
        mToastExit = Snackbar.make(mDrawerLayout, getResources().getText(R.string.back_again_to_exit), Snackbar.LENGTH_SHORT);
        mToastExit.setActionTextColor(getResources().getColor(R.color.main));
        View mySbView = mToastExit.getView();
        TextView textView = (TextView) mySbView.findViewById(android.support.design.R.id.snackbar_text);

        // We can apply the property of TextView
        textView.setTextColor(Color.WHITE);
    }

    @Override
    public void onBackPressed() {
        boolean isExit = mToastExit.getView().isShown();
        if (!isExit) {
            mToastExit.show();
        } else {
            finish();
        }
    }

    private void setUpToolbar() {
        // set toolbar
        setSupportActionBar(mToolbar);
    }

    private void setUpDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });
    }

    private void setFirstItemNavigationView() {
        mNavigationView.setCheckedItem(R.id.mHomeFragment);
        mNavigationView.getMenu().performIdentifierAction(R.id.mHomeFragment, 0);
    }

    private ActionBarDrawerToggle setUpDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
    }

    private void selectDrawerItem(MenuItem item) {
        // Create new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch (item.getItemId()) {
            case R.id.mHomeFragment:
                fragmentClass = Gui_ScreenMainFragment.class;
                break;
            case R.id.mTruyenTranhHot:
                fragmentClass = Gui_ScreenHotFragment.class;
                break;
            case R.id.mTruyenTranhNew:
                fragmentClass = Gui_ScreenNewFragment.class;
                break;
            case R.id.mTruyenTranhAdult:
                fragmentClass = Gui_Screen18PlusFragment.class;
                break;
            case R.id.mTruyenTranhManhWa:
                fragmentClass = Gui_ScreenManhwaFragment.class;
                break;
            case R.id.mTruyenTranhSport:
                fragmentClass = Gui_ScreenSportFragment.class;
                break;
            case R.id.mInfoFragment:
                fragmentClass = Gui_ScreenAboutFragment.class;
                break;
            default:
                fragmentClass = Gui_ScreenMainFragment.class;
                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.mFrameLayoutContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        item.setChecked(true);

        setTitle(item.getTitle());

        mDrawerLayout.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mHomeFragment) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }

        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
}
