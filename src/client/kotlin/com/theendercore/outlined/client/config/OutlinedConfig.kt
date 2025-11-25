package com.theendercore.outlined.client.config

import com.theendercore.outlined.Outlined.MODID
import com.theendercore.outlined.Outlined.id
import me.fzzyhmstrs.fzzy_config.annotations.NonSync
import me.fzzyhmstrs.fzzy_config.config.Config
import me.fzzyhmstrs.fzzy_config.config.ConfigGroup
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedColor

@Suppress("unused")
class OutlinedConfig: Config(id(MODID)) {
    var color = ValidatedColor()
    var clientEntry = true
}