package com.qiugong.first.x06_command.xx02_remote.stereo;

import com.qiugong.first.x06_command.xx02_remote.Command;

public class StereoOnCommand implements Command {
	Stereo stereo;

	public StereoOnCommand(Stereo stereo) {
		this.stereo = stereo;
	}

	public void execute() {
		stereo.on();
	}

	public void undo() {
		stereo.off();
	}
}
