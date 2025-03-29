//package net.sixik.sdmuilibrary.mixin.integration.ftblib;
//
//import dev.ftb.mods.ftblibrary.ui.Widget;
//import imgui.ImGui;
//import imgui.flag.ImGuiHoveredFlags;
//import net.minecraft.client.Minecraft;
//import net.sixik.sdm_core.patch.IScreenPatch;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//@Mixin(value = Widget.class, remap = false)
//public class WidgetMixin {
//
//    @Inject(method = "checkMouseOver", at = @At("HEAD"), cancellable = true)
//    public void sdm$checkMouseOver(int mouseX, int mouseY, CallbackInfoReturnable<Boolean> cir) {
//        if(Minecraft.getInstance().screen instanceof IScreenPatch patch && patch.isImGuiRendering()) {
//            if(ImGui.isWindowHovered(ImGuiHoveredFlags.AnyWindow)) cir.setReturnValue(false);
//        }
//    }
//}
