package qiugong.com.myapplication.aidl;
import qiugong.com.myapplication.aidl.Book;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}
