package com.theendercore.outlined.client.config

import com.theendercore.outlined.Outlined.MODID
import com.theendercore.outlined.Outlined.id
import me.fzzyhmstrs.fzzy_config.annotations.NonSync
import me.fzzyhmstrs.fzzy_config.config.Config
import me.fzzyhmstrs.fzzy_config.config.ConfigGroup

@Suppress("unused")
class OutlinedConfig: Config(id(MODID)) {
    var groupName = ConfigGroup("group_id", false)

    @NonSync
    @ConfigGroup.Pop
    var clientEntry = true
}