package net.sixik.sdmuilibrary.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.sixik.sdmuilibrary.client.integration.imgui.IRenderable;
import net.sixik.sdmuilibrary.client.integration.imgui.init.ImGuiBuffers;
import net.sixik.sdmuilibrary.client.integration.imgui.init.ImGuiHandler;
import net.sixik.sdmuilibrary.client.integration.imgui.patch.IScreenPatch;
import net.sixik.sdmuilibrary.utils.ReflectionUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(Screen.class)
public class ScreenRenderMixin implements IScreenPatch {

    private boolean isImGuiRender = false;
    private final Screen thisScreen = (Screen) (Object)this;

    @Inject(method = "renderWithTooltip", at = @At("RETURN"))
    public void sdm$renderWithTooltip(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo ci) {
        isImGuiRender = sdm$renderIMGUI(thisScreen.getClass(), guiGraphics, i, j, f);
    }

    @Unique
    private boolean sdm$renderIMGUI(Class<?> cls, GuiGraphics guiGraphics, int i, int j, float f) {
        if(ImGuiBuffers.SCREEN_RENDERS.isEmpty()) return false;

        for (Map.Entry<Class<?>, List<IRenderable>> classListEntry : ImGuiBuffers.SCREEN_RENDERS.entrySet()) {

            if(ReflectionUtils.canCast(cls, classListEntry.getKey())) {

                for (IRenderable iRenderable : classListEntry.getValue()) {
                    ImGuiHandler.INSTANCE.drawFrame(0, iRenderable, guiGraphics, i,j, f);
                }
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isImGuiRendering() {
        return isImGuiRender;
    }
}
