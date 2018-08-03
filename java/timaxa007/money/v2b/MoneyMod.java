package timaxa007.money.v2b;

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
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import timaxa007.money.v2b.event.EventsFML;
import timaxa007.money.v2b.event.EventsForge;
import timaxa007.money.v2b.network.OpenGuiTraderMoneyMessage;
import timaxa007.money.v2b.network.SyncMoneyMessage;

@Mod(modid = MoneyMod.MODID, name = MoneyMod.NAME, version = MoneyMod.VERSION)
public class MoneyMod {

	public static final String
	MODID = "money2b",
	NAME = "Money Mod v2/b",
	VERSION = "2/b";

	@Mod.Instance(MODID)
	public static MoneyMod instance;

	@SidedProxy(modId = MODID, serverSide = "timaxa007.money.v2b.ProxyCommon", clientSide = "timaxa007.money.v2b.client.ProxyClient")
	public static ProxyCommon proxy;

	public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

	public static Item item_coin;

	public static int villagerTraderCoinProfession = 5;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		network.registerMessage(SyncMoneyMessage.Handler.class, SyncMoneyMessage.class, 0, Side.CLIENT);
		network.registerMessage(SyncMoneyMessage.Handler.class, SyncMoneyMessage.class, 0, Side.SERVER);
		network.registerMessage(OpenGuiTraderMoneyMessage.Handler.class, OpenGuiTraderMoneyMessage.class, 1, Side.CLIENT);
		network.registerMessage(OpenGuiTraderMoneyMessage.Handler.class, OpenGuiTraderMoneyMessage.class, 1, Side.SERVER);

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

		MinecraftForge.EVENT_BUS.register(new EventsForge());
		FMLCommonHandler.instance().bus().register(new EventsFML());

		item_coin = new ItemCoin().setUnlocalizedName("money2b.coin").setTextureName("money2b:coin").setCreativeTab(CreativeTabs.tabMisc).setHasSubtypes(true).setMaxDamage(0);
		GameRegistry.registerItem(item_coin, "item_coin");

		ItemCoin.coin_copper = ItemCoin.addNBT(new ItemStack(item_coin), ItemCoin.nominal_values[0]);
		ItemCoin.coin_silver = ItemCoin.addNBT(new ItemStack(item_coin), ItemCoin.nominal_values[1]);
		ItemCoin.coin_gold = ItemCoin.addNBT(new ItemStack(item_coin), ItemCoin.nominal_values[2]);

		EntityRegistry.registerModEntity(EntityCoinTrader.class, "EntityCoinTrader", 0, instance, 64, 3, true);

		//For test
		VillagerRegistry.instance().registerVillageCreationHandler(new VillagerCoin());
		if (FMLCommonHandler.instance().getEffectiveSide().isClient())
		VillagerRegistry.instance().registerVillagerSkin(villagerTraderCoinProfession, new ResourceLocation(MODID, "/textures/trader/coin.png"));
		VillagerRegistry.instance().registerVillageTradeHandler(villagerTraderCoinProfession, new VillagerCoinTrader());

		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		event.registerServerCommand(new MoneyCommand());
	}

}
