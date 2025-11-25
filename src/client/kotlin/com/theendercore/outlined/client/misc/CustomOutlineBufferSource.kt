package com.theendercore.outlined.client.misc

import com.mojang.blaze3d.vertex.ByteBufferBuilder
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.blaze3d.vertex.VertexMultiConsumer
import com.theendercore.outlined.client.OutlinedClient.config
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.util.FastColor.ARGB32


@Environment(EnvType.CLIENT)
class CustomOutlineBufferSource(private val bufferSource: MultiBufferSource.BufferSource) : MultiBufferSource {
    private val outlineBufferSource: MultiBufferSource.BufferSource =
        MultiBufferSource.immediate(ByteBufferBuilder(1536))
    private var teamR = 255
    private var teamG = 255
    private var teamB = 255
    private var teamA = 255

    override fun getBuffer(renderType: RenderType): VertexConsumer {
        setColor()
        if (renderType.isOutline) {
            return CustomOutlineGenerator(outlineBufferSource.getBuffer(renderType), teamR, teamG, teamB, teamA)
        }
        val mainBuffer = bufferSource.getBuffer(renderType)
        val addToOutline = renderType.outline()
        if (addToOutline.isPresent) {
            val rawOutlineBuffer = outlineBufferSource.getBuffer(addToOutline.get())
            val wrappedBuffer = CustomOutlineGenerator(rawOutlineBuffer, teamR, teamG, teamB, teamA)
            return VertexMultiConsumer.create(wrappedBuffer, mainBuffer)
        }

        return mainBuffer
    }

    fun setColor(i: Int, j: Int, k: Int, l: Int) {
        teamR = i
        teamG = j
        teamB = k
        teamA = l
    }
    fun setColor() {
        teamR = config.color.r()
        teamG = config.color.g()
        teamB = config.color.b()
        teamA = config.color.a()
    }

    fun endOutlineBatch() {
        outlineBufferSource.endBatch()
    }

    @Environment(EnvType.CLIENT)
    @JvmRecord
    internal data class CustomOutlineGenerator(val delegate: VertexConsumer, val color: Int) : VertexConsumer {
        constructor(vertexConsumer: VertexConsumer, i: Int, j: Int, k: Int, l: Int) : this(
            vertexConsumer,
            ARGB32.color(l, i, j, k)
        )

        override fun addVertex(f: Float, g: Float, h: Float): VertexConsumer {
            delegate.addVertex(f, g, h).setColor(color)
            return this
        }

        override fun setUv(f: Float, g: Float): VertexConsumer {
            delegate.setUv(f, g)
            return this
        }

        override fun setColor(i: Int, j: Int, k: Int, l: Int): VertexConsumer = this
        override fun setUv1(i: Int, j: Int): VertexConsumer = this
        override fun setUv2(i: Int, j: Int): VertexConsumer = this
        override fun setNormal(f: Float, g: Float, h: Float): VertexConsumer = this
    }
}