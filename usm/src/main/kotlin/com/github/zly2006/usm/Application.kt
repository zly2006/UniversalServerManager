package com.github.zly2006.usm

import com.github.zly2006.usm.impl.PanelServer
import com.github.zly2006.usm.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.util.*
import kotlin.random.Random

object options {
    enum class Mode {
        DAEMON, PANEL, BOTH;
        fun isDaemon() = this == DAEMON || this == BOTH
        fun isPanel() = this == PANEL || this == BOTH
    }
    enum class PluginResetPasswordRule {
        NONE, NON_ADMIN, ALL;
    }
    var port: Int = 6210
    var host: String = "0.0.0.0"
    var mode: Mode = Mode.BOTH
    var pluginModifyUserRule: PluginResetPasswordRule = PluginResetPasswordRule.NON_ADMIN
    var salt: String = Random.nextBytes(128).encodeBase64()
}

var a = 1.let {
    runCatching {
        it.toString()
    }.onFailure {
        it.printStackTrace()
    }.getOrNull()
}

var panelServer: PanelServer? = null
var daemonServer: Any? = null

fun main(args: Array<String>) {
    for (i in args.indices) {
        when (args[i]) {
            "--port" -> options.port = args[i + 1].toInt()
            "--host" -> options.host = args[i + 1]
            "--mode" -> options.mode = options.Mode.valueOf(args[i + 1].uppercase())
        }
    }
    if (options.mode.isPanel()) {
        panelServer = PanelServer()
        panelServer!!.start()
    }
    embeddedServer(
        Netty,
        port = options.port,
        host = options.host,
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    configureSecurity()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureTemplating()
    configureSockets()
    configureAdministration()
    configureRouting()
    if (options.mode.isPanel()) {
        panelServer!!.configureServer(this)
    }
    if (options.mode.isDaemon()) {
        daemonServer = Any()
    }
}
