package com.github.zly2006.usm.impl

import com.github.zly2006.usm.api.exception.UserExistedException
import com.github.zly2006.usm.impl.user.User
import com.github.zly2006.usm.options
import java.security.MessageDigest
import java.util.*

class AccountService: com.github.zly2006.usm.api.AccountService {
    val bySessionId = mutableMapOf<UUID, User>()
    val byId = mutableMapOf<UUID, User>()
    val byName = mutableMapOf<String, User>()
    private val messageDigest = MessageDigest.getInstance("SHA-256")
    override fun users() = byId.values

    override fun createUser(name: String): User {
        if (byName[name] != null) {
            throw UserExistedException(byName[name]!!)
        }
        val user = User(this, UUID.nameUUIDFromBytes(name.toByteArray()), name)
        byId[user.id] = user
        byName[user.name] = user
        user.admin = true
        return user
    }

    override fun getUser(id: UUID) = byId[id]

    override fun deleteUser(id: UUID) {
        byId.computeIfPresent(id) { _, user ->
            bySessionId.remove(user.sessionId)
            byName.remove(user.name())
            null
        }
    }

    override fun findUser(username: String) = Optional.ofNullable(byName[username])

    fun hashPassword(password: String): String =
        messageDigest.digest("$password${options.salt}".toByteArray())
            .joinToString("") { "%02x".format(it) }
}
