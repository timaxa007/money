package timaxa007.money.v2b.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import timaxa007.money.v2b.MoneyMod;

public class OpenGuiMessage implements IMessage {

	public int windowID/*, id*/;
	public byte id;

	public OpenGuiMessage() {}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(windowID);
		//buf.writeInt(id);
		buf.writeByte(id);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		windowID = buf.readInt();
		//id = buf.readInt();
		id = buf.readByte();
	}

	public static class Handler implements IMessageHandler<OpenGuiMessage, IMessage> {

		@Override
		public IMessage onMessage(OpenGuiMessage packet, MessageContext message) {
			if (message.side.isClient())
				act(packet);
			else
				act(message.getServerHandler().playerEntity, packet);
			return null;
		}

		@SideOnly(Side.CLIENT)
		private void act(OpenGuiMessage packet) {
			Minecraft mc = Minecraft.getMinecraft();
			MoneyMod.proxy.openGui(packet.id, mc.thePlayer);
			mc.thePlayer.openContainer.windowId = packet.windowID;
		}

		private void act(EntityPlayerMP player, OpenGuiMessage packet) {
			MoneyMod.proxy.openGui(packet.id, player);
		}

	}

}
