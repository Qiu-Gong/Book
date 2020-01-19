package com.qiugong.dongnao.x02;

/**
 * @author qzx 20/1/19.
 */
public class QHandler {

    private final QLooper mLooper;
    private QMessageQueue mQueue;

    public QHandler() {
        mLooper = QLooper.myLooper();
        if (mLooper == null) {
            throw new RuntimeException("Can't create handler inside thread that has not called Looper.prepare()");
        }
        mQueue = mLooper.mQueue;
    }

    public void handleMessage(QMessage message) {
    }

    public void sendMessage(QMessage message) {
        enqueueMessage(message);
    }

    private void enqueueMessage(QMessage message) {
        message.target = this;
        mQueue.enqueueMessage(message);
    }

    public void dispatchMessage(QMessage message) {
        handleMessage(message);
    }
}
