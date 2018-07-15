package timaxa007.money;

public class EventsClientFML {

	//public static int lastMoney = -1;
	/*
	@SubscribeEvent
	public void tickPlayer(TickEvent.PlayerTickEvent event) {
		if (!event.side.isClient()) return;
		if (Minecraft.getMinecraft().thePlayer == null) return;

		MoneyPlayer moneyPlayer = MoneyPlayer.get(Minecraft.getMinecraft().thePlayer);
		if (moneyPlayer == null) {
			if (lastMoney != -1) lastMoney = -1;
			return;
		}

		if (lastMoney != moneyPlayer.getMoney()) {
			lastMoney = moneyPlayer.getMoney();

			if (moneyPlayer.getGold() == 0) EventsForge.gl = -1; else if (EventsForge.gl == -1) EventsForge.gl = 0;
			if (moneyPlayer.getSilver() == 0) EventsForge.sl = -1; else if (EventsForge.sl == -1) EventsForge.sl = 0;
			if (moneyPlayer.getCopper() == 0) EventsForge.cl = -1; else if (EventsForge.cl == -1) EventsForge.cl = 0;

			//На хера каждый тик или что хуже, каждый кадр проверять вычеслять размеры текста, которые за игру пару раз изменяються.
			//По этому это нужно проверять когда идёт изменение

			if (EventsForge.gl != -1)
				EventsForge.gl = Minecraft.getMinecraft().fontRenderer.getStringWidth(Integer.toString(moneyPlayer.getGold()));

			if (EventsForge.sl != -1)
				EventsForge.sl = Minecraft.getMinecraft().fontRenderer.getStringWidth(Integer.toString(moneyPlayer.getSilver()));

			if (EventsForge.cl != -1)
				EventsForge.cl = Minecraft.getMinecraft().fontRenderer.getStringWidth(Integer.toString(moneyPlayer.getCopper()));
		}

	}
	 */
	/*
	@SubscribeEvent
	public void tickPlayer(TickEvent.ClientTickEvent event) {
		MoneyPlayer moneyPlayer = MoneyPlayer.get(Minecraft.getMinecraft().thePlayer);
		if (moneyPlayer == null) {
			if (EventsForge.gl != -1) EventsForge.gl = -1;
			if (EventsForge.sl != -1) EventsForge.sl = -1;
			if (EventsForge.cl != -1) EventsForge.cl = -1;
			return;
		}

		if (moneyPlayer.getGold() == 0) EventsForge.gl = -1; else if (EventsForge.gl == -1) EventsForge.gl = 0;
		if (moneyPlayer.getSilver() == 0) EventsForge.sl = -1; else if (EventsForge.sl == -1) EventsForge.sl = 0;
		if (moneyPlayer.getCopper() == 0) EventsForge.cl = -1; else if (EventsForge.cl == -1) EventsForge.cl = 0;

		if (EventsForge.gl != -1)
			EventsForge.gl = Minecraft.getMinecraft().fontRenderer.getStringWidth(Integer.toString(moneyPlayer.getGold()));

		if (EventsForge.sl != -1)
			EventsForge.sl = Minecraft.getMinecraft().fontRenderer.getStringWidth(Integer.toString(moneyPlayer.getSilver()));

		if (EventsForge.cl != -1)
			EventsForge.cl = Minecraft.getMinecraft().fontRenderer.getStringWidth(Integer.toString(moneyPlayer.getCopper()));

	}
	 */
}
