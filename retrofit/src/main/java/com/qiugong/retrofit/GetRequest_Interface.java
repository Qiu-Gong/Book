package com.qiugong.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author qzx 20/2/25.
 */
public interface GetRequest_Interface {
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Call<Translation> getHello();
}
