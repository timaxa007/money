package timaxa007.money.v5;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import timaxa007.money.v5.network.SyncMoneyMessage;

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

	static int gl = 0, sl = 0, cl = 0;
	static boolean gr = false, sr = false, cr = false;

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void drawText(RenderGameOverlayEvent.Post event) {
		switch(event.type) {
		case ALL:
			Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationItemsTexture);
			if (gr) Minecraft.getMinecraft().ingameGUI.drawTexturedModelRectFromIcon(0, 2 + 36, MoneyMod.item_coin.getIcon(ItemCoin.coin_gold, 0), 16, 16);
			if (sr) Minecraft.getMinecraft().ingameGUI.drawTexturedModelRectFromIcon((gr ? gl + 14 : 0), 2 + 36, MoneyMod.item_coin.getIcon(ItemCoin.coin_silver, 0), 16, 16);
			if (cr) Minecraft.getMinecraft().ingameGUI.drawTexturedModelRectFromIcon((gr ? gl + 14 : 0) + (sr ? sl + 14 : 0), 2 + 36, MoneyMod.item_coin.getIcon(ItemCoin.coin_copper, 0), 16, 16);
			break;
		case TEXT:
			MoneyPlayer moneyPlayer = MoneyPlayer.get(Minecraft.getMinecraft().thePlayer);
			if (moneyPlayer == null) return;

			gr = moneyPlayer.getGold() > 0;
			sr = moneyPlayer.getSilver() > 0;
			cr = moneyPlayer.getCopper() > 0;

			if (gr) {
				String g = Integer.toString(moneyPlayer.getGold());
				gl = Minecraft.getMinecraft().fontRenderer.getStringWidth(g);
				Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(g, 12, 3 + 40, 0xFFFFFF);
			}
			//else gl = Minecraft.getMinecraft().fontRenderer.getStringWidth(Integer.toString(0));

			if (sr) {
				String s = Integer.toString(moneyPlayer.getSilver());
				sl = Minecraft.getMinecraft().fontRenderer.getStringWidth(s);
				Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(s, 12 + (gr ? gl + 14 : 0), 3 + 40, 0xFFFFFF);
			}
			//else sl = Minecraft.getMinecraft().fontRenderer.getStringWidth(Integer.toString(0));

			if (cr) {
				String c = Integer.toString(moneyPlayer.getCopper());
				cl = Minecraft.getMinecraft().fontRenderer.getStringWidth(c);
				Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(c, 12 + (gr ? gl + 14 : 0) + (sr ? sl + 14 : 0), 3 + 40, 0xFFFFFF);
			}
			//else cl = Minecraft.getMinecraft().fontRenderer.getStringWidth(Integer.toString(0));

			break;
		default:return;
		}
	}

}
