package net.sixik.sdmuilibrary.client.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.sixik.sdmuilibrary.client.render.container.RenderContainerImpl;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.widgets.SDMWidget;
import org.jetbrains.annotations.NotNull;

public class BaseScreen extends Screen {

    public Vector2 size = new Vector2(Minecraft.getInstance().getWindow().getGuiScaledWidth(), Minecraft.getInstance().getWindow().getGuiScaledHeight());
    public Vector2 position = new Vector2(0,0);


    public RenderContainerImpl container;

    protected BaseScreen() {
        super(Component.empty());
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        container = new RenderContainerImpl(graphics, new Vector2(mouseX, mouseY));
        for(Renderable renderable : this.renderables) {
            if(renderable instanceof SDMWidget widget){
                widget.setContainer(container);
            }
            renderable.render(graphics, mouseX, mouseY, partialTicks);
        }
        draw(graphics, mouseX, mouseY, partialTicks);
    }


    public void draw(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks){

    }
}
