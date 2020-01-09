package com.qiugong.artisticprobes.x02.binderpools;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.qiugong.artisticprobes.R;
import com.qiugong.artisticprobes.Tools;

/**
 * @author qzx 2019/10/17.
 */
public class BinderPoolActivity extends Activity {

    private static final String TAG = "BinderPoolActivity";

    private ISecurityCenter mSecurityCenter;
    private ICompute mCompute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
            }
        }).start();
    }

    private void doWork() {
        BinderPools binderPool = BinderPools.getInstance(BinderPoolActivity.this);
        IBinder securityBinder = binderPool.queryBinder(BinderPools.BINDER_SECURITY_CENTER);
        mSecurityCenter = SecurityCenterImpl.asInterface(securityBinder);
        Tools.log(TAG, "visit ISecurityCenter");
        String msg = "helloworld-安卓";
        Tools.log(TAG, "content:" + msg);
        try {
            String password = mSecurityCenter.encrypt(msg);
            Tools.log(TAG, "encrypt:" + password);
            Tools.log(TAG, "decrypt:" + mSecurityCenter.decrypt(password));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        Tools.log(TAG, "visit ICompute");
        IBinder computeBinder = binderPool.queryBinder(BinderPools.BINDER_COMPUTE);
        mCompute = ComputeImpl.asInterface(computeBinder);
        try {
            Tools.log(TAG, "3+5=" + mCompute.add(3, 5));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
