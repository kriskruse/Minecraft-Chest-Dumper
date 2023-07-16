package dk.stravclan.ninjalooter;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkConstants;
import org.slf4j.Logger;

import static dk.stravclan.ninjalooter.HelperFunctions.lootToggle;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(Constants.MOD_ID)
public class ninjalooter {
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public static Minecraft mc;

    public ninjalooter() {
        // Gather config saved on instance, if not create a new one with default values
        Main.initialize();

        // Modloading context is used to register extension points for your mod.
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () ->
                new IExtensionPoint.DisplayTest(() -> "true", (a, b) -> true));

        // Register ourselves for game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);





        mc = Minecraft.getInstance();
        Util.loadBlacklist();
        LOGGER.info("loaded NinjaLooter blacklist");
        LOGGER.info("NinjaLooter ready to loot!");

    }


    @SubscribeEvent
    public void onContainerOpen(TickEvent.ClientTickEvent event) {
        if (mc.player == null || mc.player.containerMenu.containerId == 0 || event.phase != TickEvent.Phase.END) {
            return;
        }
        // if lootkey status is true or toggle is on call lootContainer
        if (HelperFunctions.getlootkeyStatus() || HelperFunctions.lootToggle) {
            HelperFunctions.lootContainer(mc);
            mc.player.closeContainer();
        }
        //LOGGER.info("Container open");
    }

    @SubscribeEvent
    public void checkForPress(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        HelperFunctions.keyPressed(false);
        if (mc.player != null && HelperFunctions.getLootKey().isDown()) {
            //LOGGER.info("Key pressed");
            HelperFunctions.keyPressed(true);
        }
    }
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (mc.player == null){return;}

        // call the function toggleLoot() if the key is pressed
        if (HelperFunctions.getLootToggleKey().isDown()) {
            HelperFunctions.toogleLoot(mc);
        }
        // call the function addLootBlacklist() if the key is pressed
        else if (HelperFunctions.getToggleLootBlacklistKey().isDown()) {
            HelperFunctions.toggleLootBlacklist(mc);
        }
        // call the function addLootBlacklist() if the key is pressed
        else if (HelperFunctions.getAddLootBlacklistKey().isDown()) {
            HelperFunctions.addLootBlacklist(mc);
        }
    }

//    @SubscribeEvent(priority = EventPriority.NORMAL)
//    public void OnDrawHUD(RenderGameOverlayEvent.Post pEvent) {
//        if (pEvent.getType() == RenderGameOverlayEvent.ElementType.ALL) {
//            HelperFunctions.drawHud(mc);
//
//        }
//    }
}


