package com.qiugong.okhttp.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qzx 20/2/18.
 */
public class HttpCodec {

    private static final int CR = 13;
    private static final int LF = 10;
    private static final String CRLF = "\r\n";
    private static final String SPACE = " ";
    private static final String VERSION = "HTTP/1.1";
    private static final String COLON = ":";
    public static final String HEAD_HOST = "Host";
    public static final String HEAD_CONNECTION = "Connection";
    public static final String HEAD_CONTENT_TYPE = "Content-Type";
    public static final String HEAD_CONTENT_LENGTH = "Content-Length";
    public static final String HEAD_TRANSFER_ENCODING = "Transfer-Encoding";
    public static final String HEAD_VALUE_KEEP_ALIVE = "Keep-Alive";
    public static final String HEAD_VALUE_CHUNK = "chunked";

    private final ByteBuffer byteBuffer;

    public HttpCodec() {
        byteBuffer = ByteBuffer.allocate(10 * 1024);
    }

    void writeRequest(OutputStream output, Request request) throws IOException {
        StringBuilder protocol = new StringBuilder();

        protocol.append(request.method());
        protocol.append(SPACE);
        protocol.append(request.url().getFile());
        protocol.append(SPACE);
        protocol.append(VERSION);
        protocol.append(CRLF);

        Map<String, String> headers = request.headers();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            protocol.append(entry.getKey());
            protocol.append(COLON);
            protocol.append(SPACE);
            protocol.append(entry.getValue());
            protocol.append(CRLF);
        }
        protocol.append(CRLF);

        RequestBody body = request.body();
        if (body != null) {
            protocol.append(body.body());
        }

        output.write(protocol.toString().getBytes());
        output.flush();
    }

    public Map<String, String> readHeaders(InputStream input) throws IOException {
        HashMap<String, String> headers = new HashMap<>();
        while (true) {
            String line = readLine(input);
            if (isEmptyLine(line)) {
                break;
            }
            int index = line.indexOf(":");
            if (index > 0) {
                String key = line.substring(0, index);
                String value = line.substring(index + 2, line.length() - 2);
                headers.put(key, value);
            }
        }
        return headers;
    }

    public byte[] readBytes(InputStream input, int length) throws IOException {
        byte[] bytes = new byte[length];
        int readCnt = 0;
        while (true) {
            readCnt += input.read(bytes, readCnt, length - readCnt);
            if (readCnt == length) {
                return bytes;
            }
        }
    }

    public String readChunk(InputStream input) throws IOException {
        int length = -1;
        boolean isEmptyData = false;
        StringBuilder builder = new StringBuilder();

        while (true) {
            if (length < 0) {
                String line = readLine(input);
                line = line.substring(0, line.length() - 2);
                length = Integer.valueOf(line, 16);
                isEmptyData = length == 0;
            } else {
                byte[] bytes = readBytes(input, length + 2);
                builder.append(new String(bytes));
                length = -1;
                if (isEmptyData) {
                    return builder.toString();
                }
            }
        }
    }

    public String readLine(InputStream input) throws IOException {
        try {
            byte bytes;
            boolean isEofLine = false;
            byteBuffer.clear();
            byteBuffer.mark();

            while ((bytes = (byte) input.read()) != -1) {
                byteBuffer.put(bytes);

                if (bytes == CR) {
                    isEofLine = true;
                } else if (isEofLine) {
                    if (bytes == LF) {

                        byte[] lineBytes = new byte[byteBuffer.position()];
                        byteBuffer.reset();
                        byteBuffer.get(lineBytes);

                        byteBuffer.clear();
                        byteBuffer.mark();
                        return new String(lineBytes);
                    }
                    isEofLine = false;
                }
            }
        } catch (Exception e) {
            throw new IOException(e);
        }

        throw new IOException("Response Read Line.");
    }

    private boolean isEmptyLine(String line) {
        return line.equals("\r\n");
    }
}
