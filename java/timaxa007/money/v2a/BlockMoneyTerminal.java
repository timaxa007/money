package timaxa007.money.v2a;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockMoneyTerminal extends Block {

	public BlockMoneyTerminal(Material material) {
		super(material);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		player.openGui(MoneyMod.instance, 1, world, x, y, z);
		return true;
		//return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
	}

}
