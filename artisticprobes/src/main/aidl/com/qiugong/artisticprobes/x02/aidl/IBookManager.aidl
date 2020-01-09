package com.qiugong.artisticprobes.x02.aidl;
import com.qiugong.artisticprobes.x02.aidl.Book;
import com.qiugong.artisticprobes.x02.aidl.IOnNewBookArrivedListener;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}