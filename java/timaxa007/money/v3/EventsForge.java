package timaxa007.money.v3;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import timaxa007.money.v3.network.SyncMoneyMessage;

public class EventsForge {

	@SubscribeEvent
	public void addEntityConstructing(EntityEvent.EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)event.entity;
			if (MoneyPlayer.get(player) == null) MoneyPlayer.reg(player);
		}
	}

	@SubscribeEvent
	public void cloneMoneyPlayer(PlayerEvent.Clone event) {
		MoneyPlayer originalMoneyPlayer = MoneyPlayer.get((EntityPlayer)event.original);
		if (originalMoneyPlayer == null) return;
		MoneyPlayer newMoneyPlayer = MoneyPlayer.get((EntityPlayer)event.entityPlayer);
		if (newMoneyPlayer == null) return;
		newMoneyPlayer.setMoney(originalMoneyPlayer.getMoney());
	}

	@SubscribeEvent
	public void syncMoneyPlayer(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP)event.entity;
			MoneyPlayer moneyPlayer = MoneyPlayer.get(player);
			if (moneyPlayer == null) return;
			SyncMoneyMessage message = new SyncMoneyMessage();
			message.money = moneyPlayer.getMoney();
			MoneyMod.network.sendTo(message, player);
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void drawText(RenderGameOverlayEvent.Post event) {
		switch(event.type) {
		case TEXT:
			MoneyPlayer moneyPlayer = MoneyPlayer.get(Minecraft.getMinecraft().thePlayer);
			if (moneyPlayer == null) return;
			Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(
					StatCollector.translateToLocalFormatted("money3.value.short.name", new Object[] {moneyPlayer.getGold(), moneyPlayer.getSilver(), moneyPlayer.getCopper()}),
					3, 2 + 20, 0xFFFFFF);
			break;
		default:return;
		}
	}

}
