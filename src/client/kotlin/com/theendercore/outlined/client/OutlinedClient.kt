package com.theendercore.outlined.client

import com.theendercore.outlined.Outlined.log
import com.theendercore.outlined.client.config.OutlinedConfig
import com.theendercore.outlined.client.misc.start
import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import me.fzzyhmstrs.fzzy_config.api.RegisterType

@Suppress("unused")
object OutlinedClient {
    @JvmField
    var config = ConfigApi.registerAndLoadConfig(::OutlinedConfig, RegisterType.CLIENT)
    fun init() {
        log.info("Hello from Client")
        start()
    }
}
