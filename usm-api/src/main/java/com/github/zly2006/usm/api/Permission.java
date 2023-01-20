package com.github.zly2006.usm.api;

public enum Permission {
    None(0),
    Read(1),
    Execute(2),
    Write(3);
    private final int value;
    Permission(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
