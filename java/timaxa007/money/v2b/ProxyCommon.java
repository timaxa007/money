package timaxa007.money.v2b;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import timaxa007.money.v2b.entity.EntityCoinTrader;
import timaxa007.money.v2b.inventory.TraderContainer;
import timaxa007.money.v2b.network.OpenGuiEntityMessage;
import timaxa007.money.v2b.network.OpenGuiMessage;
import timaxa007.money.v2b.network.OpenGuiPositionMessage;

public class ProxyCommon {

	public void preInit(FMLPreInitializationEvent event) {

	}

	public Object getGui(final int id, final EntityPlayer player) {
		return null;
	}

	public Object getContainer(final int id, final EntityPlayer player) {
		return null;
	}

	public Object getGui(final int id, final EntityPlayer player, final Entity entity) {
		return null;
	}

	public Object getContainer(final int id, final EntityPlayer player, final Entity entity) {
		switch(id) {
		case 0:
			MoneyPlayer moneyPlayer = MoneyPlayer.get(player);
			if (moneyPlayer == null) return null;
			if (!(entity instanceof EntityCoinTrader)) return null;
			return new TraderContainer(player, (EntityCoinTrader)entity);
		default:return null;
		}
	}

	public Object getGui(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z) {
		return null;
	}

	public Object getContainer(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z) {
		return null;
	}

	//FMLNetworkHandler.openGui(EntityPlayer entityPlayer, Object mod, int modGuiId, World world, int x, int y, int z)

	public void openGui(byte id, EntityPlayer player) {
		if (player instanceof EntityPlayerMP) {
			EntityPlayerMP playerMP = (EntityPlayerMP)player;
			Object object = MoneyMod.proxy.getContainer(id, player);
			if (!(object instanceof Container)) return;
			playerMP.closeContainer();
			playerMP.getNextWindowId();
			int windowId = playerMP.currentWindowId;

			OpenGuiMessage message = new OpenGuiMessage();
			message.windowID = windowId;
			message.id = id;
			MoneyMod.network.sendTo(message, playerMP);

			player.openContainer = (Container)object;
			playerMP.openContainer.windowId = windowId;
			playerMP.openContainer.addCraftingToCrafters(playerMP);
		}
	}

	public void openGui(byte id, EntityPlayer player, Entity entity) {
		if (player instanceof EntityPlayerMP) {
			EntityPlayerMP playerMP = (EntityPlayerMP)player;
			Object object = MoneyMod.proxy.getContainer(id, player, entity);
			if (!(object instanceof Container)) return;
			playerMP.closeContainer();
			playerMP.getNextWindowId();
			int windowId = playerMP.currentWindowId;

			OpenGuiEntityMessage message = new OpenGuiEntityMessage();
			message.windowID = windowId;
			message.id = id;
			message.entityID = entity.getEntityId();
			MoneyMod.network.sendTo(message, playerMP);

			player.openContainer = (Container)object;
			playerMP.openContainer.windowId = windowId;
			playerMP.openContainer.addCraftingToCrafters(playerMP);
		}
	}

	public void openGui(byte id, EntityPlayer player, World world, int x, int y, int z) {
		if (player instanceof EntityPlayerMP) {
			EntityPlayerMP playerMP = (EntityPlayerMP)player;
			Object object = MoneyMod.proxy.getContainer(id, player, world, x, y, z);
			if (!(object instanceof Container)) return;
			playerMP.closeContainer();
			playerMP.getNextWindowId();
			int windowId = playerMP.currentWindowId;

			OpenGuiPositionMessage message = new OpenGuiPositionMessage();
			message.windowID = windowId;
			message.id = id;
			message.x = x;
			message.y = y;
			message.z = z;
			MoneyMod.network.sendTo(message, playerMP);

			player.openContainer = (Container)object;
			playerMP.openContainer.windowId = windowId;
			playerMP.openContainer.addCraftingToCrafters(playerMP);
		}
	}

}
