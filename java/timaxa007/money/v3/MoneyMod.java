package timaxa007.money.v3;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.MinecraftForge;
import timaxa007.money.v3.network.SyncMoneyMessage;

@Mod(modid = MoneyMod.MODID, name = MoneyMod.NAME, version = MoneyMod.VERSION)
public class MoneyMod {

	public static final String
	MODID = "money3",
	NAME = "Money Mod v3",
	VERSION = "3";

	@Mod.Instance(MODID)
	public static MoneyMod instance;

	public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		network.registerMessage(SyncMoneyMessage.Handler.class, SyncMoneyMessage.class, 0, Side.CLIENT);
		network.registerMessage(SyncMoneyMessage.Handler.class, SyncMoneyMessage.class, 0, Side.SERVER);
		MinecraftForge.EVENT_BUS.register(new EventsForge());
		FMLCommonHandler.instance().bus().register(new EventsFML());
	}

	@Mod.EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		event.registerServerCommand(new MoneyCommand());
	}

}
