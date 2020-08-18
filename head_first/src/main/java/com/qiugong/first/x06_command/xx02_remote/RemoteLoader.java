package com.qiugong.first.x06_command.xx02_remote;

import com.qiugong.first.x06_command.xx02_remote.hottub.Hottub;
import com.qiugong.first.x06_command.xx02_remote.hottub.HottubOffCommand;
import com.qiugong.first.x06_command.xx02_remote.hottub.HottubOnCommand;
import com.qiugong.first.x06_command.xx02_remote.light.Light;
import com.qiugong.first.x06_command.xx02_remote.light.LightOffCommand;
import com.qiugong.first.x06_command.xx02_remote.light.LightOnCommand;
import com.qiugong.first.x06_command.xx02_remote.stereo.Stereo;
import com.qiugong.first.x06_command.xx02_remote.stereo.StereoOffCommand;
import com.qiugong.first.x06_command.xx02_remote.stereo.StereoOnCommand;
import com.qiugong.first.x06_command.xx02_remote.tv.TV;
import com.qiugong.first.x06_command.xx02_remote.tv.TVOffCommand;
import com.qiugong.first.x06_command.xx02_remote.tv.TVOnCommand;

public class RemoteLoader {

	public static void main(String[] args) {

		RemoteControl remoteControl = new RemoteControl();

		Light light = new Light("Living Room");
		TV tv = new TV("Living Room");
		Stereo stereo = new Stereo("Living Room");
		Hottub hottub = new Hottub();
 
		LightOnCommand lightOn = new LightOnCommand(light);
		StereoOnCommand stereoOn = new StereoOnCommand(stereo);
		TVOnCommand tvOn = new TVOnCommand(tv);
		HottubOnCommand hottubOn = new HottubOnCommand(hottub);
		LightOffCommand lightOff = new LightOffCommand(light);
		StereoOffCommand stereoOff = new StereoOffCommand(stereo);
		TVOffCommand tvOff = new TVOffCommand(tv);
		HottubOffCommand hottubOff = new HottubOffCommand(hottub);

		Command[] partyOn = { lightOn, stereoOn, tvOn, hottubOn};
		Command[] partyOff = { lightOff, stereoOff, tvOff, hottubOff};
  
		MacroCommand partyOnMacro = new MacroCommand(partyOn);
		MacroCommand partyOffMacro = new MacroCommand(partyOff);
 
		remoteControl.setCommand(0, partyOnMacro, partyOffMacro);
  
		System.out.println(remoteControl);
		System.out.println("--- Pushing Macro On---");
		remoteControl.onButtonWasPushed(0);
		System.out.println("--- Pushing Macro Off---");
		remoteControl.offButtonWasPushed(0);
	}
}
