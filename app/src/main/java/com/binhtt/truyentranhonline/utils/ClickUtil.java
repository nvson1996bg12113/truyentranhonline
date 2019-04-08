package com.binhtt.truyentranhonline.utils;

import android.os.Handler;

public final class ClickUtil {
    private static final int CLICK_LOCK_INTERVAL = 500;

    private static boolean sIsLocked;

    private static Runnable mClickLockRunnale = new Runnable() {
        @Override
        public void run() {
            sIsLocked = false;
        }
    };

    private static Handler mHandler = new Handler();

    public synchronized static boolean isLocked() {
        if (sIsLocked) return true;
        mHandler.postDelayed(mClickLockRunnale, CLICK_LOCK_INTERVAL);
        sIsLocked = true;
        return false;
    }
}
