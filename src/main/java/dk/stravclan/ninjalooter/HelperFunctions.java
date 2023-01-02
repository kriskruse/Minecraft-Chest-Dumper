package dk.stravclan.ninjalooter;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_C;

public class HelperFunctions {




    public static String keyMapToString(KeyMapping keyMap) {
        return keyMap.getKey().getName();
    }

    public static KeyMapping registerKeymapping() {
        return new KeyMapping(
                // Bind the key to the keybinding
                // default key is "Shift + C"
                "key." + Constants.MOD_ID + ".loot",
                KeyConflictContext.GUI, // Mapping can only be used when a screen is open
                //KeyModifier.SHIFT, // Default modifier is shift
                InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
                GLFW_KEY_C, // Default mouse input is the left mouse button
                "key.categories."+ Constants.MOD_ID + ".NinjaLooter" // Mapping will be in the new example category
        );
    }
}
