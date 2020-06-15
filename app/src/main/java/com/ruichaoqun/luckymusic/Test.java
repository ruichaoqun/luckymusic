package com.ruichaoqun.luckymusic;

import java.lang.reflect.Proxy;

public class Test {
    public static void main(String[] args) {
        byte a = Byte.MIN_VALUE;
        byte b = 5;
        float s = (float) a+b;
        System.out.printf(""+s);
    }
}
