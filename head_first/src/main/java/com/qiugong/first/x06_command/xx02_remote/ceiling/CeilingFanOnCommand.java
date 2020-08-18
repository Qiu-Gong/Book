package com.qiugong.first.x06_command.xx02_remote.ceiling;

import com.qiugong.first.x06_command.xx02_remote.Command;

public class CeilingFanOnCommand implements Command {
	CeilingFan ceilingFan;

	public CeilingFanOnCommand(CeilingFan ceilingFan) {
		this.ceilingFan = ceilingFan;
	}
	public void execute() {
		ceilingFan.high();
	}

	@Override
	public void undo() {

	}
}
