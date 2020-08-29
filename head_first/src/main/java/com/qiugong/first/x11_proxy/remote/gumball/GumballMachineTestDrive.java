package com.qiugong.first.x11_proxy.remote.gumball;

import com.qiugong.first.x11_proxy.remote.GumballMachineRemote;

import java.rmi.*;

public class GumballMachineTestDrive {

    public static void main(String[] args) {
        args = new String[]{"seattle.mightygumball.com", "100"};
        GumballMachineRemote gumballMachine = null;
        int count;

        if (args.length < 2) {
            System.out.println("GumballMachine <name> <inventory>");
            System.exit(1);
        }

        try {
            count = Integer.parseInt(args[1]);

            gumballMachine =
                    new GumballMachine(args[0], count);
            Naming.rebind("//" + args[0] + "/x11_proxy/gumball", gumballMachine);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
