package com.qiugong.artisticprobes.x02.aidl;

import android.os.IBinder;
import android.os.RemoteException;

import java.util.List;

public interface BookManager extends android.os.IInterface {

    public static abstract class Stub extends android.os.Binder implements BookManager {

        // Binder 唯一标识
        private static final String DESCRIPTOR = "com.mtlab.library.filter.IBookManager";

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static BookManager asInterface(android.os.IBinder obj) {
            if ((obj == null)) {
                return null;
            }

            // 如果客户端和服务端进程是同一进程，返回本身
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin instanceof BookManager))) {
                return ((BookManager) iin);
            }

            // 如果不是同一进程，返回 Stub.Proxy 对象
            return new BookManager.Stub.Proxy(obj);
        }

        @Override
        public android.os.IBinder asBinder() {
            return this;
        }

        // 服务端线程池中
        @Override
        public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
            String descriptor = DESCRIPTOR;
            // code：确定目标方法
            // data：取出参数
            // reply：写入返回值
            // return false：请求失败
            switch (code) {
                case INTERFACE_TRANSACTION: {
                    reply.writeString(descriptor);
                    return true;
                }
                case TRANSACTION_getBookList: {
                    data.enforceInterface(descriptor);
                    java.util.List<Book> _result = this.getBookList();
                    reply.writeNoException();
                    // 写入返回结果
                    reply.writeTypedList(_result);
                    return true;
                }
                case TRANSACTION_addBook: {
                    data.enforceInterface(descriptor);
                    Book _arg0;
                    if ((0 != data.readInt())) {
                        _arg0 = Book.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    this.addBook(_arg0);
                    reply.writeNoException();
                    return true;
                }
                default: {
                    return super.onTransact(code, data, reply, flags);
                }
            }
        }

        // 客户端
        private static class Proxy implements BookManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                mRemote = remote;
            }

            @Override
            public android.os.IBinder asBinder() {
                return mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override
            public java.util.List<Book> getBookList() throws android.os.RemoteException {
                // 输入型 Parcel 对象 _data
                android.os.Parcel _data = android.os.Parcel.obtain();
                // 输出型 Parcel 对象 _reply
                android.os.Parcel _reply = android.os.Parcel.obtain();
                // 返回值对象 List
                java.util.List<Book> _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    // 远程过程调用 -> onTransact -> 挂起线程等待
                    mRemote.transact(Stub.TRANSACTION_getBookList, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.createTypedArrayList(Book.CREATOR);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override
            public void addBook(Book book) throws android.os.RemoteException {
                // 输入型 Parcel 对象 _data
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    // _data 写入 DESCRIPTOR
                    _data.writeInterfaceToken(DESCRIPTOR);
                    if ((book != null)) {
                        // _data 写入 1
                        _data.writeInt(1);
                        // book 写入 _data
                        book.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    mRemote.transact(Stub.TRANSACTION_addBook, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        // 整型id
        static final int TRANSACTION_getBookList = (android.os.IBinder.FIRST_CALL_TRANSACTION);
        static final int TRANSACTION_addBook = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    }

    public java.util.List<Book> getBookList() throws android.os.RemoteException;

    public void addBook(Book book) throws android.os.RemoteException;

    ///////////////////////////////////////////////////////////////////////////////////////////////

    // Binder 服务端
    BookManager.Stub mBinder = new BookManager.Stub() {

        @Override
        public List<Book> getBookList() throws RemoteException {
            return null;
        }

        @Override
        public void addBook(Book book) throws RemoteException {

        }
    };

    // DeathRecipient
    IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            // mBookManager.asBinder().unlinkToDeath()
        }
    };
    // binder.linkToDeath(mDeathRecipient, 0)
}
