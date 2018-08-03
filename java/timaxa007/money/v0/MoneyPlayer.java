package timaxa007.money.v0;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.util.Constants.NBT;
import timaxa007.money.v0.network.SyncMoneyMessage;

public class MoneyPlayer implements IExtendedEntityProperties {

	private static final String ID = "MoneyPlayer.v0";
	private EntityPlayer player;
	private int money;

	@Override
	public void saveNBTData(NBTTagCompound nbt) {
		NBTTagCompound tag = new NBTTagCompound();
		if (money != 0)
			tag.setInteger("money", money);
		if (!tag.hasNoTags())
			nbt.setTag(ID, tag);
	}

	@Override
	public void loadNBTData(NBTTagCompound nbt) {
		if (nbt.hasKey(ID, NBT.TAG_COMPOUND)) {
			NBTTagCompound tag = nbt.getCompoundTag(ID);
			if (tag.hasKey("money", NBT.TAG_INT))
				money = tag.getInteger("money");
		}
	}

	@Override
	public void init(Entity entity, World world) {
		if (entity instanceof EntityPlayer) player = (EntityPlayer)entity;
	}

	public static String reg(EntityPlayer player) {
		return player.registerExtendedProperties(MoneyPlayer.ID, new MoneyPlayer());
	}

	public static MoneyPlayer get(EntityPlayer player) {
		return (MoneyPlayer)player.getExtendedProperties(MoneyPlayer.ID);
	}

	public boolean isAddMoney(int money) {
		money += getMoney();
		return money >= 0 && money <= Integer.MAX_VALUE;
	}

	public void addMoney(final int money) {
		setMoney(money + getMoney());
	}

	public void setMoney(int money) {
		this.money = money;
		if (player instanceof EntityPlayerMP) {
			SyncMoneyMessage message = new SyncMoneyMessage();
			message.money = money;
			MoneyMod.network.sendTo(message, (EntityPlayerMP)player);
		}
	}

	public int getMoney() {
		return money;
	}

}
