package timaxa007.money.v2b;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;

public class ItemCoin extends Item {

	public static final String KEY = "Money";

	@SideOnly(Side.CLIENT)
	private IIcon
	icon_coin_copper, icon_coin_silver, icon_coin_gold,
	icon_bag_empty, icon_bag_coin;

	public static final int[] nominal_values = new int[] {
			1,
			100,
			10000
	};

	public static final String[] name_values = new String[] {
			"copper",
			"silver",
			"gold"
	};

	public static ItemStack
	coin_copper = ItemCoin.addNBT(new ItemStack(MoneyMod.item_coin), ItemCoin.nominal_values[0]),
	coin_silver = ItemCoin.addNBT(new ItemStack(MoneyMod.item_coin), ItemCoin.nominal_values[1]),
	coin_gold = ItemCoin.addNBT(new ItemStack(MoneyMod.item_coin), ItemCoin.nominal_values[2]);

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if (player.isSneaking()) {
			if (player.capabilities.isCreativeMode)
				player.openGui(MoneyMod.instance, 2, world, (int)player.posX, (int)player.posY, (int)player.posZ);
		} else {
			MoneyPlayer moneyPlayer = MoneyPlayer.get(player);
			if (moneyPlayer == null) return super.onItemRightClick(itemStack, world, player);
			int money = 0;
			if (itemStack.hasTagCompound()) {
				NBTTagCompound nbt = itemStack.getTagCompound();
				if (nbt.hasKey(KEY, NBT.TAG_BYTE)) money = nbt.getByte(KEY);
				else if (nbt.hasKey(KEY, NBT.TAG_SHORT)) money = nbt.getShort(KEY);
				else if (nbt.hasKey(KEY, NBT.TAG_INT)) money = nbt.getInteger(KEY);
			}
			if (money != 0) {
				if (moneyPlayer.isAddMoney(money)) {
					if (player instanceof EntityPlayerMP) moneyPlayer.addMoney(money);
					if (!player.capabilities.isCreativeMode) {
						--itemStack.stackSize;
						ItemStack newMoney = addNBT(new ItemStack(this), 0);
						if (!player.inventory.addItemStackToInventory(newMoney))
							player.dropPlayerItemWithRandomChoice(newMoney, false);
					}
				}
			} else {
				if (!player.capabilities.isCreativeMode) --itemStack.stackSize;
				ItemStack newMoney = addNBT(new ItemStack(this), moneyPlayer.getMoney());
				if (!player.inventory.addItemStackToInventory(newMoney))
					player.dropPlayerItemWithRandomChoice(newMoney, false);
				moneyPlayer.setMoney(0);
			}
		}
		return super.onItemRightClick(itemStack, world, player);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		NBTTagCompound nbt = itemStack.getTagCompound();
		if (nbt != null) {
			int money = 0;
			if (nbt.hasKey(KEY, NBT.TAG_BYTE)) money = nbt.getByte(KEY);
			else if (nbt.hasKey(KEY, NBT.TAG_SHORT)) money = nbt.getShort(KEY);
			else if (nbt.hasKey(KEY, NBT.TAG_INT)) money = nbt.getInteger(KEY);
			if (money != 0) {
				if (money == 1)
					return super.getUnlocalizedName(itemStack) + ".copper";
				else if (money == 100)
					return super.getUnlocalizedName(itemStack) + ".silver";
				else if (money == 10000)
					return super.getUnlocalizedName(itemStack) + ".gold";
				else return "item.money2b.bag_coin";
			} else return "item.money2b.bag_coin.empty";
		}
		return super.getUnlocalizedName(itemStack);
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemStack) {
		NBTTagCompound nbt = itemStack.getTagCompound();
		if (nbt != null) {
			int money = 0;
			if (nbt.hasKey(KEY, NBT.TAG_BYTE)) money = nbt.getByte(KEY);
			else if (nbt.hasKey(KEY, NBT.TAG_SHORT)) money = nbt.getShort(KEY);
			else if (nbt.hasKey(KEY, NBT.TAG_INT)) money = nbt.getInteger(KEY);
			if (money != 0) {
				if (money == 1)
					StatCollector.translateToLocalFormatted("money2b.copper.name", new Object[] {money});
				else if (money == 100)
					StatCollector.translateToLocalFormatted("money2b.silver.name", new Object[] {money / 100});
				else if (money == 10000)
					StatCollector.translateToLocalFormatted("money2b.gold.name", new Object[] {money / 100});
				else
					StatCollector.translateToLocalFormatted("money2b.value.name", new Object[] {money / 100, money % 100});
			} else
				StatCollector.translateToLocal("money2b.none.desc");
		}
		return super.getItemStackDisplayName(itemStack);
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean flag) {
		NBTTagCompound nbt = itemStack.getTagCompound();
		if (nbt != null) {
			int money = 0;
			if (nbt.hasKey(KEY, NBT.TAG_BYTE)) money = nbt.getByte(KEY);
			else if (nbt.hasKey(KEY, NBT.TAG_SHORT)) money = nbt.getShort(KEY);
			else if (nbt.hasKey(KEY, NBT.TAG_INT)) money = nbt.getInteger(KEY);
			if (money != 0) {
				if (money == 1);
				//list.add(StatCollector.translateToLocalFormatted("money2b.copper.desc", new Object[] {money}));
				else if (money == 100);
				//list.add(StatCollector.translateToLocalFormatted("money2b.silver.desc", new Object[] {money / 100}));
				else if (money == 10000);
				//list.add(StatCollector.translateToLocalFormatted("money2b.gold.desc", new Object[] {money / 10000}));
				else {
					int p = 0;
					if ((p = money / 10000) > 0)
						list.add(StatCollector.translateToLocalFormatted("money2b.gold.desc", new Object[] {p}));
					if ((p = (money / 100) % 100) != 0)
						list.add(StatCollector.translateToLocalFormatted("money2b.silver.desc", new Object[] {p}));
					if ((p = money % 100) != 0)
						list.add(StatCollector.translateToLocalFormatted("money2b.copper.desc", new Object[] {p}));
					//list.add(StatCollector.translateToLocalFormatted("money2b.value.desc", new Object[] {money / 100, money % 100}));
				}
			} else
				list.add(StatCollector.translateToLocal("money2b.none.desc"));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item id, CreativeTabs table, List list) {
		//list.add(new ItemStack(id, 1, 0));
		for (int value : nominal_values)
			list.add(addNBT(new ItemStack(id, 1, 0), value));
		list.add(addNBT(new ItemStack(id, 1, 0), 0));//For test or no
		list.add(addNBT(new ItemStack(id, 1, 0), 10));//For test
		list.add(addNBT(new ItemStack(id, 1, 0), 1000));//For test
		list.add(addNBT(new ItemStack(id, 1, 0), 100000));//For test
		list.add(addNBT(new ItemStack(id, 1, 0), 10101));//For test
		list.add(addNBT(new ItemStack(id, 1, 0), 10001));//For test
		list.add(addNBT(new ItemStack(id, 1, 0), 11111));//For test
		list.add(addNBT(new ItemStack(id, 1, 0), 111111));//For test
		list.add(addNBT(new ItemStack(id, 1, 0), 1111111));//For test
		list.add(addNBT(new ItemStack(id, 1, 0), Byte.MAX_VALUE));//For test
		list.add(addNBT(new ItemStack(id, 1, 0), Short.MAX_VALUE));//For test
		list.add(addNBT(new ItemStack(id, 1, 0), Integer.MAX_VALUE));//For test
	}

	public static ItemStack addNBT(ItemStack itemStack, byte money) {
		if (!itemStack.hasTagCompound()) itemStack.setTagCompound(new NBTTagCompound());
		itemStack.getTagCompound().setByte(KEY, (byte)money);
		return itemStack;
	}

	public static ItemStack addNBT(ItemStack itemStack, short money) {
		if (!itemStack.hasTagCompound()) itemStack.setTagCompound(new NBTTagCompound());
		itemStack.getTagCompound().setShort(KEY, (short)money);
		return itemStack;
	}

	public static ItemStack addNBT(ItemStack itemStack, int money) {
		if (money >= Byte.MIN_VALUE && money <= Byte.MAX_VALUE)
			return addNBT(itemStack, (byte)money);
		else if (money >= Short.MIN_VALUE && money <= Short.MAX_VALUE)
			return addNBT(itemStack, (short)money);
		else {
			if (!itemStack.hasTagCompound()) itemStack.setTagCompound(new NBTTagCompound());
			itemStack.getTagCompound().setInteger(KEY, money);
			return itemStack;
		}
	}

	public ArrayList<ItemStack> splitMoney(int money) {
		return splitMoney(money, 10000);
	}

	public ArrayList<ItemStack> splitMoney(int money, byte zeros) {
		int multi = 1;
		for (byte i = 0; i < zeros; ++i) multi *= 10;
		return splitMoney(money, multi);
	}

	public ArrayList<ItemStack> splitMoney(int money, int zeros) {
		if (zeros > 1 && zeros < 10) return splitMoney(money, (byte)zeros);
		final ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		int start;
		while (zeros > 0) {
			start = money / zeros;
			if (start > 0) {
				money -= start * zeros;
				ItemStack itemMoney = new ItemStack(this);

				while (start > 0) {
					if (start >= itemMoney.getMaxStackSize()) {
						itemMoney.stackSize = itemMoney.getMaxStackSize();
						list.add(addNBT(itemMoney.copy(), zeros));
						start -= itemMoney.getMaxStackSize();
					} else {
						itemMoney.stackSize = start;
						list.add(addNBT(itemMoney.copy(), zeros));
						start = 0;
					}
				}

			}
			zeros /= 100;
		}
		return list;
	}
	/*если использовать только текстуру coin'а
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack itemStack, int pass) {
		NBTTagCompound nbt = itemStack.getTagCompound();
		if (nbt != null) {
			int money = 0;
			if (nbt.hasKey(KEY, NBT.TAG_BYTE)) money = nbt.getByte(KEY);
			else if (nbt.hasKey(KEY, NBT.TAG_SHORT)) money = nbt.getShort(KEY);
			else if (nbt.hasKey(KEY, NBT.TAG_INT)) money = nbt.getInteger(KEY);
			if (money != 0) {
				if (money < 100) return 0xCC5F00;
				else if (money >= 100 && money < 10000 && money % 100 == 0) return 0xAFAFAF;
				else if (money >= 10000 && money % 10000 == 0) return 0xFFB200;
			}
		}
		return super.getColorFromItemStack(itemStack, pass);
	}
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack itemStack, int pass) {
		NBTTagCompound nbt = itemStack.getTagCompound();
		if (nbt != null) {
			int money = 0;
			if (nbt.hasKey(KEY, NBT.TAG_BYTE)) money = nbt.getByte(KEY);
			else if (nbt.hasKey(KEY, NBT.TAG_SHORT)) money = nbt.getShort(KEY);
			else if (nbt.hasKey(KEY, NBT.TAG_INT)) money = nbt.getInteger(KEY);
			if (money != 0) {
				if (money == 1) return icon_coin_copper;
				else if (money == 100) return icon_coin_silver;
				else if (money == 10000) return icon_coin_gold;
				else return icon_bag_coin;
			} else return icon_bag_empty;
		}
		return super.getIcon(itemStack, pass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) {
		super.registerIcons(ir);
		icon_coin_copper = ir.registerIcon(getIconString() + "_copper");
		icon_coin_silver = ir.registerIcon(getIconString() + "_silver");
		icon_coin_gold = ir.registerIcon(getIconString() + "_gold");
		icon_bag_coin = ir.registerIcon("money2b:bag_coin");
		icon_bag_empty = ir.registerIcon("money2b:bag_empty");
	}

}
