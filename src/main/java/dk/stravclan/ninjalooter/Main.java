package dk.stravclan.ninjalooter;

import com.mojang.logging.LogUtils;
import net.minecraftforge.client.ClientRegistry;
import org.slf4j.Logger;

import java.io.File;



public class Main {
    private static boolean initialized = false;
    private static final Logger LOGGER = LogUtils.getLogger();
    public static Util util;

    public static void initialize() {
        LOGGER.info(Constants.MOD_ID + " Main.initialize");
        if (initialized)
            return;

        HelperFunctions.registerKeymapping();
        ClientRegistry.registerKeyBinding(HelperFunctions.getLootKey());
        ClientRegistry.registerKeyBinding(HelperFunctions.getLootToggleKey());
        ClientRegistry.registerKeyBinding(HelperFunctions.getAddLootBlacklistKey());
        ClientRegistry.registerKeyBinding(HelperFunctions.getToggleLootBlacklistKey());
        ClientRegistry.registerKeyBinding(HelperFunctions.getAddContainerToListKey());
        LOGGER.info(Constants.MOD_ID + " Main.initialize: Keybindings registered.");

        // Set the initialized flag to true
        LOGGER.info(Constants.MOD_ID + " Initialized.");
        initialized = true;
    }
}
