package com.qiugong.first.x12_combining.mvc;

import com.qiugong.first.x12_combining.mvc.heart.HeartController;
import com.qiugong.first.x12_combining.mvc.heart.HeartModel;

public class DJTestDrive {

    public static void main(String[] args) {
//        BeatModelInterface model = new BeatModel();
//        ControllerInterface controller = new BeatController(model);

        HeartModel heartModel = new HeartModel();
        ControllerInterface model = new HeartController(heartModel);
    }
}
