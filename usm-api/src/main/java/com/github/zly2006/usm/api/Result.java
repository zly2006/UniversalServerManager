package com.github.zly2006.usm.api;

public enum Result {
    SUCCESS,
    PASS,
    FAIL;
    boolean shouldContinue() {
        return this == SUCCESS || this == PASS;
    }
}
