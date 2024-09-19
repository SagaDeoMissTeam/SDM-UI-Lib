package net.sixik.sdmuilib.client.widgets.text.printable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.sixik.sdmuilib.client.utils.misc.RGB;
import net.sixik.sdmuilib.client.widgets.text.SingleLineTextWidget;

public class PrintableBaseTextWidget extends SingleLineTextWidget {

    public boolean isPrintEnd = false;
    public int printSpeed = 1;
    public int countCharactersOnPrint = 1;
    public int tick = 0;
    public int character = 0;
    public String printText = "";
    public String lT;
    public boolean ignoreSpace = true;

    public PrintableBaseTextWidget(Component text) {
        super(text);
        lT = text.getString();
    }

    @Override
    public void draw(GuiGraphics graphics, int x, int y, int width, int height, int mouseX, int mouseY, float tick) {
        graphics.drawString(Minecraft.getInstance().font, printText, x,y, RGB.create(255,255,255).toInt());

        printTick();

    }

    public void printTick() {
        if (isPrintEnd) return;

        if (tick % printSpeed == 0) {
            char c = getCharacter();
            character += countCharactersOnPrint;
            printText += c;
        }

        tick++;
    }

    public char getCharacter() {
        if (character >= lT.length()) {
            isPrintEnd = true;
            return ' ';
        }

        if (Character.isSpaceChar(lT.charAt(character)) && ignoreSpace) {
            character++;
            return getCharacter();
        }

        return lT.charAt(character);
    }
}
