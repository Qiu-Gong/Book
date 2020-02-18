package com.qiugong.okhttp.net;

/**
 * @author qzx 20/2/18.
 */
public interface Callback {

    void onFailure(Call call, Throwable throwable);

    void onResponse(Call call, Response response);
}
