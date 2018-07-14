package timaxa007.money.v5;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import timaxa007.money.v5.event.EventsFML;
import timaxa007.money.v5.event.EventsForge;
import timaxa007.money.v5.network.OpenGuiTraderMoneyMessage;
import timaxa007.money.v5.network.SyncMoneyMessage;

@Mod(modid = MoneyMod.MODID, name = MoneyMod.NAME, version = MoneyMod.VERSION)
public class MoneyMod {

	public static final String
	MODID = "money5",
	NAME = "Money Mod v5",
	VERSION = "5";

	@Mod.Instance(MODID)
	public static MoneyMod instance;

	@SidedProxy(modId = MODID, serverSide = "timaxa007.money.v5.ProxyCommon", clientSide = "timaxa007.money.v5.client.ProxyClient")
	public static ProxyCommon proxy;

	public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

	public static Item item_coin;

	public static int villagerTraderCoinProfession = 5;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		network.registerMessage(SyncMoneyMessage.Handler.class, SyncMoneyMessage.class, 0, Side.CLIENT);
		network.registerMessage(SyncMoneyMessage.Handler.class, SyncMoneyMessage.class, 0, Side.SERVER);
		network.registerMessage(OpenGuiTraderMoneyMessage.Handler.class, OpenGuiTraderMoneyMessage.class, 0, Side.CLIENT);
		network.registerMessage(OpenGuiTraderMoneyMessage.Handler.class, OpenGuiTraderMoneyMessage.class, 0, Side.SERVER);

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

		MinecraftForge.EVENT_BUS.register(new EventsForge());
		FMLCommonHandler.instance().bus().register(new EventsFML());

		item_coin = new ItemCoin().setUnlocalizedName("money5.coin").setTextureName("money5:coin").setCreativeTab(CreativeTabs.tabMisc).setHasSubtypes(true).setMaxDamage(0);
		GameRegistry.registerItem(item_coin, "item_coin");

		VillagerRegistry.instance().registerVillageCreationHandler(new VillagerCoin());
		VillagerRegistry.instance().registerVillagerSkin(villagerTraderCoinProfession, new ResourceLocation(MODID, "/trader/coin.png"));
		VillagerRegistry.instance().registerVillageTradeHandler(villagerTraderCoinProfession, new VillagerCoinTrader());

		EntityRegistry.registerModEntity(EntityCoinTrader.class, "EntityCoinTrader", 0, instance, 64, 20, true);

		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		event.registerServerCommand(new MoneyCommand());
	}

}
