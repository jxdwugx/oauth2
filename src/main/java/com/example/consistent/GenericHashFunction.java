package com.example.consistent;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @description
 * @autor 吴光熙
 * @date 2021/5/18  20:03
 **/
public class GenericHashFunction implements HashFunction {
    private MessageDigest md5 = null;

    @Override
    public Long hash(String key) {
        if (md5 == null) {
            try {
                md5 = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException("no md5 algrithm found");
            }
        }

        md5.reset();
        md5.update(key.getBytes());
        byte[] bKey = md5.digest();
        //具体的哈希函数实现细节--每个字节 & 0xFF 再移位
        long result = ((long) (bKey[3] & 0xFF) << 24)
                | ((long) (bKey[2] & 0xFF) << 16
                | ((long) (bKey[1] & 0xFF) << 8) | (long) (bKey[0] & 0xFF));
        return result & 0xffffffffL;
    }
}
