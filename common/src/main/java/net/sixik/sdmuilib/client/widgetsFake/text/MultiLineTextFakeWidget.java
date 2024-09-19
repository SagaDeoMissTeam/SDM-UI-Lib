package net.sixik.sdmuilib.client.widgetsFake.text;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.sixik.sdmuilib.client.utils.RenderHelper;
import net.sixik.sdmuilib.client.utils.misc.RGB;

public class MultiLineTextFakeWidget extends SingleLineFakeWidget{

    public MultiLineTextFakeWidget(Component text) {
        super(text);
    }

    public void calculate(){
        lines.clear();
        int currentLine = 0;
        for(String line : text.getString().split("\n")){
            if(Minecraft.getInstance().font.width(line) > size.x){
                String[] words = line.split(" ");
                StringBuilder currentWord = new StringBuilder();
                for(String word : words){
                    if(Minecraft.getInstance().font.width(currentWord.toString() + " " + word) > size.x){
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
    }

    @Override
    public void draw(GuiGraphics graphics) {
        calculate();
        RenderHelper.pushScale(graphics, position.x, position.y, textSize);
        int posY = position.y;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            graphics.drawString(Minecraft.getInstance().font, line, position.x, posY, RGB.create(255,255,255).toInt());
            posY += Minecraft.getInstance().font.lineHeight * i + 2;
        }
        RenderHelper.popScale(graphics);
    }
}
