package com.theendercore.outlined.mixin.client;

import com.theendercore.outlined.client.misc.TestingObj;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderType.class)
public class RenderTypeMixin {

//    @Inject(method = "outline(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;", at=@At("HEAD"), cancellable = true)
//    private static void x(ResourceLocation resourceLocation, CallbackInfoReturnable<RenderType> cir){
//        cir.setReturnValue(TestingObj.getType(resourceLocation));
//    }
}
