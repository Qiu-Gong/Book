package com.qiugong.rxjava.core;

/**
 * @author qzx 20/2/14.
 */
public interface Function<T, R> {
    R apply(T t);
}
