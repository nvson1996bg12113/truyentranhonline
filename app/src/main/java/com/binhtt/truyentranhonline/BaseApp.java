package com.binhtt.truyentranhonline;

import android.app.Application;

import com.binhtt.truyentranhonline.utils.TypefaceUtils;
import com.google.firebase.FirebaseApp;
import com.google.firebase.crash.FirebaseCrash;

import org.androidannotations.annotations.EApplication;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author binhtt <binhjdev@gmail.com>
 * @version bg.0.0
 * @since 08/08/2017
 */
public class BaseApp extends Application {
    private static final String TAG = BaseApp.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            FileInputStream is = new FileInputStream("/non-existent/file");
            int c = is.read();
            FirebaseCrash.log(String.valueOf(c));
        } catch (IOException e) {
            FirebaseCrash.report(e); // Generate report
        }
        // Set the font for all textview
        TypefaceUtils.overrideFont(getApplicationContext(), "SERIF", "fonts/HLcomic3_normal.ttf");
    }
}
