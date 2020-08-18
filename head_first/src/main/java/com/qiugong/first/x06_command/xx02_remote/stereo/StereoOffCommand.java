package com.qiugong.first.x06_command.xx02_remote.stereo;

import com.qiugong.first.x06_command.xx02_remote.Command;

public class StereoOffCommand implements Command {
	Stereo stereo;
 
	public StereoOffCommand(Stereo stereo) {
		this.stereo = stereo;
	}
 
	public void execute() {
		stereo.off();
	}
}
