package dk.stravclan.ninjalooter;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.block.entity.*;
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

import java.util.Arrays;
import java.util.List;

import static dk.stravclan.ninjalooter.HelperFunctions.getAddContainerToListKey;
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
        // Check if the container is part of the container list
        if (!(HelperFunctions.containerIsInList(mc.player.containerMenu.getClass().getSimpleName()))) {
            return;
        }
        // if lootkey status is true or toggle is on call lootContainer
        if (HelperFunctions.getlootkeyStatus() || lootToggle) {
            HelperFunctions.lootContainer(mc);
            mc.player.closeContainer();
        }

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
    
    
    long startTime = System.currentTimeMillis();
    int lastPress = 0;
    double timeOutTime = 0.5 * 1000;
    long elapsedTime = 0;
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (mc.player == null){return;}
        elapsedTime = System.currentTimeMillis() - startTime;

        // call the function toggleLoot() if the key is pressed
        if (HelperFunctions.getLootToggleKey().consumeClick() &&
                (elapsedTime > timeOutTime || lastPress != HelperFunctions.getLootToggleKey().getKey().getValue())) {
            HelperFunctions.toogleLoot(mc);
            startTime = System.currentTimeMillis();
            lastPress = event.getKey();
        }
        // call the function addLootBlacklist() if the key is pressed
        else if (HelperFunctions.getToggleLootBlacklistKey().consumeClick() &&
                (elapsedTime > timeOutTime || lastPress != HelperFunctions.getToggleLootBlacklistKey().getKey().getValue())) {
            HelperFunctions.toggleLootBlacklist(mc);
            startTime = System.currentTimeMillis();
            lastPress = event.getKey();
        }
        // call the function addLootBlacklist() if the key is pressed
        else if (HelperFunctions.getAddLootBlacklistKey().consumeClick() &&
                (elapsedTime > timeOutTime || lastPress != HelperFunctions.getAddLootBlacklistKey().getKey().getValue())) {
            HelperFunctions.addLootBlacklist(mc);
            startTime = System.currentTimeMillis();
            lastPress = event.getKey();
        }
        else if ((HelperFunctions.getAddContainerToListKey().consumeClick() ||
                (event.getKey() == getAddContainerToListKey().getKey().getValue())) &&
                        (elapsedTime > timeOutTime || lastPress != HelperFunctions.getAddContainerToListKey().getKey().getValue())){
            LOGGER.info("Key pressed to add container");
            HelperFunctions.addContainerToList(mc);
            startTime = System.currentTimeMillis();
            lastPress = event.getKey();
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


