package com.qiugong.first.x06_command.xx02_remote.hottub;

import com.qiugong.first.x06_command.xx02_remote.Command;

public class HottubOffCommand implements Command {
	Hottub hottub;

	public HottubOffCommand(Hottub hottub) {
		this.hottub = hottub;
	}

	public void execute() {
		hottub.setTemperature(98);
		hottub.off();
	}
	public void undo() {
		hottub.on();
	}
}
