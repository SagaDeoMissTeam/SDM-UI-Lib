package net.sixik.sdmuilibrary.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.sixik.sdmuilibrary.client.integration.imgui.init.ImGuiBuffers;
import net.sixik.sdmuilibrary.client.integration.imgui.init.ImGuiHandler;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow @Nullable public Screen screen;

    @Inject(method = "resizeDisplay", at = @At("RETURN"))
    private void sdm$onResolutionChanged(CallbackInfo ci) {
        final var window = Minecraft.getInstance().getWindow();
        ImGuiBuffers.imguiScreenBuffer.resize(window.getWidth(), window.getHeight(), Minecraft.ON_OSX);
        ImGuiBuffers.imguiOverlayBuffer.resize(window.getWidth(), window.getHeight(), Minecraft.ON_OSX);
    }

    @Inject(method = "setScreen", at = @At("HEAD"))
    private void sdm$setScreen(@Nullable Screen guiScreen, CallbackInfo ci) {
        final var window = Minecraft.getInstance().getWindow();
        ImGuiBuffers.imguiScreenBuffer.resize(window.getWidth(), window.getHeight(), Minecraft.ON_OSX);
    }



    @Inject(method = "setScreen", at = @At(value = "HEAD"))
    private void sdm$ScreenEvent(Screen screen, CallbackInfo ci) {

        if(screen != null) {
            ImGuiHandler.INSTANCE.setCallback(true);
        } else if(this.screen != null && screen != this.screen ){
            ImGuiHandler.INSTANCE.setCallback(false);
        }
    }
}
