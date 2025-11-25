package com.theendercore.outlined.client.misc

import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.VertexFormat
import com.theendercore.outlined.Outlined.id
import net.minecraft.Util
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderStateShard
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard
import net.minecraft.client.renderer.RenderType
import net.minecraft.resources.ResourceLocation
import java.util.function.Function


object TestingObj {

    fun init() {
        println(OUTLINE_2)
    }

    @JvmStatic
    fun getBuffer(buff: MultiBufferSource): MultiBufferSource {

        return buff
    }

    @JvmStatic
    fun getType(rl: ResourceLocation) = OUTLINE_2.apply(rl)
    val OUTLINE_2: Function<ResourceLocation, RenderType.CompositeRenderType> = Util.memoize {
        RenderType.create(
            id("outline_2").toString(),
            DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS, 1536,
            RenderType.CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_OUTLINE_SHADER)
                .setTextureState(TextureStateShard(it, false, false))
                .setCullState(RenderStateShard.NO_CULL)
//            .setDepthTestState(RenderStateShard.NO_DEPTH_TEST)
                .setDepthTestState(TextureStateShard.LEQUAL_DEPTH_TEST)
                .setOutputState(RenderStateShard.OUTLINE_TARGET)
                .createCompositeState(RenderType.OutlineProperty.IS_OUTLINE)
        )
    }
}

