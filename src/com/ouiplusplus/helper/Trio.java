package com.ouiplusplus.helper;

public class Trio<T, S, E> {
    private T t1;
    private S t2;
    private E t3;

    public Trio() {
    }

    public Trio(T t1, S t2, E t3) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
    }

    public T getT1() {
        return t1;
    }

    public S getT2() {
        return t2;
    }

    public E getT3() {
        return t3;
    }

    public void setT1(T t1) {
        this.t1 = t1;
    }

    public void setT2(S t2) {
        this.t2 = t2;
    }

    public void setT3(E t3) {
        this.t3 = t3;
    }
}