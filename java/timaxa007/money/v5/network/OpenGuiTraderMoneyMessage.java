package timaxa007.money.v5.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import timaxa007.money.v5.EntityCoinTrader;
import timaxa007.money.v5.MoneyMod;
import timaxa007.money.v5.MoneyPlayer;
import timaxa007.money.v5.client.gui.TraderGui;
import timaxa007.money.v5.inventory.TraderContainer;

public class OpenGuiTraderMoneyMessage implements IMessage {

	public int entityID;

	public OpenGuiTraderMoneyMessage() {}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityID);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		entityID = buf.readInt();
	}

	public static class Handler implements IMessageHandler<OpenGuiTraderMoneyMessage, IMessage> {

		@Override
		public IMessage onMessage(OpenGuiTraderMoneyMessage packet, MessageContext message) {
			if (message.side.isClient())
				act(packet);
			else
				act(message.getServerHandler().playerEntity, packet);
			return null;
		}

		@SideOnly(Side.CLIENT)
		private void act(OpenGuiTraderMoneyMessage packet) {
			Minecraft mc = Minecraft.getMinecraft();
			MoneyPlayer moneyPlayer = MoneyPlayer.get(mc.thePlayer);
			if (moneyPlayer == null) return;

			Entity entity = mc.theWorld.getEntityByID(packet.entityID);
			if (entity instanceof EntityCoinTrader) {
				moneyPlayer.entityTrader = (EntityCoinTrader)entity;
				//mc.displayGuiScreen(new TraderGui(mc.thePlayer, (EntityCoinTrader)entity));
			}
		}

		private void act(EntityPlayerMP player, OpenGuiTraderMoneyMessage packet) {
			MoneyPlayer moneyPlayer = MoneyPlayer.get(player);
			if (moneyPlayer == null) return;

			Entity entity = player.worldObj.getEntityByID(packet.entityID);
			if (entity instanceof EntityCoinTrader) {
				player.openContainer = new TraderContainer(player, (EntityCoinTrader)entity);

				OpenGuiTraderMoneyMessage message = new OpenGuiTraderMoneyMessage();
				message.entityID = packet.entityID;
				MoneyMod.network.sendTo(message, player);
				//player.sendContainerToPlayer(player.openContainer);
			}
		}

	}

}
