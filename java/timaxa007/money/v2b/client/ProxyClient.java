package timaxa007.money.v2b.client;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import timaxa007.money.v2b.MoneyMod;
import timaxa007.money.v2b.MoneyPlayer;
import timaxa007.money.v2b.ProxyCommon;
import timaxa007.money.v2b.client.gui.TraderGui;
import timaxa007.money.v2b.entity.EntityCoinTrader;

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

	@Override
	public Object getGui(final int id, final EntityPlayer player) {
		return null;
	}

	@Override
	public Object getGui(final int id, final EntityPlayer player, final Entity entity) {
		switch(id) {
		case 0:
			MoneyPlayer moneyPlayer = MoneyPlayer.get(player);
			if (moneyPlayer == null) return null;
			if (!(entity instanceof EntityCoinTrader)) return null;
			return new TraderGui(player, (EntityCoinTrader)entity);
		default:return null;
		}
	}

	@Override
	public Object getGui(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z) {
		return null;
	}

	//FMLNetworkHandler.openGui(EntityPlayer entityPlayer, Object mod, int modGuiId, World world, int x, int y, int z)

	@Override
	public void openGui(byte id, EntityPlayer player) {
		if (player instanceof EntityPlayerMP)
			super.openGui(id, player);
		else if (FMLCommonHandler.instance().getSide().equals(Side.CLIENT)) {
			Object object = MoneyMod.proxy.getGui(id, player);
			if (!(object instanceof GuiScreen)) return;
			Minecraft.getMinecraft().displayGuiScreen((GuiScreen)object);
		}
	}

	@Override
	public void openGui(byte id, EntityPlayer player, Entity entity) {
		if (player instanceof EntityPlayerMP)
			super.openGui(id, player, entity);
		else if (FMLCommonHandler.instance().getSide().equals(Side.CLIENT)) {
			Object object = MoneyMod.proxy.getGui(id, player, entity);
			if (!(object instanceof GuiScreen)) return;
			Minecraft.getMinecraft().displayGuiScreen((GuiScreen)object);
		}
	}

	@Override
	public void openGui(byte id, EntityPlayer player, World world, int x, int y, int z) {
		if (player instanceof EntityPlayerMP)
			super.openGui(id, player, world, x, y, z);
		else if (FMLCommonHandler.instance().getSide().equals(Side.CLIENT)) {
			Object object = MoneyMod.proxy.getGui(id, player, world, x, y, z);
			if (!(object instanceof GuiScreen)) return;
			Minecraft.getMinecraft().displayGuiScreen((GuiScreen)object);
		}
	}

}
