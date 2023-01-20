package com.github.zly2006.usm.api.text;

public interface TranslatableText extends Text {
    Text translate(String lang, Text... args);
}
