package timaxa007.money.v5.client;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import timaxa007.money.v5.ProxyCommon;

public class ProxyClient extends ProxyCommon {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		String category = "gui";
		EventsClientForge.direction = (byte)config.get(category, "direction", 0, "0 - left-top, 1 - center-top, 2 - right-top, /n"
				+ "3 - left-center, 4 - center-center, 5 - right-center, /n"
				+ "6 - left-botton, 7 - center-botton, 8 - right-botton, /n").getInt();
		EventsClientForge.offsetX = config.get(category, "offsetX", 0).getInt();
		EventsClientForge.offsetY = config.get(category, "offsetY", 0).getInt();
		config.save();

		MinecraftForge.EVENT_BUS.register(new EventsClientForge());
		FMLCommonHandler.instance().bus().register(new EventsClientFML());
	}

}
