package timaxa007.money.v2b.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import timaxa007.money.v2b.MoneyMod;
import timaxa007.money.v2b.MoneyPlayer;
import timaxa007.money.v2b.client.gui.TraderGui;
import timaxa007.money.v2b.entity.EntityCoinTrader;
import timaxa007.money.v2b.inventory.TraderContainer;

public class OpenGuiEntityMessage implements IMessage {

	public int windowID,/* id,*/ entityID;
	public byte id;

	public OpenGuiEntityMessage() {}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(windowID);
		//buf.writeInt(id);
		buf.writeByte(id);
		buf.writeInt(entityID);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		windowID = buf.readInt();
		//id = buf.readInt();
		id = buf.readByte();
		entityID = buf.readInt();
	}

	public static class Handler implements IMessageHandler<OpenGuiEntityMessage, IMessage> {

		@Override
		public IMessage onMessage(OpenGuiEntityMessage packet, MessageContext message) {
			if (message.side.isClient())
				act(packet);
			else
				act(message.getServerHandler().playerEntity, packet);
			return null;
		}

		@SideOnly(Side.CLIENT)
		private void act(OpenGuiEntityMessage packet) {
			Minecraft mc = Minecraft.getMinecraft();
			MoneyMod.proxy.openGui(packet.id, mc.thePlayer, mc.theWorld.getEntityByID(packet.entityID));
			mc.thePlayer.openContainer.windowId = packet.windowID;
		}

		private void act(EntityPlayerMP player, OpenGuiEntityMessage packet) {
			MoneyMod.proxy.openGui(packet.id, player, player.worldObj.getEntityByID(packet.entityID));
		}

	}

}
