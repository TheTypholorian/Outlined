package com.theendercore.outlined.client.misc

import com.mojang.authlib.minecraft.client.MinecraftClient
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.OutlineBufferSource
import net.minecraft.client.renderer.entity.EntityRenderDispatcher
import net.minecraft.util.FastColor.ARGB32
import net.minecraft.world.entity.Entity

fun renderHitbox(
    entityRenderDispatcher: EntityRenderDispatcher,
    poseStack: PoseStack,
    vertexConsumer: VertexConsumer,
    entity: Entity,
    f: Float,
    g: Float,
    h: Float,
    i: Float,
) {
    val renderer = entityRenderDispatcher.getRenderer(entity);
    val outlineBufferSource: OutlineBufferSource = Minecraft.getInstance().renderBuffers().outlineBufferSource()
     outlineBufferSource
    val i = entity.getTeamColor()
    outlineBufferSource.setColor(ARGB32.red(i), ARGB32.green(i), ARGB32.blue(i), 255)
//    renderer.render()
//    outlineBufferSource.getBuffer()
//    renderer.render()
}
