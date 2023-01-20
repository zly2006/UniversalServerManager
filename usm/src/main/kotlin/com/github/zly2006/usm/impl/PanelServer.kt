package com.github.zly2006.usm.impl

import com.github.zly2006.usm.api.Color
import com.github.zly2006.usm.api.instance.InstanceFolder
import com.github.zly2006.usm.api.text.Text
import com.github.zly2006.usm.panelServer
import com.github.zly2006.usm.toUUID
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import java.security.MessageDigest
import kotlin.random.Random

class PanelServer: EventSource(), com.github.zly2006.usm.api.PanelServer {
    var running = false
    private val accountService = AccountService()
    private val logger = org.slf4j.LoggerFactory.getLogger(PanelServer::class.java)
    private val instanceFolders = mutableMapOf<String, InstanceFolder>()
    val daemonServers = mutableMapOf<String, DaemonServer>()

    override fun running(): Boolean {
        TODO("Not yet implemented")
    }

    override fun accounts() = accountService
    override fun instanceFolders() = instanceFolders.values

    override fun createInstanceFolder(name: String): InstanceFolder = instanceFolders.getOrPut(name) {
        InstanceFolder(Text.of(name), Text.EMPTY, Color.WHITE, mutableListOf())
    }

    override fun deleteInstanceFolder(name: String) {
        instanceFolders.remove(name)
    }

    override fun gwtInstanceFolder(name: String): InstanceFolder? = instanceFolders[name]

    fun start() {
        if (accountService.users().isEmpty()) {
            logger.info("No user found, please create a user first")
            logger.info("Please enter a username:")
            val username = readLine()?.let {
                if (it.isBlank()) {
                    null
                } else {
                    it
                }
            } ?: let {
                logger.error("failed to read username, using 'admin' as default")
                "admin"
            }
            var password: String? = null
            while (password == null) {
                logger.info("Please enter password for user $username:")
                password = readLine()
                if (password == null) {
                    logger.error("failed to read password, using a random password")
                    password = Random.nextBytes(9).encodeBase64()
                    logger.info("generated password: $password")
                    break
                }
                logger.info("Please enter password again")
                val password2 = readLine()
                if (password != password2) {
                    logger.error("password not match, please try again")
                    password = null
                }
            }
            accountService.createUser(username).resetPasswordInternal(password!!)
        }
    }
    fun configureServer(application: Application) {
        application.run {
            // login: POST /api/login
            routing {
                val missingField = JsonObject(
                    mapOf(
                        "code" to JsonPrimitive(1001),
                        "message" to JsonPrimitive("Missing field")
                    )
                )
                val invalidSign = JsonObject(
                    mapOf(
                        "code" to JsonPrimitive(1002),
                        "message" to JsonPrimitive("Invalid sign")
                    )
                )
                val expired = JsonObject(
                    mapOf(
                        "code" to JsonPrimitive(1003),
                        "message" to JsonPrimitive("Expired")
                    )
                )
                val wrongPassword = JsonObject(
                    mapOf(
                        "code" to JsonPrimitive(1004),
                        "message" to JsonPrimitive("Wrong password")
                    )
                )
                post("/api/login") {
                    val parameters = call.receive<Map<String, String>>()
                    val username = parameters["username"] ?: return@post call.respond(HttpStatusCode.Forbidden, missingField)
                    val password = parameters["password"] ?: return@post call.respond(HttpStatusCode.Forbidden, missingField)
                    val timestamp = parameters["timestamp"] ?: return@post call.respond(HttpStatusCode.Forbidden ,missingField)
                    val sign = parameters["sign"] ?: return@post call.respond(HttpStatusCode.Forbidden, missingField)
                    if (sign != MessageDigest.getInstance("SHA-256")
                            .digest("$username$timestamp".toByteArray())
                            .joinToString("") { "%02x".format(it) }
                    ) {
                        return@post call.respond(HttpStatusCode.Forbidden, invalidSign)
                    }
                    if (System.currentTimeMillis().let {
                            it until it + 1000 * 60
                        }.contains(timestamp.toLong()).not()) {
                        return@post call.respond(HttpStatusCode.Forbidden, expired)
                    }
                    panelServer!!.accounts().byName[username]?.let {
                        if (it.password == password) {
                            it.resetSessionId()
                            call.response.cookies.append(
                                name = "user",
                                value = it.sessionId!!.toString(),
                                httpOnly = true,
                                maxAge = 604800L
                            )
                            call.respond(JsonObject(mapOf(
                                "code" to JsonPrimitive(0),
                                "redirect" to JsonPrimitive("/dashboard")
                            )))
                        } else {
                            call.respond(HttpStatusCode.Forbidden, wrongPassword)
                        }
                    } ?: call.respond(HttpStatusCode.Forbidden, wrongPassword)
                }
            }
            // session authorization: POST /api/session/authorize
            routing {
                post("/api/session/authorize") {
                    val sessionId = call.parameters["sessionId"]?.toUUID() ?: return@post call.respond(HttpStatusCode.BadRequest)
                    val user = this@PanelServer.accounts().bySessionId[sessionId] ?: return@post call.respond(HttpStatusCode.NoContent)
                    //call.respond()
                }
            }
            // session logout: POST /api/session/logout
            routing {
                post("/api/session/logout") {
                    val sessionId =
                        call.parameters["sessionId"]?.toUUID() ?: return@post call.respond(HttpStatusCode.BadRequest)
                    val user = this@PanelServer.accounts().bySessionId[sessionId] ?: return@post call.respond(
                        HttpStatusCode.NoContent
                    )
                }
            }
            // user list: GET /api/user/list
        }
    }
}
