package timaxa007.money.v5;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;

public class MoneyCommand extends CommandBase {

	private static final String[] w = new String[]{
			"set",
			"add",
			"get",
			"spawn"
	};

	private static final String[] wnv = new String[]{
			"copper",
			"silver",
			"gold",
			"bag"
	};

	private static final String[] e = new String[]{
			"entityTrader",
			"villagerTrader"
	};

	@Override
	public String getCommandName() {
		return "money5";
	}

	/**Return the required permission level for this command.**/
	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public String getCommandUsage(ICommandSender ics) {
		return "commands.money5.usage";
	}

	@Override
	public void processCommand(ICommandSender ics, String[] args) {
		if (args.length <= 0) {
			throw new WrongUsageException(getCommandUsage(ics), new Object[0]);
		} else {
			EntityPlayerMP player = args.length == 0 ? getCommandSenderAsPlayer(ics) : getPlayer(ics, args[0]);
			MoneyPlayer moneyPlayer = MoneyPlayer.get(player);
			if (moneyPlayer == null) return;

			if (args[1].equalsIgnoreCase(w[0])) {
				int money = parseInt(ics, args[2]);
				moneyPlayer.setMoney(money);
			}
			else if (args[1].equalsIgnoreCase(w[1])) {
				int money = parseInt(ics, args[2]);
				if (moneyPlayer.isAddMoney(money))
					moneyPlayer.addMoney(money);
				else
					ics.addChatMessage(new ChatComponentTranslation("money5.not.add", money, player.getDisplayName(), moneyPlayer.getMoney()));
			}
			else if (args[1].equalsIgnoreCase(w[2])) {

				int money = parseInt(ics, args[2]);

				if (args.length > 3) {

					if (args[3].equalsIgnoreCase(wnv[3])) {
						ItemStack item_money = ItemCoin.addNBT(new ItemStack(MoneyMod.item_coin), money);
						if (!player.inventory.addItemStackToInventory(item_money))
							player.dropPlayerItemWithRandomChoice(item_money, false);
						return;
					}

					int zeros = -1;

					for (int i = 0; i < ItemCoin.name_values.length; ++i) {
						if (args[3].equalsIgnoreCase(ItemCoin.name_values[i])) {
							zeros = ItemCoin.nominal_values[i];
							break;
						}
					}

					if (zeros == -1) zeros = parseInt(ics, args[3]);

					for (ItemStack item_money : ((ItemCoin)MoneyMod.item_coin).splitMoney(money, zeros))
						if (!player.inventory.addItemStackToInventory(item_money))
							player.dropPlayerItemWithRandomChoice(item_money, false);
				} else {
					for (ItemStack item_money : ((ItemCoin)MoneyMod.item_coin).splitMoney(money))
						if (!player.inventory.addItemStackToInventory(item_money))
							player.dropPlayerItemWithRandomChoice(item_money, false);
				}
			}
			else if (args[1].equalsIgnoreCase(w[3])) {
				if (args.length < 3) return;
				if (args[2].equalsIgnoreCase(e[0])) {
					EntityCoinTrader trader = new EntityCoinTrader(player.worldObj);
					trader.setPosition(player.posX, player.posY, player.posZ);
					player.worldObj.spawnEntityInWorld(trader);
				}
				if (args[2].equalsIgnoreCase(e[1])) {
					EntityVillager v = new EntityVillager(player.worldObj, 5);
					v.setPosition(player.posX, player.posY, player.posZ);
					player.worldObj.spawnEntityInWorld(v);
				}
			}
		}
	}

	/**Adds the strings available in this command to the given list of tab completion options.**/
	@Override
	public List addTabCompletionOptions(ICommandSender ics, String[] args) {
		switch(args.length) {
		case 1:return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
		case 2:return getListOfStringsMatchingLastWord(args, w);
		case 3:
			if (args[1].equalsIgnoreCase(w[3]))
				return getListOfStringsMatchingLastWord(args, e);
		case 4:
			if (args[1].equalsIgnoreCase(w[2]))
				return getListOfStringsMatchingLastWord(args, wnv);
			else return null;
		default:return null;
		}
	}

	/**Return whether the specified command parameter index is a username parameter.**/
	@Override
	public boolean isUsernameIndex(String[] args, int id) {
		return id == 1;
	}

}
