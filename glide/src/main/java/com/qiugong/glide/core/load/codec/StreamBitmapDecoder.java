package com.qiugong.glide.core.load.codec;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.qiugong.glide.core.memory.recycle.ArrayPool;
import com.qiugong.glide.core.memory.recycle.BitmapPool;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author qzx 20/2/23.
 */
public class StreamBitmapDecoder implements ResourceDecoder<InputStream> {

    private final BitmapPool bitmapPool;
    private final ArrayPool arrayPool;

    public StreamBitmapDecoder(BitmapPool bitmapPool, ArrayPool arrayPool) {
        this.bitmapPool = bitmapPool;
        this.arrayPool = arrayPool;
    }

    @Override
    public boolean handles(InputStream source) throws IOException {
        return true;
    }

    @Override
    public Bitmap decode(InputStream source, int width, int height) throws IOException {
        MarkInputStream inputStream;
        if (source instanceof MarkInputStream) {
            inputStream = (MarkInputStream) source;
        } else {
            inputStream = new MarkInputStream(source, arrayPool);
        }
        inputStream.mark(0);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
        options.inJustDecodeBounds = false;

        int sourceWidth = options.outWidth;
        int sourceHeight = options.outHeight;
        int targetWidth = width < 0 ? sourceWidth : width;
        int targetHeight = height < 0 ? sourceHeight : height;
        float widthFactor = targetWidth / (float) sourceWidth;
        float heightFactor = targetHeight / (float) sourceHeight;
        float factor = Math.max(widthFactor, heightFactor);
        int outWidth = Math.round(factor * sourceWidth);
        int outHeight = Math.round(factor * sourceHeight);
        int widthScaleFactor = sourceWidth % outWidth == 0 ?
                sourceWidth / outWidth :
                sourceWidth / outWidth + 1;
        int heightScaleFactor = sourceHeight % outHeight == 0 ?
                sourceHeight / outHeight :
                sourceHeight / outHeight + 1;
        int sampleSize = Math.max(widthScaleFactor, heightScaleFactor);

        // 复用bitmap
        Bitmap bitmap = bitmapPool.get(outWidth, outHeight, Bitmap.Config.RGB_565);
        options.inSampleSize = Math.max(1, sampleSize);
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inBitmap = bitmap;
        options.inMutable = true;
        inputStream.reset();

        Bitmap result = BitmapFactory.decodeStream(inputStream, null, options);
        inputStream.release();
        return result;
    }
}
