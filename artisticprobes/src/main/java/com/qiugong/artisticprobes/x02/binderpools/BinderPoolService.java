package com.qiugong.artisticprobes.x02.binderpools;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.qiugong.artisticprobes.Tools;

/**
 * @author qzx 2019/10/17.
 */
public class BinderPoolService extends Service {

    private static final String TAG = "BinderPoolService";
    private Binder mBinderPool = new BinderPoolImpl();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return mBinderPool;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static class BinderPoolImpl extends IBinderPool.Stub {

        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            Tools.log(TAG, "queryBinder:" + binderCode);
            IBinder binder = null;
            switch (binderCode) {
                case BinderPools.BINDER_SECURITY_CENTER: {
                    binder = new SecurityCenterImpl();
                    break;
                }

                case BinderPools.BINDER_COMPUTE: {
                    binder = new ComputeImpl();
                    break;
                }

                default:
                    break;
            }

            return binder;
        }
    }
}
