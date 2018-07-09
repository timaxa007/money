package timaxa007.money.v4;

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
import timaxa007.money.v4.network.SyncMoneyMessage;

@Mod(modid = MoneyMod.MODID, name = MoneyMod.NAME, version = MoneyMod.VERSION)
public class MoneyMod {

	public static final String
	MODID = "money4",
	NAME = "Money Mod v4",
	VERSION = "4";

	@Mod.Instance(MODID)
	public static MoneyMod instance;

	@SidedProxy(modId = MODID,
			serverSide = "timaxa007.money.v4.ProxyCommon",
			clientSide = "timaxa007.money.v4.client.ProxyClient")
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

		block_money_terminal = new BlockMoneyTerminal(Material.iron).setBlockName("money4.money_terminal").setBlockTextureName("money4:money_terminal").setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerBlock(block_money_terminal, "block_money_terminal");
		
		block_vendor = new BlockVendor(Material.iron).setBlockName("money4.vendor").setBlockTextureName("money4:vendor").setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerBlock(block_vendor, "block_vendor");
		GameRegistry.registerTileEntity(TileEntityVendor.class, "money4:Vendor");

		item_money = new ItemMoney().setUnlocalizedName("money4.money").setTextureName("money4:money").setCreativeTab(CreativeTabs.tabMisc).setHasSubtypes(true).setMaxDamage(0);
		GameRegistry.registerItem(item_money, "item_money");

		proxy.preInit();
	}

	@Mod.EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		event.registerServerCommand(new MoneyCommand());
	}

}
