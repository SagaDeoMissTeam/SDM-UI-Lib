package net.sixik.sdmuilib.client.widgets.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.sixik.sdmuilib.client.utils.math.Vector2;
import net.sixik.sdmuilib.client.utils.misc.CenterOperators;
import net.sixik.sdmuilib.client.utils.misc.RGB;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SimpleButtonWidget extends BasicButtonWidget{

    public RGB color = RGB.create(255,255,255);
    public List<String> lines = new ArrayList<>();

    public int maxWidth = 0;

    public SimpleButtonWidget(Component title) {
        this(title,null,null);
    }

    public SimpleButtonWidget(Component title, Vector2 size) {
        this(title, null,size);
    }

    public SimpleButtonWidget(Component title, Vector2 position, Vector2 size) {
        super(position, size);
        setTitle(title);
    }

    @Override
    public void setTitle(Component title) {
        super.setTitle(title);
        calculate();
    }

    public void setColor(RGB color) {
        this.color = color;
    }

    @Override
    public void onClick(double mouseX, double mouseY) {

    }

    public CenterOperators.Type getCenteredType(){
        return CenterOperators.Type.NONE;
    }

    public void calculate(){
        lines.clear();
        int currentLine = 0;
        for(String line : title.getString().split("\n")){
            if(Minecraft.getInstance().font.width(line) > width){
                String[] words = line.split(" ");
                StringBuilder currentWord = new StringBuilder();
                for(String word : words){
                    int d1 = Minecraft.getInstance().font.width(currentWord.toString() + " " + word);
                    if(d1 > width){
                        if(d1 > maxWidth) {
                            maxWidth = d1;
                        }

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
    public void drawTitle(GuiGraphics graphics, int x, int y, int width, int height, int mouseX, int mouseY, float tick) {

        int posY = y;
        int posX = x;

        if (Objects.requireNonNull(getCenteredType()) == CenterOperators.Type.CENTER_Y || Objects.requireNonNull(getCenteredType()) == CenterOperators.Type.CENTER_XY) {
            posY = y + height / 3;
        }

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            if(getCenteredType() == CenterOperators.Type.CENTER_X || getCenteredType() == CenterOperators.Type.CENTER_XY){
                posX  += (width - Minecraft.getInstance().font.width(line)) / 3;
            }

            graphics.drawString(Minecraft.getInstance().font, line, posX, posY, color.toInt());
            posY -= Minecraft.getInstance().font.lineHeight / 2;
            posY += Minecraft.getInstance().font.lineHeight * i + 2;
        }
    }
}
