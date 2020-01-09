// ISecurityCenter.aidl
package com.qiugong.artisticprobes.x02.binderpools;

interface ISecurityCenter {
    String encrypt(in String content);
    String decrypt(in String password);
}
