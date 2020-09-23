package com.qiugong.java.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.net.URL;
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

    public static void listAllFiles(File dir) {
        if (dir == null || !dir.exists()) {
            return;
        }
        if (dir.isFile()) {
            System.out.println(dir.getName());
            return;
        }
        for (File file : dir.listFiles()) {
            listAllFiles(file);
        }
    }

    public static void copyFile(String src, String dist) throws IOException {
        FileInputStream in = new FileInputStream(src);
        FileOutputStream out = new FileOutputStream(dist);
        byte[] buffer = new byte[20 * 1024];
        int cnt;
        // read() 最多读取 buffer.length 个字节
        // 返回的是实际读取的个数
        // 返回 -1 的时候表示读到 eof，即文件尾
        while ((cnt = in.read(buffer, 0, buffer.length)) != -1) {
            out.write(buffer, 0, cnt);
        }
        in.close();
        out.close();
    }

    public static void readFileContent(String filePath) throws IOException {
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
        // 装饰者模式使得 BufferedReader 组合了一个 Reader 对象
        // 在调用 BufferedReader 的 close() 方法时会去调用 Reader 的 close() 方法
        // 因此只要一个 close() 调用即可
        bufferedReader.close();
    }

    public static void serializable() throws Exception {
        A a1 = new A(123, "abc");
        String objectFile = "file/a1";
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(objectFile));
        objectOutputStream.writeObject(a1);
        objectOutputStream.close();
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(objectFile));
        A a2 = (A) objectInputStream.readObject();
        objectInputStream.close();
        System.out.println(a2);
    }

    public static void readUrl() throws Exception {
        URL url = new URL("http://www.baidu.com");
        /* 字节流 */
        InputStream is = url.openStream();
        /* 字符流 */
        InputStreamReader isr = new InputStreamReader(is, "utf-8");

        /* 提供缓存功能 */
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        br.close();
    }

    private static class A implements Serializable {
        private int x;
        private String y;

        A(int x, String y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "x = " + x + " " + "y = " + y;
        }
    }
}