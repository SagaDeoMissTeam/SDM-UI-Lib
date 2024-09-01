package net.sixik.sdmuilibrary.client.widgets.text;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.utils.misc.RGB;

import java.util.ArrayList;
import java.util.List;

public class MultiLineTextWidget extends SingleLineTextWidget {

    public Component text;
    public List<String> lines = new ArrayList<>();

    public MultiLineTextWidget(Component text){
        super(null, null);
        this.text = text;
    }

    public MultiLineTextWidget(Component text, Vector2 size){
        super(null, size);
        this.text = text;
    }

    public MultiLineTextWidget(Component text, Vector2 position, Vector2 size) {
        super(text, position, size);
        this.text = text;
    }

    public void calculate(){
        lines.clear();
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
    }

    @Override
    public void draw(GuiGraphics graphics, int x, int y, int width, int height, int mouseX, int mouseY, float tick) {
        calculate();
        int posY = y;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            graphics.drawString(Minecraft.getInstance().font, line, x, posY, RGB.create(255,255,255).toInt());
            posY += Minecraft.getInstance().font.lineHeight * i + 2;

        }
    }
}
