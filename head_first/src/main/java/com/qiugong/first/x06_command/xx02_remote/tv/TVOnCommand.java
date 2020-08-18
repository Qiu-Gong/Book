package com.qiugong.first.x06_command.xx02_remote.tv;

import com.qiugong.first.x06_command.xx02_remote.Command;

public class TVOnCommand implements Command {
	TV tv;

	public TVOnCommand(TV tv) {
		this.tv= tv;
	}

	public void execute() {
		tv.on();
		tv.setInputChannel();
	}

	public void undo() {
		tv.off();
	}
}
