package net.sixik.sdmuilibrary.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.utils.misc.RGB;

import java.util.ArrayList;
import java.util.List;

public class FakeWidgetRender {

    public static void singleLineText(GuiGraphics graphics, Component component, int x, int y) {
        graphics.drawString(Minecraft.getInstance().font, component.getString(), x,y, RGB.create(255,255,255).toInt());
    }

    public static void singleLineSizableText(GuiGraphics graphics, Component component, float size, int x, int y) {
        RenderHelper.pushScale(graphics, x,y, size);
        graphics.drawString(Minecraft.getInstance().font, component.getString(), x, y, RGB.create(255,255,255).toInt());
        RenderHelper.popScale(graphics);
    }

    public static void multiLineText(GuiGraphics graphics, Component component, int x, int y, int width) {
        List<String> lines = calculate(component, width);
        int posY = y;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            graphics.drawString(Minecraft.getInstance().font, line, x, posY, RGB.create(255,255,255).toInt());
            posY += Minecraft.getInstance().font.lineHeight * i + 2;
        }
    }

    public static void multiLineSizableText(GuiGraphics graphics, Component component, float size, int x, int y, int width) {
        RenderHelper.pushScale(graphics, x,y, size);
        List<String> lines = calculate(component, width);
        int posY = y;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            graphics.drawString(Minecraft.getInstance().font, line, x, posY, RGB.create(255,255,255).toInt());
            posY += Minecraft.getInstance().font.lineHeight * i + 2;
        }
        RenderHelper.popScale(graphics);
    }

    private static List<String> calculate(Component text, int width){
        List<String> lines = new ArrayList<>();
        int currentLine = 0;
        for(String line : text.getString().split("\n")){
            if(Minecraft.getInstance().font.width(line) > width){
                String[] words = line.split(" ");
                StringBuilder currentWord = new StringBuilder();
                for(String word : words){
                    if(Minecraft.getInstance().font.width(currentWord.toString() + " " + word) > width){
                        lines.add(currentWord.toString());
                        currentWord.setLength(0);
                        currentLine++;
                    }
                    currentWord.append(word).append(" ");
                }
                if(!currentWord.isEmpty()){
                    lines.add(currentWord.toString());
                }
            } else {
                lines.add(line);
            }
        }
        return lines;
    }
}
