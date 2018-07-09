package timaxa007.money.v4;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockVendor extends Block implements ITileEntityProvider {

	public BlockVendor(Material material) {
		super(material);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityVendor();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.getTileEntity(x, y, z) instanceof TileEntityVendor) {
			player.openGui(MoneyMod.instance, 0, world, x, y, z);
			return true;
		}
		return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
	}

}
