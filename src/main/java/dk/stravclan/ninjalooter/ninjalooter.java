package dk.stravclan.ninjalooter;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
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


    @SubscribeEvent
    // when chest is open and key is pressed, loot the chest
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (event.getKey() == Config.lootKey.getKey().getValue() && mc.player != null &&
                 mc.player.containerMenu.containerId != 0) {
            // Cycle items in chest and drop them on the ground
            for (int i = 0; i < mc.player.containerMenu.slots.size() - Constants.PLAYER_INV_SIZE_CHEST; i++) {
                LOGGER.info("Item in slot " + i + " is " + mc.player.containerMenu.slots.get(i).getItem());

                ItemStack item = mc.player.containerMenu.slots.get(i).getItem();
                // if item is not air, left click it and move curser outside inventory and drop it
                if (!item.isEmpty()) {
                    assert mc.gameMode != null;
                    mc.gameMode.handleInventoryMouseClick(mc.player.containerMenu.containerId, i, 0, ClickType.PICKUP, mc.player);
                    mc.gameMode.handleInventoryMouseClick(mc.player.containerMenu.containerId, -999, 0, ClickType.PICKUP, mc.player);
                }

            }
            mc.player.closeContainer();
        }

    }
}

