package com.example.paint;

import android.util.Pair;

import java.io.Serializable;

public class Par<K extends Serializable, V extends Serializable> {

    public K first;
    public V second;

    public Par(){}

    public Par(K newK, V newV) {
        first = newK;
        second = newV;

    }

    public void setKey(K newF){

        first = newF;

    }


    public void setValue(V newS){

        second = newS;

    }

    public K getFirst() {
        return first;
    }


    public V getSecond() {
        return second;
    }
}
