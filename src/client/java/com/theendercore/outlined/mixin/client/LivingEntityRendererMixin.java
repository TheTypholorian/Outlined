package com.theendercore.outlined.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.theendercore.outlined.Outlined;
import com.theendercore.outlined.client.misc.TestingObj;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Debug(export = true)
@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
    @WrapOperation(method = "getRenderType", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;outline(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;"))
    private <T extends LivingEntity> RenderType run(ResourceLocation resourceLocation, Operation<RenderType> original) {
        Outlined.log.info("Chat i pain");
        return TestingObj.INSTANCE.getOUTLINE_2().apply(resourceLocation);
    }
}
