package com.qiugong.first.x06_command.xx02_remote.tv;

import com.qiugong.first.x06_command.xx02_remote.Command;

public class TVOffCommand implements Command {
	TV tv;

	public TVOffCommand(TV tv) {
		this.tv= tv;
	}

	public void execute() {
		tv.off();
	}

	public void undo() {
		tv.on();
	}
}
