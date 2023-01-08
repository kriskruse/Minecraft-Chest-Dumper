package dk.stravclan.ninjalooter;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;

import static org.lwjgl.glfw.GLFW.*;

public class HelperFunctions {
    static KeyMapping lootkey;
    static KeyMapping lootToggleKey;
    static KeyMapping AddLootBlacklistKey;
    static boolean lootKeyStatus = false;
    static int lootKeyFalseCount = 0;
    static boolean lootToggle = false;

    // constructor
    public HelperFunctions() {
        registerKeymapping();
    }

    public static KeyMapping getLootKey() {
        if (lootkey == null) {
            registerKeymapping();
        }
        return lootkey;
    }
    public static KeyMapping getLootToggleKey() {
        if (lootToggleKey == null) {
            registerKeymapping();
        }
        return lootToggleKey;
    }
    public static KeyMapping getAddLootBlacklistKey() {
        if (AddLootBlacklistKey == null) {
            registerKeymapping();
        }
        return AddLootBlacklistKey;
    }

    public static void registerKeymapping() {
        // registers the keymapping we need
        lootkey = new KeyMapping(
                // Bind the key to the keybinding
                // default key is "Alt + E"
                Constants.CONFIG_LOOT_KEY, // Key Title
                KeyConflictContext.IN_GAME, // Mapping can only be used when a screen is open
                KeyModifier.ALT, // Default modifier is shift
                InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
                GLFW_KEY_E, // Default input is the "E" key
                Constants.MOD_ID  // Mapping will be in the new example category
        );
        lootToggleKey = new KeyMapping(
                // Bind the key to the keybinding
                // default key is ","
                Constants.CONFIG_LOOT_TOGGLE_KEY, // Key Title
                KeyConflictContext.IN_GAME, // Mapping can only be used when a screen is open
                InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
                GLFW_KEY_COMMA, // Default input is the "," key
                Constants.MOD_ID  // Mapping will be in the new example category
        );
        AddLootBlacklistKey = new KeyMapping(
                // Bind the key to the keybinding
                // default key is "B"
                Constants.CONFIG_LOOT_TOGGLE_KEY, // Key Title
                KeyConflictContext.IN_GAME, // Mapping can only be used when a screen is open
                KeyModifier.CONTROL, // Default modifier is CTRL
                InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
                GLFW_KEY_B, // Default input is the "," key
                Constants.MOD_ID  // Mapping will be in the new example category
        );
    }
    public static void lootContainer(Minecraft mc) {
        if (mc.player == null){
            return;
        }
        for (int i = 0; i < mc.player.containerMenu.slots.size() - Constants.PLAYER_INV_SIZE_CHEST; i++) {
            //LOGGER.info("Item in slot " + i + " is " + mc.player.containerMenu.slots.get(i).getItem());

            ItemStack item = mc.player.containerMenu.slots.get(i).getItem();
            // if item is not air, left click it and move curser outside inventory and drop it
            if (!item.isEmpty()) {
                assert mc.gameMode != null;
                mc.gameMode.handleInventoryMouseClick(mc.player.containerMenu.containerId, i, 0, ClickType.PICKUP, mc.player);
                mc.gameMode.handleInventoryMouseClick(mc.player.containerMenu.containerId, -999, 0, ClickType.PICKUP, mc.player);
            }

        }
    }

    public static boolean lootkeyStatus() {
        return lootKeyStatus;
    }

    public static void keyPressed(boolean keyPressed) {
        if (keyPressed) {
            lootKeyStatus = true;
        }else if (lootKeyFalseCount > 10) {
            lootKeyStatus = false;
            lootKeyFalseCount = 0;
        }else {
            lootKeyFalseCount++;
        }

    }

    public static void toogleLoot(Minecraft mc) {
        if (mc.player == null){return;}
        //LOGGER.info("Toggle key pressed");
        lootToggle = !lootToggle;
        // Call update to UI generator
        mc.player.sendMessage(new TextComponent("Loot toggle: " + lootToggle), mc.player.getUUID());
    }

    public static void addLootBlacklist(Minecraft mc) {

        // some kind of dictionary with items, and a key boolean to toggle if it should be looted or not
        // unseen items should be looted by default and only be added to the dictionary if the user adds them
        // to the blacklist

        // look up item name in dictionary
        // if item is in dictionary and item is blacklisted skip
        // else loot item


    }


}
