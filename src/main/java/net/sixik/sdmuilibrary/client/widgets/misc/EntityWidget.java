package net.sixik.sdmuilibrary.client.widgets.misc;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.sixik.sdmuilibrary.client.utils.RenderHelper;
import net.sixik.sdmuilibrary.client.utils.math.Vector2;
import net.sixik.sdmuilibrary.client.widgets.SDMWidget;

public class EntityWidget extends SDMWidget {

    public LivingEntity entity;
    public double scale = 1.0;
    public double yaw = 0.0;

    public EntityWidget(LivingEntity entity, Vector2 position, Vector2 size) {
        super(position, size);
        this.entity = entity;
    }
    public EntityWidget(EntityType<?> entity, Vector2 position, Vector2 size) {
        super(position, size);
        this.entity = RenderHelper.getEntity(entity);
    }

    public EntityWidget setScale(double scale) {
        this.scale = scale;
        return this;
    }

    public EntityWidget setYaw(double yaw) {
        this.yaw = yaw;
        return this;
    }

    @Override
    public void draw(GuiGraphics graphics, int x, int y, int width, int height, int mouseX, int mouseY, float tick) {
        if(entity == null) return;
        RenderHelper.drawLivingEntity(graphics, x,y, scale, yaw, 0, entity);
    }
}
