package com.theendercore.outlined.client

import com.theendercore.outlined.Outlined.log
import com.theendercore.outlined.client.config.OutlinedConfig
import me.fzzyhmstrs.fzzy_config.api.ConfigApi

@Suppress("unused")
object OutlinedClient {
    @JvmField
    var config = ConfigApi.registerAndLoadConfig(::OutlinedConfig)
    fun init() {
        log.info("Hello from Client")
    }
}
