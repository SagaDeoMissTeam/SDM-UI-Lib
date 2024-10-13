package net.sixik.v2.color;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ColorHelper {

    public static RGB rgb(int r, int g, int b){
        return RGB.create(r, g, b);
    }

    public static RGBA rgba(int r, int g, int b, int a){
        return RGBA.create(r, g, b, a);
    }

    public static GradientRBG gradientRBG(RGB start, RGB end) {
        return GradientRBG.create(start, end);
    }

    public static TextureColor texture(ResourceLocation id){
        return TextureColor.create(id);
    }

    public static AtlasTextureColor textureAtlas(ResourceLocation id){
        return AtlasTextureColor.create(id);
    }

    public static ItemColor item(ItemStack itemStack) {
        return ItemColor.fromStack(itemStack);
    }

    public static ItemColor item(Item item) {
        return ItemColor.fromStack(item);
    }
}
