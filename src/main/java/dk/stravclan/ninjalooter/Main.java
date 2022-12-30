package dk.stravclan.ninjalooter;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;

import dk.stravclan.ninjalooter.Config;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
public class Main {
    public static Config config;
    private static boolean initialized = false;
    private static Minecraft mc;

    private static final Logger Logger = LogUtils.getLogger();

    public static void initialize() {
        Logger.info("Main.initialize()");

        if (initialized)
            return;

        mc = Minecraft.getInstance();

        config = new Config(mc.gameDirectory.getAbsolutePath() + File.separator + "config" + File.separator + "NinjaLooter.cfg");
        config.read();

        Logger.info("Initialized.");
        initialized = true;
    }

}
