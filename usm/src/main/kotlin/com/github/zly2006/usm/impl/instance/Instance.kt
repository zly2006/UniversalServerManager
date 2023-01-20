package com.github.zly2006.usm.impl.instance

import com.github.zly2006.usm.api.AbstractCommand
import com.github.zly2006.usm.api.Permission
import com.github.zly2006.usm.api.instance.InstanceFolder
import com.github.zly2006.usm.api.text.Text
import com.github.zly2006.usm.api.user.User
import com.github.zly2006.usm.impl.EventSource
import java.nio.file.Path
import java.util.*

class Instance(
    val name: String,
    val uuid: UUID = UUID.randomUUID(),
    val root: Path,
    val startCommand: AbstractCommand,
    val stopCommand: AbstractCommand,
): EventSource(), com.github.zly2006.usm.api.instance.Instance {
    var running = false
    var folder: InstanceFolder? = null
    override fun running() = running
    override fun start() {
        if (running) {
            return
        }
        running = true
        startCommand().execute(null)
    }

    override fun stop() {
        stopCommand().executeFuture(null).join()
        running = false
    }

    override fun id() = uuid
    override fun root() = root
    override fun getName() = Text.of(name)
    override fun startCommand() = startCommand
    override fun stopCommand() = stopCommand
    override fun folder() = folder

    override fun checkPermission(user: User?, permission: Permission?): Boolean {
        TODO("Not yet implemented")
    }

    override fun setPermission(user: User?, permission: Permission?, value: Boolean) {
        TODO("Not yet implemented")
    }
}