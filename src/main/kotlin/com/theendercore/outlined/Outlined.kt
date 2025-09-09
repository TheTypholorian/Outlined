package com.theendercore.outlined

import net.minecraft.resources.ResourceLocation
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Suppress("unused")
object Outlined {
    const val MODID = "outlined"
    @JvmField
    val log: Logger = LoggerFactory.getLogger(MODID)
    fun id(path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(MODID, path)
}
