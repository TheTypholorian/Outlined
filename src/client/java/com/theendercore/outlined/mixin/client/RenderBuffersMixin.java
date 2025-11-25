package com.theendercore.outlined.mixin.client;

import com.theendercore.outlined.client.misc.BuffersKt;
import com.theendercore.outlined.client.misc.CustomOutlineBufferSource;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderBuffers.class)
public abstract class RenderBuffersMixin {
    @Shadow
    @Final
    private MultiBufferSource.BufferSource bufferSource;

    @Inject(method = "<init>", at = @At("TAIL"))
    void initCustomBufferSource(int i, CallbackInfo ci) {
        BuffersKt.COBS = new CustomOutlineBufferSource(bufferSource);
    }
}
