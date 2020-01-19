package com.qiugong.dongnao.x02;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author qzx 20/1/19.
 */
public class QMessageQueue {

    private ArrayBlockingQueue<QMessage> mBlockingQueue = new ArrayBlockingQueue<>(50);

    public void enqueueMessage(QMessage message) {
        mBlockingQueue.add(message);
    }

    public QMessage next() {
        try {
            return mBlockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
