package com.qiugong.first.x06_command.xx02_remote.ceiling;

import com.qiugong.first.x06_command.xx02_remote.Command;

public class CeilingFanOffCommand implements Command {
	CeilingFan ceilingFan;

	public CeilingFanOffCommand(CeilingFan ceilingFan) {
		this.ceilingFan = ceilingFan;
	}
	public void execute() {
		ceilingFan.off();
	}
}
