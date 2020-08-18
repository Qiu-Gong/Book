package com.qiugong.first.x06_command.xx02_remote.light;

import com.qiugong.first.x06_command.xx02_remote.Command;

public class LightOffCommand implements Command {
	Light light;
	int level;
	public LightOffCommand(Light light) {
		this.light = light;
	}
 
	public void execute() {
        level = light.getLevel();
		light.off();
	}
 
	public void undo() {
		light.dim(level);
	}
}
