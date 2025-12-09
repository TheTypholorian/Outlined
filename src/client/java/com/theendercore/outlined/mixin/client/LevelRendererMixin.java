package com.theendercore.outlined.mixin.client;

import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.theendercore.outlined.client.OutlinedClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    @Final
    private static Logger LOGGER;

    @Inject(
            method = "initOutline",
            at = @At("TAIL")
    )
    private void initOutline(CallbackInfo ci) {
        PostChain effect = OutlinedClient.getENTITY_EFFECT();
        RenderTarget target;

        if (effect != null) {
            effect.close();
        }

        ResourceLocation location = OutlinedClient.INSTANCE.id("shaders/post/entity_outline.json");

        try {
            effect = new PostChain(
                    minecraft.getTextureManager(), minecraft.getResourceManager(), minecraft.getMainRenderTarget(), location
            );
            effect.resize(minecraft.getWindow().getWidth(), minecraft.getWindow().getHeight());
            target = effect.getTempTarget("final");
        } catch (IOException var3) {
            LOGGER.warn("Failed to load shader: {}", location, var3);
            effect = null;
            target = null;
        } catch (JsonSyntaxException var4) {
            LOGGER.warn("Failed to parse shader: {}", location, var4);
            effect = null;
            target = null;
        }

        OutlinedClient.setENTITY_EFFECT(effect);
        OutlinedClient.setOUTLINE_TARGET(target);
    }
}
