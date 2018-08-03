package timaxa007.money.v2b.client;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import timaxa007.money.v2b.ProxyCommon;

public class ProxyClient extends ProxyCommon {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		EventsClient eventClient = new EventsClient();
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		String category = "gui";
		eventClient.direction = (byte)config.get("gui", "direction", eventClient.direction,
				"0 - left-top,		1 - center-top,		2 - right-top, \n" +
				"3 - left-center,	4 - center-center,	5 - right-center, \n" +
				"6 - left-botton,	7 - center-botton,	8 - right-botton.").getInt();
		eventClient.offsetX = config.get(category, "offsetX", eventClient.offsetX).getInt();
		eventClient.offsetY = config.get(category, "offsetY", eventClient.offsetY).getInt();
		eventClient.isVertical = config.get(category, "isVertical", eventClient.isVertical).getBoolean();
		eventClient.maxDelay = config.get(category, "maxDelay", eventClient.maxDelay).getInt();
		eventClient.multiAddCoins = config.get(category, "multiAddCoins", eventClient.multiAddCoins).getBoolean();
		config.save();
		MinecraftForge.EVENT_BUS.register(eventClient);
		FMLCommonHandler.instance().bus().register(eventClient);
	}

}
