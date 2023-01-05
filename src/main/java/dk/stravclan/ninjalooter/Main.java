package dk.stravclan.ninjalooter;

import com.mojang.logging.LogUtils;
import net.minecraftforge.client.ClientRegistry;
import org.slf4j.Logger;

public class Main {
    private static boolean initialized = false;
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void initialize() {
        LOGGER.info("Main.initialize()");
        if (initialized)
            return;
        HelperFunctions.registerKeymapping();
        ClientRegistry.registerKeyBinding(HelperFunctions.getLootKey());
        ClientRegistry.registerKeyBinding(HelperFunctions.getLootToggleKey());

        // Set the initialized flag to true
        LOGGER.info("Initialized.");
        initialized = true;
    }
}
