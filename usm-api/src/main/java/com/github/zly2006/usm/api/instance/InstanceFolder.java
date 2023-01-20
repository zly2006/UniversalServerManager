package com.github.zly2006.usm.api.instance;

import com.github.zly2006.usm.api.Color;
import com.github.zly2006.usm.api.text.Text;

import java.util.ArrayList;
import java.util.List;

public record InstanceFolder (
    Text name,
    Text description,
    Color color,
    List<Instance> instances
) {
    public InstanceFolder {
        instances = new ArrayList<>();
    }
}
