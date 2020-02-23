package com.qiugong.glide.core.load.codec;

import com.qiugong.glide.core.memory.recycle.ArrayPool;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author qzx 20/2/23.
 */
public class MarkInputStream extends InputStream {

    // 64K
    private static final int BUFFER_SIZE_BYTES = 64 * 1024;

    private ArrayPool arrayPool;
    private InputStream inputStream;

    private int pos;
    private int markPos = -1;
    private int readCount;
    private byte[] buffer;

    MarkInputStream(InputStream inputStream, ArrayPool arrayPool) {
        this.inputStream = inputStream;
        this.arrayPool = arrayPool;
        buffer = arrayPool.get(BUFFER_SIZE_BYTES);
    }

    @Override
    public int read() throws IOException {
        // 被reset小于已读, 从buf中拿
        if (pos < readCount) {
            return buffer[pos]++;
        }

        //没有更多数据
        int read = inputStream.read();
        if (read == -1) {
            return read;
        }

        if (pos >= buffer.length) {
            resizeBuffer(0);
        }
        buffer[pos++] = (byte) read;
        readCount++;
        return read;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        //需要读取的总长度
        int count = len - off;
        //buf中的有效数据
        int available = readCount - pos;
        //满足读取的需求
        if (available >= count) {
            //复制数据
            System.arraycopy(buffer, pos, b, off, count);
            pos += count;
            return count;
        }

        //先将buf中的数据读进b
        if (available > 0) {
            System.arraycopy(buffer, pos, b, off, available);
            off += available;
            pos += available;
        }

        //还需要读取的数据长度 从原inputStream读取
        count = len - off;
        int readLen = inputStream.read(b, off, count);
        if (readLen == -1) {
            return readLen;
        }

        //没有足够的空间存放本次数据
        int i = pos + readLen - buffer.length;
        if (i > 0) {
            resizeBuffer(i);
        }
        System.arraycopy(b, off, buffer, pos, readLen);
        pos += readLen;

        //记录已经读取的总长度
        readCount += readLen;
        return readLen;
    }

    private void resizeBuffer(int len) {
        int newLen = buffer.length * 2 + len;
        byte[] newBuffer = arrayPool.get(newLen);

        //拷贝数据
        System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);

        //加入数组池
        arrayPool.put(buffer);
        buffer = newBuffer;
    }

    @Override
    public synchronized void reset() throws IOException {
        pos = markPos;
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    @Override
    public synchronized void mark(int readLimit) {
        markPos = pos;
    }

    @Override
    public void close() throws IOException {
        release();
        inputStream.close();
    }

    void release() {
        if (buffer != null) {
            arrayPool.put(buffer);
            buffer = null;
        }
    }
}
