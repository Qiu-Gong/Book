package com.qiugong.encryptapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * @author qzx 20/3/4.
 */
public class MyProvider extends ContentProvider {

    private static final String TAG = "MyProvider";

    @Override
    public boolean onCreate() {
        Log.i(TAG, "provider onCreate:" + getContext());
        Log.i(TAG, "provider onCreate:" + getContext().getApplicationContext());
        Log.i(TAG, "provider onCreate:" + getContext().getApplicationInfo().className);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.i(TAG, "provider delete:" + getContext());
        Log.i(TAG, "provider delete:" + getContext().getApplicationContext());
        Log.i(TAG, "provider delete:" + getContext().getApplicationInfo().className);
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
