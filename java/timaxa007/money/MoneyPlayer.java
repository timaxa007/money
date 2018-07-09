package timaxa007.money;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class MoneyPlayer implements IExtendedEntityProperties {

	private static final String TAG = "MoneyPlayer";
	int money;

	@Override
	public void saveNBTData(NBTTagCompound nbt) {

	}

	@Override
	public void loadNBTData(NBTTagCompound nbt) {

	}

	@Override
	public void init(Entity entity, World world) {}

	public static String reg(EntityPlayer player) {
		return player.registerExtendedProperties(MoneyPlayer.TAG, new MoneyPlayer());
	}

	public static MoneyPlayer get(EntityPlayer player) {
		return (MoneyPlayer)player.getExtendedProperties(MoneyPlayer.TAG);
	}

	public boolean isAddMoney(int money) {
		money += this.money;
		return money > 0 && money <= Integer.MAX_VALUE;
	}

	public void addMoney(final int money) {
		setMoney(money + getMoney());
	}

	public void addCopper(int copper) {
		setMoney(getMoney() + copper);
	}

	public void addSilver(int silver) {
		setMoney(getMoney() + (silver * 1000));
	}

	public void addGold(int gold) {
		setMoney(getMoney() + (gold * 1000000));
	}
	/*
	public void addCopper(int copper) {
		copper += getCopper();
		setMoney(getMoney() - copper);
		if (copper >= 1000) {
			addSilver(copper / 1000);
			copper %= 1000;
		}
		setMoney(getMoney() + copper);
	}

	public void addSilver(int silver) {
		silver += getSilver();
		setMoney(getMoney() - silver);
		if (silver >= 1000) {
			addGold(silver / 1000);
			silver %= 1000;
		}
		setMoney(getMoney() + (silver * 1000));
	}

	public void addGold(int gold) {
		//gold += getGold();
		//setMoney(getMoney() - gold);
		setMoney(getMoney() + (gold * 1000000));
	}
	 */
	/*
	public void addMoney(int money) {
		//int copper = money & 1023;
		//int silver = money >> 10 & 1023;
		//int gold = money >> 20 & 1023;

		int copper = money % 1000;
		int silver = (money / 1000) % 1000;
		int gold = money / 1000000;

		copper += getCopper();
		if (copper >= 1000) {
			silver += copper / 1000;
			copper %= 1000;
		}

		silver += getSilver();
		if (silver >= 1000) {
			gold += silver / 1000;
			silver %= 1000;
		}

		gold += getGold();

		setMoney((gold * 1000000) + (silver * 1000) + copper);
	}
	 */
	public void setMoney(int money) {
		this.money = money;
	}

	public int getMoney() {
		return money;
	}

	public int getCopper() {
		//return money & 1023;
		return money % 1000;
	}

	public int getSilver() {
		//return money >> 10 & 1023;
		return (money / 1000) % 1000;
	}

	public int getGold() {
		//return money >> 20 & 1023;
		return money / 1000000;
	}

}
