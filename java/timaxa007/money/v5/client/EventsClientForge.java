package timaxa007.money.v5.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import timaxa007.money.v5.ItemCoin;
import timaxa007.money.v5.MoneyMod;
import timaxa007.money.v5.MoneyPlayer;
import timaxa007.money.v5.event.MoneyEvent;

public class EventsClientForge {

	public static byte direction = 0;
	public static int offsetX = 0, offsetY = 0;
	static int gl = -1, sl = -1, cl = -1;
	/*
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void drawText(RenderGameOverlayEvent.Post event) {
		switch(event.type) {
		case ALL:
			if (gl == -1 && sl == -1 && cl == -1) return;
			Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationItemsTexture);
			if (gl != -1) Minecraft.getMinecraft().ingameGUI.drawTexturedModelRectFromIcon(0, 2 + 36, MoneyMod.item_coin.getIcon(ItemCoin.coin_gold, 0), 16, 16);
			if (sl != -1) Minecraft.getMinecraft().ingameGUI.drawTexturedModelRectFromIcon((gl != -1 ? gl + 14 : 0), 2 + 36, MoneyMod.item_coin.getIcon(ItemCoin.coin_silver, 0), 16, 16);
			if (cl != -1) Minecraft.getMinecraft().ingameGUI.drawTexturedModelRectFromIcon((gl != -1 ? gl + 14 : 0) + (sl != -1 ? sl + 14 : 0), 2 + 36, MoneyMod.item_coin.getIcon(ItemCoin.coin_copper, 0), 16, 16);
			break;
		case TEXT:
			MoneyPlayer moneyPlayer = MoneyPlayer.get(Minecraft.getMinecraft().thePlayer);
			if (moneyPlayer == null) {
				if (gl != -1) gl = -1;
				if (sl != -1) sl = -1;
				if (cl != -1) cl = -1;
				return;
			}

			if (moneyPlayer.getGold() == 0) gl = -1; else if (gl == -1) gl = 0;
			if (moneyPlayer.getSilver() == 0) sl = -1; else if (sl == -1) sl = 0;
			if (moneyPlayer.getCopper() == 0) cl = -1; else if (cl == -1) cl = 0;

			if (gl == -1 && sl == -1 && cl == -1) return;

			if (gl != -1) {
				String g = Integer.toString(moneyPlayer.getGold());
				gl = Minecraft.getMinecraft().fontRenderer.getStringWidth(g);
				Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(g, 12, 3 + 40, 0xFFFFFF);
			}

			if (sl != -1) {
				String s = Integer.toString(moneyPlayer.getSilver());
				sl = Minecraft.getMinecraft().fontRenderer.getStringWidth(s);
				Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(s, 12 + (gl != -1 ? gl + 14 : 0), 3 + 40, 0xFFFFFF);
			}

			if (cl != -1) {
				String c = Integer.toString(moneyPlayer.getCopper());
				cl = Minecraft.getMinecraft().fontRenderer.getStringWidth(c);
				Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(c, 12 + (gl != -1 ? gl + 14 : 0) + (sl != -1 ? sl + 14 : 0), 3 + 40, 0xFFFFFF);
			}

			break;
		default:return;
		}
	}
	 */
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void drawText(RenderGameOverlayEvent.Post event) {
		switch(event.type) {
		case ALL:
			if (gl == -1 && sl == -1 && cl == -1) return;
			Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationItemsTexture);
			if (gl != -1) Minecraft.getMinecraft().ingameGUI.drawTexturedModelRectFromIcon(0, 2 + 36, MoneyMod.item_coin.getIcon(ItemCoin.coin_gold, 0), 16, 16);
			if (sl != -1) Minecraft.getMinecraft().ingameGUI.drawTexturedModelRectFromIcon((gl != -1 ? gl + 14 : 0), 2 + 36, MoneyMod.item_coin.getIcon(ItemCoin.coin_silver, 0), 16, 16);
			if (cl != -1) Minecraft.getMinecraft().ingameGUI.drawTexturedModelRectFromIcon((gl != -1 ? gl + 14 : 0) + (sl != -1 ? sl + 14 : 0), 2 + 36, MoneyMod.item_coin.getIcon(ItemCoin.coin_copper, 0), 16, 16);
			break;
		case TEXT:
			MoneyPlayer moneyPlayer = MoneyPlayer.get(Minecraft.getMinecraft().thePlayer);
			if (moneyPlayer == null) return;
			if (gl == -1 && sl == -1 && cl == -1) return;

			if (gl != -1) Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(Integer.toString(moneyPlayer.getGold()), 12, 3 + 40, 0xFFFFFF);
			if (sl != -1) Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(Integer.toString(moneyPlayer.getSilver()), 12 + (gl != -1 ? gl + 14 : 0), 3 + 40, 0xFFFFFF);
			if (cl != -1) Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(Integer.toString(moneyPlayer.getCopper()), 12 + (gl != -1 ? gl + 14 : 0) + (sl != -1 ? sl + 14 : 0), 3 + 40, 0xFFFFFF);

			break;
		default:return;
		}
	}

	/**
	Зачем каждый тик или что хуже, каждый кадр проверять 'вычеслять размеры текста', которые за игру может пару раз изменяються?</br>
	Незачем, только лишная трата ресурсов. По этому это нужно 'вычеслять размеры текста', когда идёт изменение количества монет.</br>
	</br>
	What for every tick or what is worse, each frame to check 'to compute the sizes of the text', which for a game can change a couple of times?</br>
	There is no need, only waste of resources. For this, it is necessary to 'to compute the sizes of the text' when the number of coins changes.</br>
	 **/
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void reSize(MoneyEvent.ChargeMoney.Post event) {

		if (event.moneyPlayer == null) return;

		if (event.moneyPlayer.getGold() == 0) gl = -1; else if (gl == -1) gl = 0;
		if (event.moneyPlayer.getSilver() == 0) sl = -1; else if (sl == -1) sl = 0;
		if (event.moneyPlayer.getCopper() == 0) cl = -1; else if (cl == -1) cl = 0;

		if (gl != -1) gl = Minecraft.getMinecraft().fontRenderer.getStringWidth(Integer.toString(event.moneyPlayer.getGold()));
		if (sl != -1) sl = Minecraft.getMinecraft().fontRenderer.getStringWidth(Integer.toString(event.moneyPlayer.getSilver()));
		if (cl != -1) cl = Minecraft.getMinecraft().fontRenderer.getStringWidth(Integer.toString(event.moneyPlayer.getCopper()));

	}

}
