package com.theendercore.outlined.client

import com.mojang.blaze3d.vertex.ByteBufferBuilder
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.blaze3d.vertex.VertexMultiConsumer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.util.FastColor.ARGB32

@Environment(EnvType.CLIENT)
class DepthOutlineBufferSource : MultiBufferSource {
    private val bufferSource: MultiBufferSource.BufferSource
    private val outlineBufferSource: MultiBufferSource.BufferSource =
        MultiBufferSource.immediate(ByteBufferBuilder(1536))
    private var teamR = 255
    private var teamG = 255
    private var teamB = 255
    private var teamA = 255

    constructor(bufferSource: MultiBufferSource.BufferSource) {
        this.bufferSource = bufferSource
    }

    override fun getBuffer(renderType: RenderType): VertexConsumer {
        if (renderType.isOutline) {
            val vertexConsumer = this.outlineBufferSource.getBuffer(renderType)
            return EntityOutlineGenerator(vertexConsumer, this.teamR, this.teamG, this.teamB, this.teamA)
        } else {
            val vertexConsumer = this.bufferSource.getBuffer(renderType)

            if (renderType is RenderType.CompositeRenderType && renderType.state().outlineProperty == RenderType.OutlineProperty.AFFECTS_OUTLINE) {
                val texture = renderType.state().textureState.cutoutTexture()

                if (texture.isPresent) {
                    val vertexConsumer2 = this.outlineBufferSource.getBuffer(OutlinedClient.OUTLINE_LAYER.apply(texture.get()))
                    val entityOutlineGenerator = EntityOutlineGenerator(
                        vertexConsumer2, this.teamR, this.teamG, this.teamB, this.teamA
                    )
                    return VertexMultiConsumer.create(entityOutlineGenerator, vertexConsumer)
                }
            }

            return vertexConsumer
        }
    }

    fun setColor(i: Int, j: Int, k: Int, l: Int) {
        this.teamR = i
        this.teamG = j
        this.teamB = k
        this.teamA = l
    }

    fun endOutlineBatch() {
        this.outlineBufferSource.endBatch()
    }

    @Environment(EnvType.CLIENT)
    @JvmRecord
    data class EntityOutlineGenerator(val delegate: VertexConsumer, val color: Int) : VertexConsumer {
        constructor(vertexConsumer: VertexConsumer, i: Int, j: Int, k: Int, l: Int) : this(
            vertexConsumer,
            ARGB32.color(l, i, j, k)
        )

        override fun addVertex(f: Float, g: Float, h: Float): VertexConsumer {
            this.delegate.addVertex(f, g, h).setColor(this.color)
            return this
        }

        override fun setColor(i: Int, j: Int, k: Int, l: Int): VertexConsumer {
            return this
        }

        override fun setUv(f: Float, g: Float): VertexConsumer {
            this.delegate.setUv(f, g)
            return this
        }

        override fun setUv1(i: Int, j: Int): VertexConsumer {
            return this
        }

        override fun setUv2(i: Int, j: Int): VertexConsumer {
            return this
        }

        override fun setNormal(f: Float, g: Float, h: Float): VertexConsumer {
            return this
        }
    }
}