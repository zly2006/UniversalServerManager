package com.github.zly2006.usm.api;

public record FormOption (
    String label,
    String placeholder,
    String value,
    String tooltip,
    boolean enabled
) {
    static FormOption of(String label, String placeholder, String value, String tooltip, boolean enabled) {
        return new FormOption(label, placeholder, value, tooltip, enabled);
    }

    static FormOption of(String label, String placeholder, String value, String tooltip) {
        return new FormOption(label, placeholder, value, tooltip, true);
    }

    static FormOption of(String label, String placeholder, String value) {
        return new FormOption(label, placeholder, value, null, true);
    }

    static FormOption of(String label, String placeholder) {
        return new FormOption(label, placeholder, null, null, true);
    }

    static FormOption of(String label) {
        return new FormOption(label, null, null, null, true);
    }
}
