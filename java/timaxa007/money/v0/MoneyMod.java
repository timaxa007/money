package timaxa007.money.v0;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.MinecraftForge;
import timaxa007.money.v0.network.SyncMoneyMessage;

@Mod(modid = MoneyMod.MODID, name = MoneyMod.NAME, version = MoneyMod.VERSION)
public class MoneyMod {

	public static final String
	MODID = "money0",
	NAME = "Money Mod v0",
	VERSION = "0";

	@Mod.Instance(MODID)
	public static MoneyMod instance;

	public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		network.registerMessage(SyncMoneyMessage.Handler.class, SyncMoneyMessage.class, 0, Side.CLIENT);
		network.registerMessage(SyncMoneyMessage.Handler.class, SyncMoneyMessage.class, 0, Side.SERVER);
		MinecraftForge.EVENT_BUS.register(new EventsForge());
	}

}
