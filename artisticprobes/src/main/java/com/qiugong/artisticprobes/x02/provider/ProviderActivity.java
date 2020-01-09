package com.qiugong.artisticprobes.x02.provider;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.qiugong.artisticprobes.R;
import com.qiugong.artisticprobes.Tools;
import com.qiugong.artisticprobes.x02.aidl.Book;

/**
 * @author qzx 2019/7/14.
 */
public class ProviderActivity extends Activity {
    private static final String TAG = "ProviderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

//        Uri uri = Uri.parse("content://com.qiugong.artisticprobes.provider");
//        getContentResolver().query(uri, null, null, null, null);
//        getContentResolver().query(uri, null, null, null, null);
//        getContentResolver().query(uri, null, null, null, null);

        Uri bookUri = Uri.parse("content://com.qiugong.artisticprobes.provider/book");
        ContentValues values = new ContentValues();
        values.put("_id", 6);
        values.put("name", "程序设计的艺术");
        getContentResolver().insert(bookUri, values);

        Cursor bookCursor = getContentResolver().query(bookUri, new String[]{"_id", "name"},
                null, null, null);
        while (bookCursor.moveToNext()) {
            Book book = new Book();
            book.bookId = bookCursor.getInt(0);
            book.bookName = bookCursor.getString(1);
            Tools.log(TAG, "query book:" + book.toString());
        }
        bookCursor.close();
    }
}
