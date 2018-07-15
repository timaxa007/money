package timaxa007.money.v5.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import timaxa007.money.v5.ItemCoin;
import timaxa007.money.v5.MoneyMod;
import timaxa007.money.v5.MoneyPlayer;
import timaxa007.money.v5.network.SyncMoneyMessage;

public class EventsForge {

	@SubscribeEvent
	public void addEntityConstructing(EntityEvent.EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)event.entity;
			if (MoneyPlayer.get(player) == null) MoneyPlayer.reg(player);
		}
	}

	@SubscribeEvent
	public void cloneMoneyPlayer(PlayerEvent.Clone event) {
		MoneyPlayer originalMoneyPlayer = MoneyPlayer.get((EntityPlayer)event.original);
		if (originalMoneyPlayer == null) return;
		MoneyPlayer newMoneyPlayer = MoneyPlayer.get((EntityPlayer)event.entityPlayer);
		if (newMoneyPlayer == null) return;
		newMoneyPlayer.setMoney(originalMoneyPlayer.getMoney());
	}

	@SubscribeEvent
	public void syncMoneyPlayer(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP)event.entity;
			MoneyPlayer moneyPlayer = MoneyPlayer.get(player);
			if (moneyPlayer == null) return;
			SyncMoneyMessage message = new SyncMoneyMessage();
			message.money = moneyPlayer.getMoney();
			MoneyMod.network.sendTo(message, player);
		}
	}
	/*
	@SubscribeEvent
	public void replaceEntityItemCoin(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityItemCoin) return;
		if (!(event.entity instanceof EntityItem)) return;
		EntityItem entityItem = (EntityItem)event.entity;
		ItemStack itemStack = entityItem.getEntityItem();
		if (!(itemStack.getItem() instanceof ItemCoin)) return;
		if (!event.world.isRemote) {
			EntityItemCoin entityItemCoin = new EntityItemCoin(entityItem.worldObj, entityItem.posX, entityItem.posY, entityItem.posZ, itemStack);
			entityItemCoin.delayBeforeCanPickup = 80;
			entityItemCoin.age = entityItem.age;
			entityItemCoin.lifespan = entityItem.lifespan * 2;
			event.world.spawnEntityInWorld(entityItemCoin);
		}
		event.setCanceled(true);
	}*/

	@SubscribeEvent
	public void entityItemPickupEvent(EntityItemPickupEvent event) {
		if (!(event.entityPlayer instanceof EntityPlayerMP)) return;
		MoneyPlayer moneyPlayer = MoneyPlayer.get(event.entityPlayer);
		if (moneyPlayer == null) return;
		ItemStack itemStack = event.item.getEntityItem();
		if (itemStack.getItem() instanceof ItemCoin) {
			if (!itemStack.hasTagCompound()) return;
			NBTTagCompound nbt = itemStack.getTagCompound();
			int money = 0;
			if (nbt.hasKey(ItemCoin.KEY, NBT.TAG_BYTE)) money = nbt.getByte(ItemCoin.KEY);
			else if (nbt.hasKey(ItemCoin.KEY, NBT.TAG_SHORT)) money = nbt.getShort(ItemCoin.KEY);
			else if (nbt.hasKey(ItemCoin.KEY, NBT.TAG_INT)) money = nbt.getInteger(ItemCoin.KEY);
			if (money <= 0) return;
			money *= itemStack.stackSize;
			if (moneyPlayer.isAddMoney(money)) {
				moneyPlayer.addMoney(money);
				event.item.setDead();
				event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "random.levelup", 0.15F, (event.entityPlayer.worldObj.rand.nextFloat() + 5F) / 7F);
				event.setCanceled(true);
			}
		}
	}

}
