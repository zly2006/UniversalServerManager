package com.github.zly2006.usm.api.instance;

import com.github.zly2006.usm.api.FormOption;
import com.github.zly2006.usm.api.text.Text;

import java.util.List;

public interface InstanceTemplate {
    Text name();
    Text description();
    List<FormOption> options();
}
