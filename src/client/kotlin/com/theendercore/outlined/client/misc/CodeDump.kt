package com.theendercore.outlined.client.misc

import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexFormat
import com.theendercore.outlined.Outlined.id
import net.minecraft.Util
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderStateShard
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard
import net.minecraft.client.renderer.RenderType
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity
import java.util.function.Function


fun start() {
    println(OUTLINE_2)
}

val OUTLINE_2: Function<ResourceLocation, RenderType.CompositeRenderType> = Util.memoize {
    RenderType.create(
        id("outline_2").toString(),
        DefaultVertexFormat.POSITION_TEX_COLOR,
        VertexFormat.Mode.QUADS,
        1536,
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


fun <T : Entity> getBuffer(entity: T, postStack: PoseStack, bufferSrc: MultiBufferSource): MultiBufferSource {
    val renderer = Minecraft.getInstance().entityRenderDispatcher.getRenderer(entity)
    val cBuff = getCustomOutlineBuffer()

//    val exitBuffer = MultiBufferSource {
//        val defaultLayer = cBuff.getBuffer(it)
//        if (it.affectsCrumbling()) VertexMultiConsumer.create(cBuff.getBuffer(BUG.apply(renderer.getTextureLocation(entity))), defaultLayer)
//        else defaultLayer
//    }

    return cBuff
}