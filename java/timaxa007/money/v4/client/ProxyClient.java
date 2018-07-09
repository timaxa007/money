package timaxa007.money.v4.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import timaxa007.money.v4.ProxyCommon;

public class ProxyClient extends ProxyCommon {

	@Override
	public void preInit() {

	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return super.getServerGuiElement(ID, player, world, x, y, z);
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		switch(ID) {
		case 0:

			return null;
		default:return super.getClientGuiElement(ID, player, world, x, y, z);
		}
	}

}
