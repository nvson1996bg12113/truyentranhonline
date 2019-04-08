package com.binhtt.truyentranhonline.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Patterns;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Copyright © 2016 BAP CO., LTD
 * Created by SONNT on 10/16/16.
 */

public class UrlUtil {

    /**
     * interface to set sync link listener
     */
    public interface OnSyncCallback {
        void onBeginSyncLink();

        void onSyncCallback(String link, String title, String description);

        void onSyncFail(String message);
    }

    /**
     * get Pat Form URI
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getPathFromUri(Context context, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public static File createFileFromInputStream(InputStream inputStream, String fileName) {
        try {
            File f = new File(fileName);
            f.setWritable(true, false);
            OutputStream outputStream = new FileOutputStream(f);
            byte buffer[] = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();
            return f;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * check is url
     *
     * @param url
     * @return
     */
    public static boolean isUrl(String url) {
        return Patterns.WEB_URL.matcher(url).matches();
    }
}
