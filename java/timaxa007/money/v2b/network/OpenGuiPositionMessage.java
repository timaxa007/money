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

public class OpenGuiPositionMessage implements IMessage {

	public int windowID,/* id,*/ x, y, z;
	public byte id;

	public OpenGuiPositionMessage() {}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(windowID);
		//buf.writeInt(id);
		buf.writeByte(id);
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		windowID = buf.readInt();
		//id = buf.readInt();
		id = buf.readByte();
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
	}

	public static class Handler implements IMessageHandler<OpenGuiPositionMessage, IMessage> {

		@Override
		public IMessage onMessage(OpenGuiPositionMessage packet, MessageContext message) {
			if (message.side.isClient())
				act(packet);
			else
				act(message.getServerHandler().playerEntity, packet);
			return null;
		}

		@SideOnly(Side.CLIENT)
		private void act(OpenGuiPositionMessage packet) {
			Minecraft mc = Minecraft.getMinecraft();
			MoneyMod.proxy.openGui(packet.id, mc.thePlayer, mc.theWorld, packet.x, packet.y, packet.z);
			mc.thePlayer.openContainer.windowId = packet.windowID;
		}

		private void act(EntityPlayerMP player, OpenGuiPositionMessage packet) {
			MoneyMod.proxy.openGui(packet.id, player, player.worldObj, packet.x, packet.y, packet.z);
		}

	}

}
