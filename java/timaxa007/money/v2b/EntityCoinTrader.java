package timaxa007.money.v2b;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.INpc;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import timaxa007.money.v2b.inventory.TraderContainer;
import timaxa007.money.v2b.network.OpenGuiTraderMoneyMessage;

public class EntityCoinTrader extends EntityCreature implements INpc {

	public final InventoryBasic inventory = new InventoryBasic("InventoryCoinTrader", false, 9 * 3);

	public EntityCoinTrader(World world) {
		super(world);
		setSize(0.8F, 1.8F);
	}

	@Override
	protected boolean interact(EntityPlayer player) {
		System.out.println("asdddddddddddddddd - " + this.getEntityId());
		/*
		if (worldObj.isRemote) {
			OpenGuiTraderMoneyMessage message = new OpenGuiTraderMoneyMessage();
			message.entityID = getEntityId();
			MoneyMod.network.sendToServer(message);
		}
		 */
		/*
		if (player instanceof EntityPlayerMP) {
			System.out.println("openContainer");
			player.openContainer = new TraderContainer(player, this);
			OpenGuiTraderMoneyMessage message = new OpenGuiTraderMoneyMessage();
			message.entityID = getEntityId();
			MoneyMod.network.sendTo(message, (EntityPlayerMP)player);
			//((EntityPlayerMP)player).sendContainerToPlayer(new TraderContainer(player, this));
		}
		 */
		if (player instanceof EntityPlayerMP) {
			System.out.println("openContainer");

			MoneyPlayer moneyPlayer = MoneyPlayer.get(player);
			if (moneyPlayer == null) return true;
			
			moneyPlayer.entityTrader = this;
			OpenGuiTraderMoneyMessage message = new OpenGuiTraderMoneyMessage();
			message.entityID = getEntityId();
			MoneyMod.network.sendTo(message, (EntityPlayerMP)player);
			//((EntityPlayerMP)player).sendContainerToPlayer(new TraderContainer(player, this));
			player.openGui(MoneyMod.instance, 0, player.worldObj, -1, -1, -1);
		}
		return true;
	}

	public void onDeath(DamageSource damageSource) {
		super.onDeath(damageSource);

	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		NBTTagList list = new NBTTagList();
		for (int i = 0; i < inventory.getSizeInventory(); ++i) {
			if (inventory.getStackInSlot(i) == null) continue;
			NBTTagCompound slot_nbt = new NBTTagCompound();
			inventory.getStackInSlot(i).writeToNBT(slot_nbt);
			slot_nbt.setByte("Slot", (byte)i);
			list.appendTag(slot_nbt);
		}
		nbt.setTag("Inventory", list);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		if (nbt.hasKey("Inventory", NBT.TAG_LIST)) {
			NBTTagList list = nbt.getTagList("Inventory", NBT.TAG_COMPOUND);
			for (int i = 0; i < list.tagCount(); ++i) {
				NBTTagCompound slot_nbt = list.getCompoundTagAt(i);
				inventory.setInventorySlotContents((slot_nbt.getByte("Slot") & 255), ItemStack.loadItemStackFromNBT(slot_nbt));
			}
		}
	}

}
