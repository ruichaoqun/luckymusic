package com.ruichaoqun.luckymusic.utils;

import java.io.Closeable;

public class IoUtils {
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable unused) {
            }
        }
    }

}
