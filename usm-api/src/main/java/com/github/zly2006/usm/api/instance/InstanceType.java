package com.github.zly2006.usm.api.instance;

import com.github.zly2006.usm.api.text.Text;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Nullable;

@Accessors(chain = true, fluent = true)
public class InstanceType {
    @Getter
    Text name;
    @Getter
    Text description;
    /**
     * get the instance template, this field may be null.
     */
    @Getter @Nullable
    InstanceTemplate template;
}
