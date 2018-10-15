package timaxa007.money.v2b.event;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import timaxa007.money.v2b.ItemCoin;
import timaxa007.money.v2b.MoneyMod;
import timaxa007.money.v2b.MoneyPlayer;
import timaxa007.money.v2b.network.SyncMoneyMessage;

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

	@SubscribeEvent
	public void onLivingDropsEvent(LivingDropsEvent event) {
		if (event.entityLiving.isChild()) return;//Плохо убивать детей ради монет.
		Entity from = event.source.getSourceOfDamage();//Кто убил.
		if (!(from instanceof EntityPlayer)) return;//Не игрок.
		int i = (int)(event.entityLiving.getMaxHealth() / 4);
		if (i <= 0) return;
		if (event.entityLiving.dimension != 0) i *= 2;//Не Overworld, то количество монет удваеться
		i = event.entityLiving.worldObj.rand.nextInt(i);//Рандомное количество монет
		if (i <= 0) return;
		for (ItemStack cop : ((ItemCoin)MoneyMod.item_coin).splitItemMoney(i))
			event.drops.add(new EntityItem(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, cop));
	}

	@SubscribeEvent(priority=EventPriority.LOW)
	public void blockHurt(LivingHurtEvent event) {
		if (event.entityLiving.worldObj.isRemote) return;
		if (event.entityLiving.isChild()) return;//Плохо бить детей ради монет.
		Entity from = event.source.getSourceOfDamage();//Кто ударил.
		if (!(from instanceof EntityPlayer)) return;//Кто - не игрок.
		int i = (int)(event.ammount / 3.5F);
		if (i <= 0) return;
		if (event.entityLiving.dimension != 0) i *= 2;//Не Overworld, то количество монет удваеться
		i = event.entityLiving.worldObj.rand.nextInt(i);//Рандомное количество монет
		if (i <= 0) return;
		for (ItemStack cop : ((ItemCoin)MoneyMod.item_coin).splitItemMoney(i))
			event.entityLiving.worldObj.spawnEntityInWorld(new EntityItem(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, cop));
	}

}
