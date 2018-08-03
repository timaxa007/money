package timaxa007.money.v0.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import timaxa007.money.v0.MoneyPlayer;

public class SyncMoneyMessage implements IMessage {

	public int money;

	public SyncMoneyMessage() {}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(money);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		money = buf.readInt();
	}

	public static class Handler implements IMessageHandler<SyncMoneyMessage, IMessage> {

		@Override
		public IMessage onMessage(SyncMoneyMessage packet, MessageContext message) {
			if (message.side.isClient())
				act(packet);
			else
				act(message.getServerHandler().playerEntity, packet);
			return null;
		}

		@SideOnly(Side.CLIENT)
		private void act(SyncMoneyMessage packet) {
			Minecraft mc = Minecraft.getMinecraft();
			MoneyPlayer moneyPlayer = MoneyPlayer.get(mc.thePlayer);
			if (moneyPlayer == null) return;
			moneyPlayer.setMoney(packet.money);
		}

		private void act(EntityPlayerMP player, SyncMoneyMessage packet) {
			MoneyPlayer moneyPlayer = MoneyPlayer.get(player);
			if (moneyPlayer == null) return;
			moneyPlayer.setMoney(packet.money);
		}

	}

}
