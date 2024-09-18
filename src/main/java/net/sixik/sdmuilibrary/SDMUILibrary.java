package net.sixik.sdmuilibrary;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.sixik.sdmuilibrary.client.screen.examples.GameUIScreen;
import org.slf4j.Logger;

@Mod(SDMUILibrary.MODID)
public class SDMUILibrary {

    public static final String MODID = "sdm_ui_library";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static boolean debugMode = false;

    public SDMUILibrary() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
    }

//    @SubscribeEvent
//    @OnlyIn(Dist.CLIENT)
//    public void on(PlayerInteractEvent.RightClickEmpty event) {
//        if (event.getEntity().level().isClientSide) {
//            if(debugMode) {
//                Minecraft.getInstance().setScreen(new GameUIScreen());
//            }
//        }
//    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

    }
}
