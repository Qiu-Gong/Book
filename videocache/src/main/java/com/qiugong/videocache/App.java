package com.qiugong.videocache;

import android.app.Application;
import android.content.Context;

import com.danikula.videocache.HttpProxyCacheServer;

/**
 * @author qzx 20/2/25.
 */
public class App extends Application {
    private HttpProxyCacheServer proxy;

    public HttpProxyCacheServer getProxy(Context context) {
        if (proxy == null) {
            proxy = new HttpProxyCacheServer.Builder(this).build();
        }
        return proxy;
    }
}
