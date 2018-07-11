package timaxa007.money.v5;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.util.Constants.NBT;
import timaxa007.money.v5.network.SyncMoneyMessage;

public class MoneyPlayer implements IExtendedEntityProperties {

	private static final String ID = "MoneyPlayer.v5";
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

	public boolean isAddCopper(int copper) {
		copper += getMoney();
		return copper >= 0 && copper <= Integer.MAX_VALUE;
	}

	public boolean isAddSilver(int silver) {
		silver *= 100;
		silver += getMoney();
		return silver >= 0 && silver <= Integer.MAX_VALUE;
	}

	public boolean isAddGold(int gold) {
		gold *= 10000;
		gold += getMoney();
		return gold >= 0 && gold <= Integer.MAX_VALUE;
	}

	public void addMoney(final int money) {
		setMoney(money + getMoney());
	}

	public void addCopper(int copper) {
		setMoney(getMoney() + copper);
	}

	public void addSilver(int silver) {
		setMoney(getMoney() + (silver * 100));
	}

	public void addGold(int gold) {
		setMoney(getMoney() + (gold * 10000));
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

	public int getCopper() {
		return money % 100;
	}

	public int getSilver() {
		return (money / 100) % 100;
	}

	public int getGold() {
		return money / 10000;
	}

}
