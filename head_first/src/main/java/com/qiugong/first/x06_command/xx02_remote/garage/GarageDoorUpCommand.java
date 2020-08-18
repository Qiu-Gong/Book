package com.qiugong.first.x06_command.xx02_remote.garage;

import com.qiugong.first.x06_command.xx02_remote.Command;

public class GarageDoorUpCommand implements Command {
	GarageDoor garageDoor;

	public GarageDoorUpCommand(GarageDoor garageDoor) {
		this.garageDoor = garageDoor;
	}

	public void execute() {
		garageDoor.up();
	}
}
