package dk.stravclan.ninjalooter;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkConstants;
import org.slf4j.Logger;



// The value here should match an entry in the META-INF/mods.toml file
@Mod(Constants.MOD_ID)
public class ninjalooter {
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    private static Minecraft mc;
    private static boolean lootToggle = false;

    public ninjalooter() {
        // Gather config saved on instance, if not create a new one with default values
        Main.initialize();

        // Modloading context is used to register extension points for your mod.
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () ->
                new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        mc = Minecraft.getInstance();

    }


    @SubscribeEvent // ScreenEvent.BackgroundDrawnEvent event
    public void onContainerOpen(TickEvent.ClientTickEvent event) {
        if (mc.player == null || mc.player.containerMenu.containerId == 0 || event.phase != TickEvent.Phase.END) {
            return;
        }
        // if lootkey status is true or toggle is on call lootContainer
        if (HelperFunctions.lootkeyStatus() || lootToggle) {
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
    public void toggleLoot(InputEvent.KeyInputEvent event) {
        if (mc.player == null){return;}
        if (HelperFunctions.getLootToggleKey().isDown()) {
            //LOGGER.info("Toggle key pressed");
            lootToggle = !lootToggle;
            // Call update to UI generator
            mc.player.sendMessage(new TextComponent("Loot toggle: " + lootToggle), mc.player.getUUID());
        }
    }
}


