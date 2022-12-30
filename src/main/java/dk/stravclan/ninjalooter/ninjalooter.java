package dk.stravclan.ninjalooter;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigGuiHandler.ConfigGuiFactory;
import net.minecraftforge.client.event.ScreenEvent.MouseClickedEvent;
import net.minecraftforge.client.event.ScreenEvent.MouseInputEvent;
import net.minecraftforge.client.event.ScreenEvent.MouseReleasedEvent;
import net.minecraftforge.client.event.ScreenEvent.MouseDragEvent;
import net.minecraftforge.client.event.ScreenEvent.MouseScrollEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import dk.stravclan.ninjalooter.Constants;
import org.slf4j.Logger;


import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Constants.MOD_ID)
public class ninjalooter
{
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public ninjalooter()
    {
        
        // run main
        Main.initialize();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }


}
