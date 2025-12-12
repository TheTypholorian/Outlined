package com.theendercore.outlined.client

import com.theendercore.outlined.Outlined.log
import com.theendercore.outlined.client.config.OutlinedConfig
import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import me.fzzyhmstrs.fzzy_config.api.RegisterType
import net.minecraft.resources.ResourceLocation

@Suppress("unused")
object OutlinedClient {
    const val MOD_ID = "outlined"

    @JvmField
    var config = ConfigApi.registerAndLoadConfig(::OutlinedConfig, RegisterType.CLIENT)

    fun init() {
        log.info("Hello from Client")
    }

    fun id(path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(MOD_ID, path)
}
