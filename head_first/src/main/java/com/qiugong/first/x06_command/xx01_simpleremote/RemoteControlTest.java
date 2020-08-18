package com.qiugong.first.x06_command.xx01_simpleremote;

public class RemoteControlTest {
    public static void main(String[] args) {
        SimpleRemoteControl remote = new SimpleRemoteControl();
        Light light = new Light();
        LightOnCommand lightOn = new LightOnCommand(light);

        GarageDoor garageDoor = new GarageDoor();
        GarageDoorOpenCommand garageOpen = new GarageDoorOpenCommand(garageDoor);

        // Light is on
        remote.setCommand(lightOn);
        remote.buttonWasPressed();

        // Garage Door is Open
		remote.setCommand(garageOpen);
		remote.buttonWasPressed();
    }
}
