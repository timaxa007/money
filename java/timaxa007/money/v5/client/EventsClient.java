package timaxa007.money.v5.client;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import timaxa007.money.v5.ItemCoin;
import timaxa007.money.v5.MoneyMod;
import timaxa007.money.v5.MoneyPlayer;
import timaxa007.money.v5.event.MoneyEvent;

public class EventsClient {

	public static byte direction = 0;
	public static int offsetX = 0, offsetY = 0;
	private static int
	gl = -1, sl = -1, cl = -1,
	addMoney = 0, time = 0;

	@SubscribeEvent
	public void tickClient(TickEvent.ClientTickEvent event) {
		if (time > 0) --time;
	}

	@SubscribeEvent
	public void drawText(RenderGameOverlayEvent.Post event) {
		switch(event.type) {
		case ALL:
			if (gl == -1 && sl == -1 && cl == -1) return;
			Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationItemsTexture);
			if (gl != -1) Minecraft.getMinecraft().ingameGUI.drawTexturedModelRectFromIcon(
					0,
					offsetY - 5, MoneyMod.item_coin.getIcon(ItemCoin.coin_gold, 0), 16, 16);
			if (sl != -1) Minecraft.getMinecraft().ingameGUI.drawTexturedModelRectFromIcon(
					(gl != -1 ? gl + 14 : 0),
					offsetY - 5, MoneyMod.item_coin.getIcon(ItemCoin.coin_silver, 0), 16, 16);
			if (cl != -1) Minecraft.getMinecraft().ingameGUI.drawTexturedModelRectFromIcon(
					(gl != -1 ? gl + 14 : 0) + (sl != -1 ? sl + 14 : 0),
					offsetY - 5, MoneyMod.item_coin.getIcon(ItemCoin.coin_copper, 0), 16, 16);
			break;
		case TEXT:
			if (gl == -1 && sl == -1 && cl == -1) return;
			MoneyPlayer moneyPlayer = MoneyPlayer.get(Minecraft.getMinecraft().thePlayer);
			if (moneyPlayer == null) return;

			if (gl != -1) Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(Integer.toString(moneyPlayer.getGold()),
					13,
					offsetY, 0xFFFFFF);
			if (sl != -1) Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(Integer.toString(moneyPlayer.getSilver()),
					13 + (gl != -1 ? gl + 14 : 0),
					offsetY, 0xFFFFFF);
			if (cl != -1) Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(Integer.toString(moneyPlayer.getCopper()),
					13 + (gl != -1 ? gl + 14 : 0) + (sl != -1 ? sl + 14 : 0),
					offsetY, 0xFFFFFF);

			if (time > 1) {
				GL11.glEnable(GL11.GL_BLEND);
				int j;
				float k = (float)time / 120F;
				if ((j = addMoney / 10000) != 0) {
					if (addMoney > 0)
						Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("+" + Integer.toString(j),
								12,
								offsetY + 3 + (int)(k * 9), (((int)(k * 255F) & 0xFF) << 24 | 255 << 16 | 255 << 8 | 30));
					else
						Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(Integer.toString(j),
								12,
								offsetY + 3 + (int)(k * 9), (((int)(k * 255F) & 0xFF) << 24 | 255 << 16 | 120 << 8 | 120));
				}
				if ((j = ((addMoney / 100) % 100)) != 0) {
					if (addMoney > 0)
						Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("+" + Integer.toString(j),
								12 + (gl != -1 ? gl + 14 : 0),
								offsetY + 3 + (int)(k * 9), (((int)(k * 255F) & 0xFF) << 24 | 220 << 16 | 220 << 8 | 250));
					else
						Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(Integer.toString(j),
								12 + (gl != -1 ? gl + 14 : 0),
								offsetY + 3 + (int)(k * 9), (((int)(k * 255F) & 0xFF) << 24 | 225 << 16 | 120 << 8 | 120));
				}
				if ((j = addMoney % 100) != 0) {
					if (addMoney > 0)
						Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("+" + Integer.toString(j),
								12 + (gl != -1 ? gl + 14 : 0) + (sl != -1 ? sl + 14 : 0),
								offsetY + 3 + (int)(k * 9), (((int)(k * 255F) & 0xFF) << 24 | 255 << 16 | 180 << 8 | 30));
					else
						Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(Integer.toString(j),
								12 + (gl != -1 ? gl + 14 : 0) + (sl != -1 ? sl + 14 : 0),
								offsetY + 3 + (int)(k * 9), (((int)(k * 255F) & 0xFF) << 24 | 255 << 16 | 120 << 8 | 120));
				}
				GL11.glDisable(GL11.GL_BLEND);
			}

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
	@SubscribeEvent(priority=EventPriority.LOWEST)
	public void reSize(MoneyEvent.SetMoney.Post event) {
		if (event.moneyPlayer == null) return;
		//System.out.println("gl - " + gl + ", sl - " + sl + ", cl - " + cl + ".");
		if (event.moneyPlayer.getGold() == 0) gl = -1; else gl = 0;
		if (event.moneyPlayer.getSilver() == 0) sl = -1; else sl = 0;
		if (event.moneyPlayer.getCopper() == 0) cl = -1; else cl = 0;

		if (gl != -1) gl = Minecraft.getMinecraft().fontRenderer.getStringWidth(Integer.toString(event.moneyPlayer.getGold()));
		if (sl != -1) sl = Minecraft.getMinecraft().fontRenderer.getStringWidth(Integer.toString(event.moneyPlayer.getSilver()));
		if (cl != -1) cl = Minecraft.getMinecraft().fontRenderer.getStringWidth(Integer.toString(event.moneyPlayer.getCopper()));
		//System.out.println("g - " + event.moneyPlayer.getGold() + ", s - " + event.moneyPlayer.getSilver() + ", c - " + event.moneyPlayer.getCopper() + ".");
	}

	@SubscribeEvent(priority=EventPriority.LOWEST)
	public void message(MoneyEvent.SetMoney.Post event) {
		if (event.isCanceled()) return;
		if (event.entityPlayer instanceof EntityPlayerMP) return;
		addMoney = event.moneyPlayer.getMoney() - event.money;
		System.out.println(addMoney);
		time = 120;
		if (addMoney == 0) return;
		int i = Math.abs(addMoney);
		String mess = "";
		int j;

		if ((j = i / 10000) >= 1) {
			mess += StatCollector.translateToLocalFormatted("money5.gold.short.name", new Object[]{j});
		}
		if ((j = ((i / 100) % 100)) >= 1) {
			if (mess.length() > 0) mess += ", ";
			mess += StatCollector.translateToLocalFormatted("money5.silver.short.name", new Object[]{j});
		}
		if ((j = i % 100) >= 1) {
			if (mess.length() > 0) mess += ", ";
			mess += StatCollector.translateToLocalFormatted("money5.copper.short.name", new Object[]{j});
		}

		if (addMoney > 0) event.entityPlayer.addChatMessage(new ChatComponentTranslation("money5.add.coins", mess));
		else event.entityPlayer.addChatMessage(new ChatComponentTranslation("money5.remove.coins", mess));
	}

}
