package com.qiugong.first.x06_command.xx02_remote.light;

import com.qiugong.first.x06_command.xx02_remote.Command;
import com.qiugong.first.x06_command.xx02_remote.light.Light;

public class LightOffCommand implements Command {
    Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.off();
    }
}
