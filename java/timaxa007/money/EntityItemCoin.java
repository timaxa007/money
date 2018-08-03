package timaxa007.money;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import timaxa007.money.v2b.ItemCoin;

public class EntityItemCoin extends EntityItem {

	public EntityItemCoin(World world) {
		super(world);
	}

	public EntityItemCoin(World world, double posX, double posY, double posZ, ItemStack itemStack) {
		super(world, posX, posY, posZ);        
		this.setEntityItemStack(itemStack);
		this.lifespan =  itemStack.getItem().getEntityLifespan(itemStack, world) * 2;
	}

	public void onCollideWithPlayer(EntityPlayer player) {
			if (this.delayBeforeCanPickup > 0) return;
			System.out.println("onCollideWithPlayer");

			ItemStack itemStack = this.getEntityItem();
			if (!itemStack.hasTagCompound()) return;
			NBTTagCompound nbt = itemStack.getTagCompound();
			int money = 0;
			if (nbt.hasKey(ItemCoin.KEY, NBT.TAG_BYTE)) money = nbt.getByte(ItemCoin.KEY);
			else if (nbt.hasKey(ItemCoin.KEY, NBT.TAG_SHORT)) money = nbt.getShort(ItemCoin.KEY);
			else if (nbt.hasKey(ItemCoin.KEY, NBT.TAG_INT)) money = nbt.getInteger(ItemCoin.KEY);
			money *= itemStack.stackSize;
			MoneyPlayer moneyPlayer = MoneyPlayer.get(player);
			if (moneyPlayer != null && moneyPlayer.isAddMoney(money)) {
				moneyPlayer.addMoney(money);
				this.setDead();
			} else super.onCollideWithPlayer(player);
			/*
            EntityItemPickupEvent event = new EntityItemPickupEvent(player, this);

            if (MinecraftForge.EVENT_BUS.post(event)) return;

            ItemStack itemstack = this.getEntityItem();
            int i = itemstack.stackSize;

            if (this.delayBeforeCanPickup <= 0 && (this.func_145798_i() == null || lifespan - this.age <= 200 || this.func_145798_i().equals(player.getCommandSenderName())) && (event.getResult() == Result.ALLOW || i <= 0 || player.inventory.addItemStackToInventory(itemstack))) {
                FMLCommonHandler.instance().firePlayerItemPickupEvent(player, this);
                this.worldObj.playSoundAtEntity(player, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                player.onItemPickup(this, i);
                if (itemstack.stackSize <= 0) this.setDead();
            }
			 */
	}

}
