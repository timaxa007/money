package timaxa007.money.v2a;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;

public class ItemMoney extends Item {

	@SideOnly(Side.CLIENT)
	private IIcon icon_coint, icon_check;

	public static final int[] nominal_values = new int[] {
			1,
			5,
			10,
			25,
			50,
			100,
			500,
			1000,
			5000,
			10000,
			50000,
			100000
	};

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
				if (nbt.hasKey("Money", NBT.TAG_BYTE)) money = nbt.getByte("Money");
				else if (nbt.hasKey("Money", NBT.TAG_SHORT)) money = nbt.getShort("Money");
				else if (nbt.hasKey("Money", NBT.TAG_INT)) money = nbt.getInteger("Money");
			}
			if (!player.capabilities.isCreativeMode) --itemStack.stackSize;
			if (money != 0) {
				moneyPlayer.addMoney(money);
			} else {
				ItemStack newMoney = new ItemStack(this);
				addNBT(newMoney, moneyPlayer.getMoney());
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
			if (nbt.hasKey("Money", NBT.TAG_BYTE)) money = nbt.getByte("Money");
			else if (nbt.hasKey("Money", NBT.TAG_SHORT)) money = nbt.getShort("Money");
			else if (nbt.hasKey("Money", NBT.TAG_INT)) money = nbt.getInteger("Money");
			if (money != 0) {
				if (money < 100 && money > -100)
					return "item.money2a.coint";
				else if (money > 100 && money % 100 != 0)
					return "item.money2a.check";
			} else return "item.money2a.check";
		}
		return super.getUnlocalizedName(itemStack);
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemStack) {
		NBTTagCompound nbt = itemStack.getTagCompound();
		if (nbt != null) {
			int money = 0;
			if (nbt.hasKey("Money", NBT.TAG_BYTE)) money = nbt.getByte("Money");
			else if (nbt.hasKey("Money", NBT.TAG_SHORT)) money = nbt.getShort("Money");
			else if (nbt.hasKey("Money", NBT.TAG_INT)) money = nbt.getInteger("Money");
			if (money != 0) {
				if (money < 100 && money > -100)
					StatCollector.translateToLocalFormatted("money2a.secondary.name", new Object[] {money});
				else if (money > 100 && money % 100 == 0)
					StatCollector.translateToLocalFormatted("money2a.primary.name", new Object[] {money / 100});
				else
					StatCollector.translateToLocalFormatted("money2a.value.name", new Object[] {money / 100, money % 100});
			} else
				StatCollector.translateToLocal("money2a.none.desc");
		}
		return super.getItemStackDisplayName(itemStack);
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean flag) {
		NBTTagCompound nbt = itemStack.getTagCompound();
		if (nbt != null) {
			int money = 0;
			if (nbt.hasKey("Money", NBT.TAG_BYTE)) money = nbt.getByte("Money");
			else if (nbt.hasKey("Money", NBT.TAG_SHORT)) money = nbt.getShort("Money");
			else if (nbt.hasKey("Money", NBT.TAG_INT)) money = nbt.getInteger("Money");
			if (money != 0) {
				if (money < 100)
					list.add(StatCollector.translateToLocalFormatted("money2a.secondary.desc", new Object[] {money}));
				else if (money >= 100 && money % 100 == 0)
					list.add(StatCollector.translateToLocalFormatted("money2a.primary.desc", new Object[] {money / 100}));
				else
					list.add(StatCollector.translateToLocalFormatted("money2a.value.desc", new Object[] {money / 100, money % 100}));
			} else
				list.add(StatCollector.translateToLocal("money2a.none.desc"));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item id, CreativeTabs table, List list) {
		//list.add(new ItemStack(id, 1, 0));
		for (int value : nominal_values)
		list.add(addNBT(new ItemStack(id, 1, 0), value));
		list.add(addNBT(new ItemStack(id, 1, 0), 0));//For test or no
		list.add(addNBT(new ItemStack(id, 1, 0), Byte.MAX_VALUE));//For test
		list.add(addNBT(new ItemStack(id, 1, 0), Short.MAX_VALUE));//For test
		list.add(addNBT(new ItemStack(id, 1, 0), Integer.MAX_VALUE));//For test
	}

	public static ItemStack addNBT(ItemStack itemStack, byte money) {
		if (!itemStack.hasTagCompound()) itemStack.setTagCompound(new NBTTagCompound());
		itemStack.getTagCompound().setByte("Money", (byte)money);
		return itemStack;
	}

	public static ItemStack addNBT(ItemStack itemStack, short money) {
		if (!itemStack.hasTagCompound()) itemStack.setTagCompound(new NBTTagCompound());
		itemStack.getTagCompound().setShort("Money", (short)money);
		return itemStack;
	}

	public static ItemStack addNBT(ItemStack itemStack, int money) {
		if (money >= Byte.MIN_VALUE && money <= Byte.MAX_VALUE)
			return addNBT(itemStack, (byte)money);
		else if (money >= Short.MIN_VALUE && money <= Short.MAX_VALUE)
			return addNBT(itemStack, (short)money);
		else {
			if (!itemStack.hasTagCompound()) itemStack.setTagCompound(new NBTTagCompound());
			itemStack.getTagCompound().setInteger("Money", money);
			return itemStack;
		}
	}

	public ArrayList<ItemStack> splitMoney(int money) {
		return splitMoney(money, true);
	}

	public ArrayList<ItemStack> splitMoney(int money, boolean isFive) {
		return splitMoney(money, 1000000, isFive);
	}

	public ArrayList<ItemStack> splitMoney(int money, byte zeros) {
		return splitMoney(money, zeros, true);
	}

	public ArrayList<ItemStack> splitMoney(int money, byte zeros, boolean isFive) {
		int multi = 1;
		for (byte i = 0; i < zeros; ++i) multi *= 10;
		return splitMoney(money, multi, isFive);
	}

	public ArrayList<ItemStack> splitMoney(int money, int zeros) {
		return splitMoney(money, zeros, true);
	}

	public ArrayList<ItemStack> splitMoney(int money, int zeros, boolean isFive) {
		if (zeros > 1 && zeros < 10) return splitMoney(money, (byte)zeros, isFive);
		final ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		int start;
		while (zeros > 0) {
			start = money / zeros;
			if (start > 0) {
				money -= start * zeros;
				ItemStack itemMoney = new ItemStack(this);

				if (isFive && start >= 5) {
					int count = start / 5;
					start -= count * 5;

					while (count > 0) {
						if (count >= itemMoney.getMaxStackSize()) {
							itemMoney.stackSize = itemMoney.getMaxStackSize();
							list.add(addNBT(itemMoney.copy(), zeros * 5));
							count -= itemMoney.getMaxStackSize();
						} else {
							itemMoney.stackSize = count;
							list.add(addNBT(itemMoney.copy(), zeros * 5));
							count = 0;
						}
					}
				}

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
			zeros /= 10;
		}
		return list;
	}

	public ArrayList<ItemStack> splitMoney_oldVersion(int money, int zeros, boolean isFive) {
		if (zeros > 1 && zeros < 10) return splitMoney(money, (byte)zeros, isFive);
		final ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		int start;
		while (zeros > 0) {
			start = money / zeros;
			if (start > 0) {
				money -= start * zeros;
				if (isFive) {
					while (start >= 5) {
						start -= 5;
						list.add(addNBT(new ItemStack(this), zeros * 5));
					}
				}
				for (int i = 0; i < start; ++i) {
					list.add(addNBT(new ItemStack(this), zeros));
				}
			}
			zeros /= 10;
		}
		return list;
	}

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
			if (nbt.hasKey("Money", NBT.TAG_BYTE)) money = nbt.getByte("Money");
			else if (nbt.hasKey("Money", NBT.TAG_SHORT)) money = nbt.getShort("Money");
			else if (nbt.hasKey("Money", NBT.TAG_INT)) money = nbt.getInteger("Money");
			if (money == 0) return icon_check;
			else if (money < 100 && money > -100) return icon_coint;
			else if (money >= 100 && money % 100 != 0) return icon_check;
		}
		return super.getIcon(itemStack, pass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) {
		super.registerIcons(ir);
		icon_coint = ir.registerIcon("money2a:coint");
		icon_check = ir.registerIcon("money2a:check");
	}

}
