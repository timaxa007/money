package timaxa007.money.v3;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

public class MoneyCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "money3";
	}

	/**Return the required permission level for this command.**/
	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public String getCommandUsage(ICommandSender ics) {
		return "commands.money3.usage";
	}

	@Override
	public void processCommand(ICommandSender ics, String[] args) {
		if (args.length <= 0) {
			throw new WrongUsageException(getCommandUsage(ics), new Object[0]);
		} else {
			EntityPlayerMP player = args.length == 0 ? getCommandSenderAsPlayer(ics) : getPlayer(ics, args[0]);
			MoneyPlayer moneyPlayer = MoneyPlayer.get(player);
			if (moneyPlayer == null) return;

			if (args[1].equalsIgnoreCase("set")) {
				int money = parseInt(ics, args[2]);
				moneyPlayer.setMoney(money);
			}
			else if (args[1].equalsIgnoreCase("add")) {
				int money = parseInt(ics, args[2]);
				if (moneyPlayer.isAddMoney(money))
					moneyPlayer.addMoney(money);
				else
					ics.addChatMessage(new ChatComponentTranslation("money3.not.add", money, player.getDisplayName(), moneyPlayer.getMoney()));
			}

		}
	}

	/**Adds the strings available in this command to the given list of tab completion options.**/
	@Override
	public List addTabCompletionOptions(ICommandSender ics, String[] args) {
		return args.length == 2 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
	}

	/**Return whether the specified command parameter index is a username parameter.**/
	@Override
	public boolean isUsernameIndex(String[] args, int id) {
		return id == 1;
	}

}
