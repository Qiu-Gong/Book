package com.qiugong.artisticprobes.x02.binderpools;

import android.os.RemoteException;

import com.qiugong.artisticprobes.Tools;

/**
 * @author qzx 2019/10/17.
 */
public class SecurityCenterImpl extends ISecurityCenter.Stub {
    private static final String TAG = "SecurityCenterImpl";
    private static final char SECRET_CODE = '^';

    @Override
    public String encrypt(String content) throws RemoteException {
        Tools.log(TAG, "encrypt:" + content);
        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] ^= SECRET_CODE;
        }
        return new String(chars);
    }

    @Override
    public String decrypt(String password) throws RemoteException {
        Tools.log(TAG, "decrypt:" + password);
        return encrypt(password);
    }
}
