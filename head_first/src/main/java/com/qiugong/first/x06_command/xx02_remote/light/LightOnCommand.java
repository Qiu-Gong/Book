package com.qiugong.first.x06_command.xx02_remote.light;

import com.qiugong.first.x06_command.xx02_remote.Command;

public class LightOnCommand implements Command {
	Light light;
	int level;
	public LightOnCommand(Light light) {
		this.light = light;
	}
 
	public void execute() {
        level = light.getLevel();
		light.on();
	}
 
	public void undo() {
		light.dim(level);
	}
}
