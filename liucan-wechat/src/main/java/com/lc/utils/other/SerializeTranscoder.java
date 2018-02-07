package com.lc.utils.other;

import java.io.Closeable;

/**
 * Created by feihaitao on 2017/5/3.
 */
public abstract  class SerializeTranscoder {

    public abstract byte[] serialize(Object value);

    public abstract Object deserialize(byte[] in);

    public void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
            }
        }
    }
}
