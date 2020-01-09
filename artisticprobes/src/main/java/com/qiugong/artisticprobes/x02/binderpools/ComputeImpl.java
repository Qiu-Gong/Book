package com.qiugong.artisticprobes.x02.binderpools;

import android.os.RemoteException;

import com.qiugong.artisticprobes.Tools;

/**
 * @author qzx 2019/10/17.
 */
public class ComputeImpl extends ICompute.Stub {

    private static final String TAG = "ComputeImpl";

    @Override
    public int add(int a, int b) throws RemoteException {
        Tools.log(TAG, "add: a=" + a + " b=" + b);
        return a + b;
    }
}
