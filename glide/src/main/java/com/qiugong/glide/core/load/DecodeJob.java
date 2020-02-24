package com.qiugong.glide.core.load;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.qiugong.glide.core.Glide;
import com.qiugong.glide.core.key.Key;
import com.qiugong.glide.core.load.codec.LoadPath;
import com.qiugong.glide.core.load.generator.DataCacheGenerator;
import com.qiugong.glide.core.load.generator.DataGenerator;
import com.qiugong.glide.core.load.generator.DataGeneratorListener;
import com.qiugong.glide.core.load.generator.SourceGenerator;
import com.qiugong.glide.core.memory.active.Resource;
import com.qiugong.glide.core.memory.disk.DiskCache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author qzx 20/2/21.
 */
public class DecodeJob implements Runnable, DataGeneratorListener {

    private enum Stage {
        INITIALIZE,
        DATA_CACHE,
        SOURCE,
        FINISHED
    }

    private static final String TAG = "DecodeJob";
    private final Context context;
    private final Object model;
    private final int width;
    private final int height;
    private final DecodeJobListener decodeJobListener;

    private DataGenerator currentGenerator;
    private Key sourceKey;
    private Stage stage;

    private boolean isCancelled;
    private boolean isCallbackNotified;

    DecodeJob(Context context, Object model,
              int width, int height, DecodeJobListener listener) {
        this.context = context;
        this.model = model;
        this.width = width;
        this.height = height;
        this.decodeJobListener = listener;
    }

    @Override
    public void run() {
        Log.d(TAG, "run: 开始加载数据");
        if (isCancelled) {
            Log.d(TAG, "run: 取消加载数据");
            decodeJobListener.onLoadFailed(new IOException("Canceled"));
            return;
        }

        stage = getNextStage(Stage.INITIALIZE);
        currentGenerator = getNextGenerator(stage);
        runGenerators();
    }

    void cancel() {
        isCancelled = true;
        if (currentGenerator != null) {
            currentGenerator.cancel();
        }
    }

    private Stage getNextStage(Stage stage) {
        switch (stage) {
            case INITIALIZE:
                return Stage.DATA_CACHE;
            case DATA_CACHE:
                return Stage.SOURCE;
            case SOURCE:
            case FINISHED:
                return Stage.FINISHED;
            default:
                throw new IllegalArgumentException("Unrecognized stage:" + stage);
        }
    }

    private DataGenerator getNextGenerator(Stage stage) {
        switch (stage) {
            case DATA_CACHE:
                Log.d(TAG, "getNextGenerator: 使用磁盘加载器");
                return new DataCacheGenerator(Glide.get(context), model, this);
            case SOURCE:
                Log.d(TAG, "getNextGenerator: 使用资源加载器");
                return new SourceGenerator(Glide.get(context), model, this);
            case FINISHED:
                return null;
            default:
                throw new IllegalArgumentException("Unrecognized stage:" + stage);
        }
    }

    private void runGenerators() {
        boolean isStarted = false;
        while (!isCancelled && currentGenerator != null) {
            // 开始获取数据
            isStarted = currentGenerator.startNext();
            if (isStarted) {
                break;
            }

            stage = getNextStage(stage);
            if (stage == Stage.FINISHED) {
                Log.d(TAG, "runGenerators: 状态结束,没有加载器能够加载对应数据");
                break;
            }
            currentGenerator = getNextGenerator(stage);
        }
        if ((stage == Stage.FINISHED || isCancelled) && !isStarted) {
            notifyFailed();
        }
    }

    private void notifyFailed() {
        Log.d(TAG, "notifyFailed: 加载失败");
        if (!isCallbackNotified) {
            isCallbackNotified = true;
            decodeJobListener.onLoadFailed(new RuntimeException("Failed to load resource"));
        }
    }

    /**
     * 加载器加载完成后回调
     */
    @Override
    public void onDataReady(Key key, Object data, DataSource dataSource) {
        Log.d(TAG, "onDataReady: 加载成功,开始解码数据");
        this.sourceKey = key;
        runLoadPath(data, dataSource);
    }

    @Override
    public void onDataFetcherFailed(Key sourceKey, Exception e) {
        Log.d(TAG, "onDataFetcherFailed: 加载失败，尝试使用下一个加载器:" + e.getMessage());
        runGenerators();
    }

    private <Data> void runLoadPath(Data data, DataSource dataSource) {
        LoadPath<Data> loadPath = Glide.get(context).getRegistry()
                .getLoadPath((Class<Data>) data.getClass());
        Bitmap bitmap = loadPath.runLoad(data, width, height);
        if (bitmap != null) {
            Log.d(TAG, "runLoadPath: 解码成功回调");
            notifyComplete(bitmap, dataSource);
        } else {
            Log.d(TAG, "runLoadPath: 解码失败，尝试使用下一个加载器");
            runGenerators();
        }
    }

    private void notifyComplete(final Bitmap bitmap, DataSource dataSource) {
        if (dataSource == DataSource.REMOTE) {
            Glide.get(context)
                    .getDiskCache()
                    .put(sourceKey, new DiskCache.Writer() {
                        @Override
                        public boolean write(File file) {
                            try {
                                FileOutputStream fileOutputStream = new FileOutputStream(file);
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
                                fileOutputStream.close();
                                return true;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return false;
                        }
                    });
        }

        Resource resource = new Resource(bitmap);
        decodeJobListener.onResourceReady(resource);
    }
}
