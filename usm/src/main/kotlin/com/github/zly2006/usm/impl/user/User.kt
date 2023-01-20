package com.github.zly2006.usm.impl.user

import com.github.zly2006.usm.impl.AccountService
import com.github.zly2006.usm.impl.EventSource
import com.github.zly2006.usm.options
import com.github.zly2006.usm.options.PluginResetPasswordRule.*
import java.util.*

class User(
    private val accountService: AccountService,
    val id: UUID,
    val name: String
): EventSource(), com.github.zly2006.usm.api.user.User {
    internal var password: String = ""
    internal var sessionId: UUID? = null
        private set
    internal var admin = false

    fun resetSessionId() {
        if (sessionId != null) {
            accountService.bySessionId.remove(sessionId)
        }
        sessionId = UUID.randomUUID()
        accountService.bySessionId[sessionId!!] = this
    }

    override fun id() = id
    override fun name() = name
    override fun resetPassword(password: String) {
        when (options.pluginModifyUserRule) {
            NON_ADMIN -> {
                if (!this.admin()) {
                    resetPasswordInternal(password)
                }
            }
            ALL -> {
                resetPasswordInternal(password)
            }
            NONE -> {}
        }
    }

    internal fun resetPasswordInternal(password: String) {
        this.password = accountService.hashPassword(password)
    }

    override fun admin() = admin

    override fun setAdmin(admin: Boolean) {
        when (options.pluginModifyUserRule) {
            ALL -> {
                this.admin = admin
            }
            else -> {}
        }
    }
}
