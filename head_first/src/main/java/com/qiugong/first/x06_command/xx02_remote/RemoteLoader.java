package com.qiugong.first.x06_command.xx02_remote;

import com.qiugong.first.x06_command.xx02_remote.ceiling.CeilingFan;
import com.qiugong.first.x06_command.xx02_remote.ceiling.CeilingFanOffCommand;
import com.qiugong.first.x06_command.xx02_remote.ceiling.CeilingFanOnCommand;
import com.qiugong.first.x06_command.xx02_remote.garage.GarageDoor;
import com.qiugong.first.x06_command.xx02_remote.garage.GarageDoorDownCommand;
import com.qiugong.first.x06_command.xx02_remote.garage.GarageDoorUpCommand;
import com.qiugong.first.x06_command.xx02_remote.light.Light;
import com.qiugong.first.x06_command.xx02_remote.light.LightOffCommand;
import com.qiugong.first.x06_command.xx02_remote.light.LightOnCommand;
import com.qiugong.first.x06_command.xx02_remote.stereo.Stereo;
import com.qiugong.first.x06_command.xx02_remote.stereo.StereoOffCommand;
import com.qiugong.first.x06_command.xx02_remote.stereo.StereoOnWithCDCommand;

public class RemoteLoader {

    public static void main(String[] args) {
        RemoteControl remoteControl = new RemoteControl();

        Light livingRoomLight = new Light("Living Room");
        Light kitchenLight = new Light("Kitchen");
        CeilingFan ceilingFan = new CeilingFan("Living Room");
        GarageDoor garageDoor = new GarageDoor("");
        Stereo stereo = new Stereo("Living Room");

        LightOnCommand livingRoomLightOn = new LightOnCommand(livingRoomLight);
        LightOffCommand livingRoomLightOff = new LightOffCommand(livingRoomLight);

        LightOnCommand kitchenLightOn = new LightOnCommand(kitchenLight);
        LightOffCommand kitchenLightOff = new LightOffCommand(kitchenLight);

        CeilingFanOnCommand ceilingFanOn = new CeilingFanOnCommand(ceilingFan);
        CeilingFanOffCommand ceilingFanOff = new CeilingFanOffCommand(ceilingFan);

        GarageDoorUpCommand garageDoorUp = new GarageDoorUpCommand(garageDoor);
        GarageDoorDownCommand garageDoorDown = new GarageDoorDownCommand(garageDoor);

        StereoOnWithCDCommand stereoOnWithCD = new StereoOnWithCDCommand(stereo);
        StereoOffCommand stereoOff = new StereoOffCommand(stereo);

        remoteControl.setCommand(0, livingRoomLightOn, livingRoomLightOff);
        remoteControl.setCommand(1, kitchenLightOn, kitchenLightOff);
        remoteControl.setCommand(2, ceilingFanOn, ceilingFanOff);
        remoteControl.setCommand(3, garageDoorUp, garageDoorDown);
        remoteControl.setCommand(4, stereoOnWithCD, stereoOff);

        System.out.println(remoteControl);

        remoteControl.onButtonWasPushed(0);
        remoteControl.offButtonWasPushed(0);
        remoteControl.onButtonWasPushed(1);
        remoteControl.offButtonWasPushed(1);
        remoteControl.onButtonWasPushed(2);
        remoteControl.offButtonWasPushed(2);
        remoteControl.onButtonWasPushed(3);
        remoteControl.offButtonWasPushed(3);
        remoteControl.onButtonWasPushed(4);
        remoteControl.offButtonWasPushed(4);
    }
}
