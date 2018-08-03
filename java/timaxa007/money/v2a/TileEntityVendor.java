package timaxa007.money.v2a;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileEntityVendor extends TileEntity {

	//private final IInventory inventory = new InventoryBasic("InventoryVendor", false, 2);
	private final ArrayList<Bid> list = new ArrayList<Bid>();

	public static class Bid {

		public final ItemStack[] items;
		public final int money;

		public Bid(final ItemStack[] items, final int money) {
			this.items = items;
			this.money = money;
		}

		public boolean isBuy() {
			return !forSale();
		}

		public boolean forSale() {
			return money < 0;
		}

	}

}
