package dk.stravclan.ninjalooter;

import java.io.File;

public class Constants {
    public static final String MOD_ID = "ninjalooter";
    public static final String NAME_LOOT_TOGGLE_KEY = "Toggle loot function";
    public static final String CONFIG_BLACKLIST = "blacklist";
    public static final CharSequence DEFAULT_BLACKLIST = "";
    static final String NAME_LOOT_KEY = "LootKey";
    static final String NAME_ADD_BLACKLIST_KEY = "Add to blacklist";
    static final String NAME_TOGGLE_BLACKLIST_KEY = "Toggle blacklist";
    static final String NAME_ADD_CONTAINER_KEY = "Add container to list";
    public static int PLAYER_INV_SIZE = 40;
    public static int PLAYER_INV_SIZE_CHEST = 36;
    public static final String CONFIG_FILEPATH = ninjalooter.mc.gameDirectory.getAbsolutePath() + File.separator + "config" + File.separator + "ninjalooter.cfg";
}
