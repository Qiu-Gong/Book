package com.qiugong.java.basis;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

/**
 * @author qzx 20/1/13.
 */
public class JavaIo {

    public static void main(String[] args) {

        long time = System.currentTimeMillis();
        nioFast("", "");
        System.out.println(System.currentTimeMillis() - time);
    }

    public static void nioFast(String src, String dist) {
        FileInputStream fin = null;
        FileChannel fcin = null;
        FileOutputStream fout = null;
        FileChannel fcout = null;
        try {
            /* 获得源文件的输入字节流 */
            fin = new FileInputStream(src);
            /* 获取输入字节流的文件通道 */
            fcin = fin.getChannel();
            /* 获取目标文件的输出字节流 */
            fout = new FileOutputStream(dist);
            /* 获取输出字节流的文件通道 */
            fcout = fout.getChannel();
            /* 为缓冲区分配 1024 个字节 */
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

            while (true) {
                /* 从输入通道中读取数据到缓冲区中 */
                int r = fcin.read(buffer);
                /* read() 返回 -1 表示 EOF */
                if (r == -1) break;
                /* 切换读写 */
                buffer.flip();
                /* 把缓冲区的内容写入输出文件中 */
                fcout.write(buffer);
                /* 清空缓冲区 */
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fin != null) {
                    fin.close();
                }
                if (fout != null) {
                    fout.close();
                }
                if (fcin != null) {
                    fcin.close();
                }
                if (fcout != null) {
                    fcout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void nioMemory(String src, String dist) {
        FileInputStream fin = null;
        RandomAccessFile ros = null;
        FileChannel fcin = null;
        FileChannel fcout = null;
        try {
            /* 获得源文件的输入字节流 */
            fin = new FileInputStream(src);
            /* 获取输入字节流的文件通道 */
            fcin = fin.getChannel();
            /* 获取目标文件的输出字节流 */
            ros = new RandomAccessFile(dist, "rw");
            /* 获取输出字节流的文件通道 */
            fcout = ros.getChannel();
            IntBuffer iIb = fcin.map(FileChannel.MapMode.READ_ONLY, 0, fcin.size()).asIntBuffer();
            IntBuffer oIb = fcout.map(FileChannel.MapMode.READ_WRITE, 0, fcout.size()).asIntBuffer();

            while (iIb.hasRemaining()) {
                int read = iIb.get();
                oIb.put(read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fin != null) {
                    fin.close();
                }
                if (ros != null) {
                    ros.close();
                }
                if (fcin != null) {
                    fcin.close();
                }
                if (fcout != null) {
                    fcout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
