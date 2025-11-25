package com.theendercore.outlined.client.misc

import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.util.FastColor.ARGB32

@JvmRecord
data class SafeConsumer(val delegate: VertexConsumer, val color: Int) : VertexConsumer {
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