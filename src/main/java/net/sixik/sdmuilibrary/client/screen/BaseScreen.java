package net.sixik.sdmuilibrary.client.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.sixik.sdmuilibrary.client.render.container.RenderContainerImpl;
import net.sixik.sdmuilibrary.client.utils.GLHelper;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.widgets.RenderWidget;
import net.sixik.sdmuilibrary.client.widgets.SDMWidget;
import org.jetbrains.annotations.NotNull;

public class BaseScreen extends Screen {

    public Vector2 size = new Vector2(0,0);;
    public Vector2 position = new Vector2(0,0);;

    protected BaseScreen() {
        super(Component.empty());
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        draw(graphics, mouseX, mouseY, partialTicks);
        for(Renderable renderable : this.renderables) {
            if(renderable instanceof RenderWidget renderWiget) {
                GLHelper.pushScissor(graphics, position, size);
                renderable.render(graphics, mouseX, mouseY, partialTicks);
                GLHelper.popScissor(graphics);
            } else
                renderable.render(graphics, mouseX, mouseY, partialTicks);




        }
    }

    protected void addWidgets() {

    }

    @Override
    protected void init() {
        super.init();
        rebuildProperty();
        addWidgets();
    }

    @Override
    protected void rebuildWidgets() {
        super.rebuildWidgets();
        rebuildProperty();
    }

    protected void rebuildProperty(){
        size = new Vector2(Minecraft.getInstance().getWindow().getGuiScaledWidth(), Minecraft.getInstance().getWindow().getGuiScaledHeight());
        position = new Vector2(0,0);
    }

    @Override
    public void tick() {
        for(Renderable renderable : this.renderables) {
            if(renderable instanceof SDMWidget widget){
                widget.tick();
            }
        }

    }

    public void draw(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks){

    }
}
