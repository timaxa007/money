package timaxa007.money.v2b;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import timaxa007.money.v2b.client.gui.TraderGui;
import timaxa007.money.v2b.inventory.TraderContainer;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
		case 0:
			MoneyPlayer moneyPlayer = MoneyPlayer.get(player);
			if (moneyPlayer == null) return null;
			return new TraderContainer(player, moneyPlayer.entityTrader);
		default:return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
		case 0:
			MoneyPlayer moneyPlayer = MoneyPlayer.get(player);
			if (moneyPlayer == null) return null;
			return new TraderGui(player, moneyPlayer.entityTrader);
		default:return null;
		}
	}

}
