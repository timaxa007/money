package timaxa007.money.v4;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;

public class MoneyCommand extends CommandBase {

	private static final String[] w = new String[]{
			"set",
			"add",
			"get"
	};

	@Override
	public String getCommandName() {
		return "money4";
	}

	@Override
	/**Return the required permission level for this command.**/
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public String getCommandUsage(ICommandSender ics) {
		return "commands.money4.usage";
	}

	@Override
	public void processCommand(ICommandSender ics, String[] args) {
		if (args.length <= 2) {
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
					ics.addChatMessage(new ChatComponentTranslation("money4.not.add", money, player.getDisplayName(), moneyPlayer.getMoney()));
			}
			else if (args[1].equalsIgnoreCase(w[2])) {

				int money = parseInt(ics, args[2]);
				boolean isFive = true;

				if (args.length > 4) isFive = parseBoolean(ics, args[4]);

				if (args.length > 3) {
					int zeros = parseInt(ics, args[3]);
					for (ItemStack item_money : ((ItemMoney)MoneyMod.item_money).splitMoney(money, zeros, isFive))
						if (!player.inventory.addItemStackToInventory(item_money))
							player.dropPlayerItemWithRandomChoice(item_money, false);
				} else {
					for (ItemStack item_money : ((ItemMoney)MoneyMod.item_money).splitMoney(money, isFive))
						if (!player.inventory.addItemStackToInventory(item_money))
							player.dropPlayerItemWithRandomChoice(item_money, false);
				}
			}

		}
	}

	/**Adds the strings available in this command to the given list of tab completion options.**/
	@Override
	public List addTabCompletionOptions(ICommandSender ics, String[] args) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) :
			args.length == 2 ? getListOfStringsMatchingLastWord(args, w) : null;
	}

	/**Return whether the specified command parameter index is a username parameter.**/
	@Override
	public boolean isUsernameIndex(String[] args, int id) {
		return id == 1;
	}

}
