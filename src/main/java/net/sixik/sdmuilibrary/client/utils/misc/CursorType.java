package net.sixik.sdmuilibrary.client.utils.misc;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

/**
 * Enum representing different cursor types for the Minecraft client.
 * This enum is used to set the cursor shape in the game window.
 */
public enum CursorType {
    ARROW(221185),
    IBEAM(221186),
    CROSSHAIR(221187),
    HAND(221188),
    HRESIZE(221189),
    VRESIZE(221190);

    private final int shape;
    private long cursor = 0L;

    /**
     * Constructor for CursorType enum.
     *
     * @param c The shape code for the cursor.
     */
    private CursorType(int c) {
        this.shape = c;
    }

    /**
     * Sets the cursor shape in the Minecraft client window.
     *
     * @param type The CursorType enum value representing the desired cursor shape.
     *             If null, the cursor will be reset to the default arrow shape.
     * @OnlyIn(Dist.CLIENT)
     */
    @OnlyIn(Dist.CLIENT)
    public static void set(@Nullable CursorType type) {
        long window = Minecraft.getInstance().getWindow().getWindow();
        if (type == null) {
            GLFW.glfwSetCursor(window, 0L);
        } else {
            if (type.cursor == 0L) {
                type.cursor = GLFW.glfwCreateStandardCursor(type.shape);
            }

            GLFW.glfwSetCursor(window, type.cursor);
        }
    }
}
