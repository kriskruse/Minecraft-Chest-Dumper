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
        LOGGER.info("Main.initialize");
        if (initialized)
            return;

        HelperFunctions.registerKeymapping();
        ClientRegistry.registerKeyBinding(HelperFunctions.getLootKey());
        ClientRegistry.registerKeyBinding(HelperFunctions.getLootToggleKey());
        ClientRegistry.registerKeyBinding(HelperFunctions.getAddLootBlacklistKey());
        ClientRegistry.registerKeyBinding(HelperFunctions.getToggleLootBlacklistKey());
        LOGGER.info("Main.initialize: registered keybindings");

        // Set the initialized flag to true
        LOGGER.info("Initialized.");
        initialized = true;
    }
}
