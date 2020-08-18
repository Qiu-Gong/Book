package com.qiugong.first.x06_command.xx02_remote.garage;

import com.qiugong.first.x06_command.xx02_remote.Command;

public class GarageDoorDownCommand implements Command {
	GarageDoor garageDoor;

	public GarageDoorDownCommand(GarageDoor garageDoor) {
		this.garageDoor = garageDoor;
	}

	public void execute() {
		garageDoor.up();
	}

	@Override
	public void undo() {

	}
}
