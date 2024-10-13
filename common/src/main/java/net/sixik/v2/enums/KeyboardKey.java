package net.sixik.v2.enums;


import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

public class KeyboardKey {

    /**
     * {@link GLFW}
     */

    public final int keyCode;
    public final int scanCode;
    public final KeyModifier modifiers;


    private static boolean matchesWithoutConflicts(KeyMapping keyBinding, InputConstants.Key keyCode) {
        return KeyboardKey.matchesWithoutConflicts(keyBinding, keyCode);
    }


    public KeyboardKey(int k, int s, int m) {
        this.keyCode = k;
        this.scanCode = s;
        this.modifiers = new KeyModifier(m);
    }

    public boolean is(int k) {
        return this.keyCode == k;
    }

    public InputConstants.Key getInputMapping() {
        return InputConstants.getKey(this.keyCode, this.scanCode);
    }

    public boolean esc() {
        return this.is(256);
    }

    public boolean escOrInventory() {
        return this.esc() || matchesWithoutConflicts(Minecraft.getInstance().options.keyInventory, this.getInputMapping());
    }

    public boolean enter() {
        return this.is(257);
    }

    public boolean backspace() {
        return this.is(259);
    }

    public boolean cut() {
        return Screen.isCut(this.keyCode);
    }

    public boolean paste() {
        return Screen.isPaste(this.keyCode);
    }

    public boolean copy() {
        return Screen.isCopy(this.keyCode);
    }

    public boolean selectAll() {
        return Screen.isSelectAll(this.keyCode);
    }

    public boolean deselectAll() {
        return this.keyCode == 68 && Screen.hasControlDown() && !Screen.hasShiftDown() && !Screen.hasAltDown();
    }

    public static class KeyModifier {
        public static final KeyModifier NONE = new KeyModifier(0);
        public final int modifiers;

        public KeyModifier(int m) {
            this.modifiers = m;
        }

        public boolean shift() {
            return (this.modifiers & 1) != 0;
        }

        public boolean control() {
            return (this.modifiers & 2) != 0;
        }

        public boolean alt() {
            return (this.modifiers & 4) != 0;
        }

        public boolean start() {
            return (this.modifiers & 8) != 0;
        }

        public boolean numLock() {
            return (this.modifiers & 32) != 0;
        }

        public boolean capsLock() {
            return (this.modifiers & 16) != 0;
        }

        public boolean onlyControl() {
            return this.control() && !this.shift() && !this.alt();
        }
    }
}
