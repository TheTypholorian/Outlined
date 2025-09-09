package com.theendercore.outlined.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;render(Lnet/minecraft/world/entity/Entity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"))
    private <T extends Entity> void run(EntityRenderer<T> instance, T entity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, Operation<Void> original) {
        MultiBufferSource exitBuffer = renderType -> {
            var extraLayer = multiBufferSource.getBuffer(RenderType.lines());
            VertexConsumer defaultLayer = multiBufferSource.getBuffer(renderType);
            return renderType.affectsCrumbling() ? VertexMultiConsumer.create(extraLayer, defaultLayer) : defaultLayer;
        };
        original.call(instance, entity, f, g, poseStack, exitBuffer, i);
    }
}
