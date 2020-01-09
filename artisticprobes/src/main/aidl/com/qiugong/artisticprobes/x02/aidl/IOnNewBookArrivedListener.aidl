package com.qiugong.artisticprobes.x02.aidl;
import com.qiugong.artisticprobes.x02.aidl.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}
