package dk.stravclan.ninjalooter;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.checkerframework.checker.units.qual.K;

import java.util.*;

import static org.lwjgl.glfw.GLFW.*;

public class HelperFunctions {
    static KeyMapping lootkey;
    static KeyMapping lootToggleKey;
    static KeyMapping addLootBlacklistKey;
    static KeyMapping toggleLootBlacklistKey;
    static boolean lootKeyStatus = false;
    static int lootKeyFalseCount = 0;
    static boolean lootToggle = false;
    static boolean blacklistToggle = false;
    static Dictionary<String, Boolean> lootBlacklist = new Hashtable<>();

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
        if (addLootBlacklistKey == null) {
            registerKeymapping();
        }
        return addLootBlacklistKey;
    }
    public static KeyMapping getToggleLootBlacklistKey() {
        if (toggleLootBlacklistKey == null) {
            registerKeymapping();
        }
        return toggleLootBlacklistKey;
    }
    public static boolean getlootkeyStatus() {
        return lootKeyStatus;
    }

    public static void registerKeymapping() {
        // registers the keymapping we need
        lootkey = new KeyMapping(
                // Bind the key to the keybinding
                // default key is "Alt + E"
                Constants.NAME_LOOT_KEY, // Key Title
                KeyConflictContext.IN_GAME, // Mapping can only be used when a screen is open
                InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
                GLFW_KEY_LEFT_ALT, // Default input is the "E" key
                Constants.MOD_ID  // Mapping will be in the new example category
        );
        lootToggleKey = new KeyMapping(
                // Bind the key to the keybinding
                // default key is ","
                Constants.NAME_LOOT_TOGGLE_KEY, // Key Title
                KeyConflictContext.IN_GAME, // Mapping can only be used when a screen is open
                InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
                GLFW_KEY_COMMA, // Default input is the "," key
                Constants.MOD_ID  // Mapping will be in the new example category
        );
        addLootBlacklistKey = new KeyMapping(
                // Bind the key to the keybinding
                // default key is "B"
                Constants.NAME_ADD_BLACKLIST_KEY, // Key Title
                KeyConflictContext.IN_GAME, // Mapping can only be used when a screen is open
                InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
                GLFW_KEY_V, // Default input is the "," key
                Constants.MOD_ID// Mapping will be in the new example category
        );
        toggleLootBlacklistKey = new KeyMapping(
                // Bind the key to the keybinding
                // default key is "B"
                Constants.NAME_TOGGLE_BLACKLIST_KEY, // Key Title
                KeyConflictContext.IN_GAME, // Mapping can only be used when a screen is open
                InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
                GLFW_KEY_B, // Default input is the "," key
                Constants.MOD_ID  // Mapping will be in the new example category
        );
        //event.register()

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
                if (!blacklistToggle){
                    // if not in blacklist mode, loot everything
                    mc.gameMode.handleInventoryMouseClick(mc.player.containerMenu.containerId, i, 0, ClickType.PICKUP, mc.player);
                    mc.gameMode.handleInventoryMouseClick(mc.player.containerMenu.containerId, -999, 0, ClickType.PICKUP, mc.player);
                    continue;
                } else if (!isInBlacklist(item.getDescriptionId())) {
                    // move cursor to cell and pick up item
                    mc.gameMode.handleInventoryMouseClick(mc.player.containerMenu.containerId, i, 0, ClickType.PICKUP, mc.player);
                    // move cursor outside inventory and drop item
                    mc.gameMode.handleInventoryMouseClick(mc.player.containerMenu.containerId, -999, 0, ClickType.PICKUP, mc.player);
                }else { // Skip item if it is blacklisted
                    continue;
                }

            }
        }
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
        if (mc.player == null){return;}
        // get item stack in hand
        ItemStack item = mc.player.getItemInHand(mc.player.getUsedItemHand());

        // if there is an item in hand, and it is not in the blacklist, add it to the blacklist
        if (!item.isEmpty() && lootBlacklist.get(item.getDescriptionId()) == null) {
            lootBlacklist.put(item.getDescriptionId(), true);
            mc.player.sendMessage(new TextComponent(Util.describtionIdToName(item.getDescriptionId()) + " added to blacklist"), mc.player.getUUID());

            // if the item is in the blacklist, reverse the value
        } else if (lootBlacklist.get(item.getDescriptionId()) != null) {
            //lootBlacklist.put(item.getDescriptionId(), !lootBlacklist.get(item.getDescriptionId()));
            lootBlacklist.remove(item.getDescriptionId());
            mc.player.sendMessage(new TextComponent(Util.describtionIdToName(item.getDescriptionId()) + " removed from blacklist"), mc.player.getUUID());
        }
        // Save the change
        Util.saveBlacklist();

    }


    public static void toggleLootBlacklist(Minecraft mc) {
        if (mc.player == null){return;}
        //LOGGER.info("Toggle blacklist key pressed");
        blacklistToggle = !blacklistToggle;
        // Call update to UI generator
        mc.player.sendMessage(new TextComponent("Loot blacklist toggle: " + blacklistToggle), mc.player.getUUID());
        mc.player.sendMessage(new TextComponent("Items in blacklist: " + Util.dictEnumeratorToBlockNameList(lootBlacklist.keys())), mc.player.getUUID());

    }

    public static boolean isInBlacklist(String itemId) {
        // dict <item ID, Blacklist status>
        if (lootBlacklist.get(itemId) == null) {
            return false;
        } else {
            return lootBlacklist.get(itemId);
        }
    }

    public static void addDescribtionsToBlacklist(String[] list) {
        for (String id : list) {
            lootBlacklist.put(id, true);
        }
    }

}
