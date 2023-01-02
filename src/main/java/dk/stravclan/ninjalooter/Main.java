package dk.stravclan.ninjalooter;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientRegistry;
import org.slf4j.Logger;

import java.io.File;



public class Main {
    public static Config config;
    private static boolean initialized = false;
    private static Minecraft mc;

    private static final Logger LOGGER = LogUtils.getLogger();

    public static void initialize() {
        LOGGER.info("Main.initialize()");

        if (initialized)
            return;

        // this is the minceaft instance
        mc = Minecraft.getInstance();

        // Define the config file
        config = new Config(mc.gameDirectory.getAbsolutePath() + File.separator + "config" + File.separator + "NinjaLooter.cfg");
        // Try to load the config file
        config.read();

        ClientRegistry.registerKeyBinding(config.getLootKey());
        // Set the initialized flag to true
        LOGGER.info("Initialized.");
        initialized = true;
    }
}
