package com.github.zly2006.usm.api.text;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface Text {
    String content();
    Text EMPTY = () -> "";
    @Contract(pure = true)
    static @NotNull Text of(String content) {
        return () -> content;
    }
}
