package net.sixik.sdmuilibrary;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.sixik.sdmuilibrary.client.screen.TestScreen;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SDMUILibrary.MODID)
public class SDMUILibrary {

    public static final String MODID = "sdm_ui_library";
    private static final Logger LOGGER = LogUtils.getLogger();


    public SDMUILibrary() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);


    }

    @SubscribeEvent
    public void on(PlayerInteractEvent.RightClickEmpty event) {
        if(event.getEntity().level().isClientSide){
            Minecraft.getInstance().setScreen(new TestScreen());
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

    }
}
