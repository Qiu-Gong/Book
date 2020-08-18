package com.qiugong.first.x06_command.xx02_remote.light;

import com.qiugong.first.x06_command.xx02_remote.Command;
import com.qiugong.first.x06_command.xx02_remote.light.Light;

public class LightOnCommand implements Command {
    Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.on();
    }
}
