package com.qiugong.glide.core.request;

import java.io.File;

/**
 * @author qzx 20/2/20.
 */
public interface ModelTypes {

    RequestBuilder load(String path);

    RequestBuilder load(File file);
}
