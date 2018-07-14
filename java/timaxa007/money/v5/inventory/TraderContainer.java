package timaxa007.money.v5.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import timaxa007.money.v5.EntityCoinTrader;
import timaxa007.money.v5.MoneyPlayer;

public class TraderContainer extends Container {

	private final EntityCoinTrader entity;

	public TraderContainer(EntityPlayer player, EntityCoinTrader entity) {
		entity.inventory.openInventory();
		this.entity = entity;

		int j;
		int k;

		for (j = 0; j < 3; ++j) {
			for (k = 0; k < 9; ++k) {
				addSlotToContainer(new Slot(entity.inventory, k + j * 9, 8 + k * 18, 18 + j * 18));
			}
		}

		for (j = 0; j < 3; ++j) {
			for (k = 0; k < 9; ++k) {
				addSlotToContainer(new Slot(player.inventory, k + j * 9 + 9, 8 + k * 18, 85 + j * 18));
			}
		}

		for (j = 0; j < 9; ++j) {
			addSlotToContainer(new Slot(player.inventory, j, 8 + j * 18, 143));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		MoneyPlayer moneyPlayer = MoneyPlayer.get(player);
		if (moneyPlayer == null) return false;
		if (moneyPlayer.entityTrader == null) return false;
		if (moneyPlayer.entityTrader.isDead) return false;
		return entity.inventory.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(slotID);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotID < 3 * 9) {
				if (!this.mergeItemStack(itemstack1, 3 * 9, this.inventorySlots.size(), true)) {
					return null;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 0, 3 * 9, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack)null);
			}
			else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		entity.inventory.closeInventory();
		MoneyPlayer moneyPlayer = MoneyPlayer.get(player);
		if (moneyPlayer == null) return;
		moneyPlayer.entityTrader = null;
	}

}
