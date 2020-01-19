package com.qiugong.dongnao.x02;

/**
 * @author qzx 20/1/19.
 */
public class QLooper {

    private static final ThreadLocal<QLooper> sThreadLocal = new ThreadLocal<>();
    QMessageQueue mQueue;

    public QLooper() {
        mQueue = new QMessageQueue();
    }

    public static void prepare() {
        if (sThreadLocal.get() != null)
            throw new RuntimeException("Only one Looper may be created per thread");
        sThreadLocal.set(new QLooper());
    }

    public static void loop() {
        QLooper looper = myLooper();
        QMessageQueue queue = looper.mQueue;

        for (; ; ) {
            QMessage message = queue.next();
            QHandler handler =  message.target;
            handler.dispatchMessage(message);
        }
    }

    public static QLooper myLooper() {
        return sThreadLocal.get();
    }
}
