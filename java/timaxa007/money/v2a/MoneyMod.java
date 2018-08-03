package timaxa007.money.v2a;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import timaxa007.money.v2a.network.SyncMoneyMessage;

@Mod(modid = MoneyMod.MODID, name = MoneyMod.NAME, version = MoneyMod.VERSION)
public class MoneyMod {

	public static final String
	MODID = "money2a",
	NAME = "Money Mod v2/a",
	VERSION = "2/a";

	@Mod.Instance(MODID)
	public static MoneyMod instance;

	@SidedProxy(modId = MODID,
			serverSide = "timaxa007.money.v2a.ProxyCommon",
			clientSide = "timaxa007.money.v2a.client.ProxyClient")
	public static ProxyCommon proxy;

	public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

	public static Block
	block_money_terminal,
	block_vendor;
	
	public static Item item_money;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		network.registerMessage(SyncMoneyMessage.Handler.class, SyncMoneyMessage.class, 0, Side.CLIENT);
		network.registerMessage(SyncMoneyMessage.Handler.class, SyncMoneyMessage.class, 0, Side.SERVER);
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
		MinecraftForge.EVENT_BUS.register(new EventsForge());
		FMLCommonHandler.instance().bus().register(new EventsFML());

		block_money_terminal = new BlockMoneyTerminal(Material.iron).setBlockName("money2a.money_terminal").setBlockTextureName("money2a:money_terminal").setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerBlock(block_money_terminal, "block_money_terminal");
		
		block_vendor = new BlockVendor(Material.iron).setBlockName("money2a.vendor").setBlockTextureName("money2a:vendor").setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerBlock(block_vendor, "block_vendor");
		GameRegistry.registerTileEntity(TileEntityVendor.class, "money2a:Vendor");

		item_money = new ItemMoney().setUnlocalizedName("money2a.money").setTextureName("money2a:money").setCreativeTab(CreativeTabs.tabMisc).setHasSubtypes(true).setMaxDamage(0);
		GameRegistry.registerItem(item_money, "item_money");

		proxy.preInit();
	}

	@Mod.EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		event.registerServerCommand(new MoneyCommand());
	}

}
