package com.ouiplusplus.helper;

public class Pair<T, S> {
    private T p1;
    private S p2;


    public Pair(T p1, S p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public void setP1(T p1) {
        this.p1 = p1;
    }

    public void setP2(S p2) {
        this.p2 = p2;
    }

    public T getP1() {
        return p1;
    }
    public S getP2() {
        return p2;
    }
}