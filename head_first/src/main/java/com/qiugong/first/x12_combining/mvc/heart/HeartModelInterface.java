package com.qiugong.first.x12_combining.mvc.heart;

import com.qiugong.first.x12_combining.mvc.BPMObserver;
import com.qiugong.first.x12_combining.mvc.BeatObserver;

public interface HeartModelInterface {
    int getHeartRate();

    void registerObserver(BeatObserver o);

    void removeObserver(BeatObserver o);

    void registerObserver(BPMObserver o);

    void removeObserver(BPMObserver o);
}
