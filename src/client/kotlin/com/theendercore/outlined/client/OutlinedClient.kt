package com.theendercore.outlined.client

import com.mojang.blaze3d.pipeline.RenderTarget
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.VertexFormat
import com.theendercore.outlined.Outlined.log
import com.theendercore.outlined.client.config.OutlinedConfig
import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import me.fzzyhmstrs.fzzy_config.api.RegisterType
import net.minecraft.Util
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.PostChain
import net.minecraft.client.renderer.RenderStateShard
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard
import net.minecraft.client.renderer.RenderType
import net.minecraft.resources.ResourceLocation
import java.util.function.Function

@Suppress("unused")
object OutlinedClient {
    const val MOD_ID = "outlined"

    @JvmField
    var config = ConfigApi.registerAndLoadConfig(::OutlinedConfig, RegisterType.CLIENT)

    fun init() {
        log.info("Hello from Client")
    }

    @JvmStatic
    var ENTITY_EFFECT: PostChain? = null
    @JvmStatic
    var OUTLINE_TARGET: RenderTarget? = null
    val OUTLINE_TARGET_SHARD: RenderStateShard.OutputStateShard = RenderStateShard.OutputStateShard(
        id("outline_target").toString(),
        { OUTLINE_TARGET!!.bindWrite(false) },
        { Minecraft.getInstance().mainRenderTarget.bindWrite(false) }
    )
    val OUTLINE_LAYER: Function<ResourceLocation, RenderType.CompositeRenderType> = Util.memoize {
        RenderType.create(
            id("outline").toString(),
            DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS, 1536,
            RenderType.CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_OUTLINE_SHADER)
                .setTextureState(TextureStateShard(it, false, false))
                .setCullState(RenderStateShard.NO_CULL)
                .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST)
                .setOutputState(OUTLINE_TARGET_SHARD)
                .createCompositeState(RenderType.OutlineProperty.IS_OUTLINE)
        )
    }

    fun id(path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(MOD_ID, path)
}
