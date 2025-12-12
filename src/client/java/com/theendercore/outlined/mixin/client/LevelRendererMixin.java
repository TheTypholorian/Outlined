package com.theendercore.outlined.mixin.client;

import com.google.gson.JsonSyntaxException;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.theendercore.outlined.Outlined;
import com.theendercore.outlined.client.DepthOutlineBufferSource;
import com.theendercore.outlined.client.OutlinedClient;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.Objects;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    @Final
    private static Logger LOGGER;

    @Shadow
    protected abstract boolean shouldShowEntityOutlines();

    @Shadow private @Nullable PostChain entityEffect;
    @Shadow @Final private RenderBuffers renderBuffers;
    @Unique
    private DepthOutlineBufferSource outlineSource;

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void init(Minecraft minecraft, EntityRenderDispatcher entityRenderDispatcher, BlockEntityRenderDispatcher blockEntityRenderDispatcher, RenderBuffers renderBuffers, CallbackInfo ci) {
        outlineSource = new DepthOutlineBufferSource(renderBuffers.bufferSource());
    }

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

        ResourceLocation location = Outlined.INSTANCE.id("shaders/post/entity_outline.json");

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

    @Inject(
            method = "renderLevel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/LevelRenderer;renderEntity(Lnet/minecraft/world/entity/Entity;DDDFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;)V"
            )
    )
    private void renderLevel1(DeltaTracker deltaTracker, boolean bl, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci, @Local Entity entity, @Local LocalRef<MultiBufferSource> source) {
        if (shouldShowEntityOutlines()) {
            var outlineSource = renderBuffers.outlineBufferSource();
            source.set(outlineSource);
            int color = entity.getTeamColor();
            outlineSource.setColor(FastColor.ARGB32.red(color), FastColor.ARGB32.green(color), FastColor.ARGB32.blue(color), 255);
        }
    }

    @Inject(
            method = "renderLevel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/OutlineBufferSource;endOutlineBatch()V"
            )
    )
    private void renderLevel2(DeltaTracker deltaTracker, boolean bl, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci) {
        renderBuffers.outlineBufferSource().endOutlineBatch();
        Objects.requireNonNull(OutlinedClient.getENTITY_EFFECT()).process(deltaTracker.getGameTimeDeltaTicks());
        minecraft.getMainRenderTarget().bindWrite(false);
    }
}
