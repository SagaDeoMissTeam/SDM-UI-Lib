package net.sixik.sdmuilibrary.mixin;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.sixik.sdmuilibrary.client.integration.imgui.ImGuiOverlayWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {


    @Inject(method = "render", at = @At("RETURN"))
    public void sdm$render(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        guiGraphics.pose().pushPose();

        guiGraphics.pose().translate(0,0, 200);

        ImGuiOverlayWrapper.renderAll(guiGraphics, 0,0,0);

        guiGraphics.pose().popPose();
    }
}
