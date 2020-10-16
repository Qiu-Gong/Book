package com.qiugong.first.x11_proxy.xx03_remote;

import com.qiugong.first.x11_proxy.xx03_remote.gumball.State;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GumballMachineRemote extends Remote {
    public int getCount() throws RemoteException;

    public String getLocation() throws RemoteException;

    public State getState() throws RemoteException;
}
