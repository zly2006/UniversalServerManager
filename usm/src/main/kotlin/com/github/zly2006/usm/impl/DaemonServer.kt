package com.github.zly2006.usm.impl

import com.github.zly2006.usm.api.instance.Instance

class DaemonServer: com.github.zly2006.usm.api.DaemonServer {
    val instances = mutableMapOf<String, Instance>()
    override fun running(): Boolean {
        TODO("Not yet implemented")
    }

    override fun instances() = instances.values
}
