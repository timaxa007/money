package timaxa007.money.v2b.client;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import timaxa007.money.v2b.ItemCoin;
import timaxa007.money.v2b.MoneyMod;
import timaxa007.money.v2b.MoneyPlayer;
import timaxa007.money.v2b.event.MoneyEvent;

public class EventsClient {

	public static byte direction = 0;
	public static int offsetX = 0, offsetY = 0, maxDelay = 100;
	public boolean
	multiAddCoins = true, firstMessege = true, isVertical = false;
	private static int
	gl = -1, sl = -1, cl = -1,
	addMoney = 0, time = -1,
	posX, posY;

	@SubscribeEvent
	public void drawText(RenderGameOverlayEvent.Post event) {
		switch(event.type) {
		case ALL:
			if (gl == -1 && sl == -1 && cl == -1) return;
			GL11.glEnable(GL11.GL_BLEND);
			Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationItemsTexture);

			if (gl != -1) {

				if (direction % 3 == 1) {
					posX = event.resolution.getScaledWidth() / 2;
				} else if (direction % 3 == 2) {
					posX = event.resolution.getScaledWidth();
					posX -= gl + 14;
					if (!isVertical) {
						if (sl != -1) posX -= sl + 13;
						if (cl != -1) posX -= cl + 13;
					}
				} else {
					posX = -3;
				}

				if (direction / 3 == 1) {
					posY = event.resolution.getScaledHeight() / 2;
					if (isVertical) posY -= 21; else posY -= 5;
				} else if (direction / 3 == 2) {
					posY = event.resolution.getScaledHeight() - 11;
					if (isVertical) {
						if (sl != -1) posY -= 10;
						if (cl != -1) posY -= 10;
					}
				} else {
					posY = 2;
				}

				posX += offsetX;
				posY += offsetY - 4;

				Minecraft.getMinecraft().ingameGUI.drawTexturedModelRectFromIcon(posX, posY,
						MoneyMod.item_coin.getIcon(ItemCoin.coin_gold, 0), 16, 16);
				/*
				Minecraft.getMinecraft().ingameGUI.drawTexturedModelRectFromIcon(
						offsetX,
						offsetY - 5, MoneyMod.item_coin.getIcon(ItemCoin.coin_gold, 0), 16, 16);
				 */
			}

			if (sl != -1) {

				if (direction % 3 == 1) {
					posX = event.resolution.getScaledWidth() / 2;
				} else if (direction % 3 == 2) {
					posX = event.resolution.getScaledWidth();
					posX -= sl + 14;
					if (!isVertical) {
						if (cl != -1) posX -= cl + 13;
					}
				} else {
					posX = -3;
					if (!isVertical) {
						if (gl != -1) posX += gl + 14;
					}
				}

				if (direction / 3 == 1) {
					posY = event.resolution.getScaledHeight() / 2;
					if (isVertical) posY -= 11; else posY -= 5;
				} else if (direction / 3 == 2) {
					posY = event.resolution.getScaledHeight() - 11;
					if (isVertical) {
						if (cl != -1) posY -= 10;
					}
				} else {
					posY = 2;
					if (isVertical) {
						if (gl != -1) posY += 10;
					}
				}

				posX += offsetX;
				posY += offsetY - 4;

				Minecraft.getMinecraft().ingameGUI.drawTexturedModelRectFromIcon(posX, posY,
						MoneyMod.item_coin.getIcon(ItemCoin.coin_silver, 0), 16, 16);
				/*
				Minecraft.getMinecraft().ingameGUI.drawTexturedModelRectFromIcon(
						offsetX + (gl != -1 ? gl + 14 : 0),
						offsetY - 5, MoneyMod.item_coin.getIcon(ItemCoin.coin_silver, 0), 16, 16);
				 */
			}

			if (cl != -1) {

				if (direction % 3 == 1) {
					posX = event.resolution.getScaledWidth() / 2;
				} else if (direction % 3 == 2) {
					posX = event.resolution.getScaledWidth();
					posX -= cl + 14;
				} else {
					posX = -3;
					if (!isVertical) {
						if (sl != -1) posX += sl + 14;
						if (gl != -1) posX += gl + 14;
					}
				}

				if (direction / 3 == 1) {
					posY = event.resolution.getScaledHeight() / 2;
					if (isVertical) posY -= 1; else posY -= 5;
				} else if (direction / 3 == 2) {
					posY = event.resolution.getScaledHeight() - 11;
				} else {
					posY = 2;
					if (isVertical) {
						if (sl != -1) posY += 10;
						if (gl != -1) posY += 10;
					}
				}

				posX += offsetX;
				posY += offsetY - 4;

				Minecraft.getMinecraft().ingameGUI.drawTexturedModelRectFromIcon(posX, posY,
						MoneyMod.item_coin.getIcon(ItemCoin.coin_copper, 0), 16, 16);
				/*
				Minecraft.getMinecraft().ingameGUI.drawTexturedModelRectFromIcon(
						offsetX + (gl != -1 ? gl + 14 : 0) + (sl != -1 ? sl + 14 : 0),
						offsetY - 5, MoneyMod.item_coin.getIcon(ItemCoin.coin_copper, 0), 16, 16);
				 */
			}
			GL11.glDisable(GL11.GL_BLEND);
			break;
		case TEXT:
			if (gl == -1 && sl == -1 && cl == -1) return;
			MoneyPlayer moneyPlayer = MoneyPlayer.get(Minecraft.getMinecraft().thePlayer);
			if (moneyPlayer == null) return;
			//------------------------------------------------------------------------
			if (time > 1) {
				float k = (float)time / (float)maxDelay;
				GL11.glPushMatrix();
				GL11.glEnable(GL11.GL_BLEND);
				if (isVertical)
					GL11.glTranslatef((direction % 3 != 0 ? -(k * 10F) : k * 10F), 0, 0);
				else
					GL11.glTranslatef(0, (direction / 3 == 2 ? -(k * 8F) : k * 8F), 0);
				int j;

				if ((j = MoneyPlayer.getGold(addMoney)) != 0) {

					if (direction % 3 == 1) {
						posX = event.resolution.getScaledWidth() / 2;
					} else if (direction % 3 == 2) {
						posX = event.resolution.getScaledWidth();
						posX -= gl;
						//fix for russian lang
						posX -= Minecraft.getMinecraft().fontRenderer.getUnicodeFlag() ? 5 : 7;
						if (!isVertical) {
							if (sl != -1) posX -= sl + 13;
							if (cl != -1) posX -= cl + 13;
						} else posX -= gl;
					} else {
						//fix for russian lang
						posX = Minecraft.getMinecraft().fontRenderer.getUnicodeFlag() ? 6 : 4;
						if (isVertical) posX += gl;
					}

					if (direction / 3 == 1) {
						posY = event.resolution.getScaledHeight() / 2;
						if (isVertical) posY -= 20; else posY -= 5;
					} else if (direction / 3 == 2) {
						posY = event.resolution.getScaledHeight() - 10;
						if (isVertical) {
							if (sl != -1) posY -= 10;
							if (cl != -1) posY -= 10;
						} else posY -= 3;
					} else {
						if (isVertical) posY = 2; else posY = 4;
					}

					posX += offsetX;
					posY += offsetY;

					if (addMoney > 0)
						Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("+" + Integer.toString(j), posX, posY,
								(((int)(k * 255F) & 0xFF) << 24 | 255 << 16 | 255 << 8 | 30));
					else
						Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(Integer.toString(j), posX, posY,
								(((int)(k * 255F) & 0xFF) << 24 | 250 << 16 | 63 << 8 | 31));
					/*
					if (addMoney > 0)
						Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("+" + Integer.toString(j),
								offsetX + 12,
								offsetY + 3, (((int)(k * 255F) & 0xFF) << 24 | 255 << 16 | 255 << 8 | 30));
					else
						Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(Integer.toString(j),
								offsetX + 12,
								offsetY + 3, (((int)(k * 255F) & 0xFF) << 24 | 250 << 16 | 63 << 8 | 31));
					 */
				}

				if ((j = MoneyPlayer.getSilver(addMoney)) != 0) {

					if (direction % 3 == 1) {
						posX = event.resolution.getScaledWidth() / 2;
					} else if (direction % 3 == 2) {
						posX = event.resolution.getScaledWidth();
						posX -= sl;
						//fix for russian lang
						posX -= Minecraft.getMinecraft().fontRenderer.getUnicodeFlag() ? 5 : 7;
						if (!isVertical) {
							if (cl != -1) posX -= cl + 13;
						} else
							posX -= sl;
					} else {
						//fix for russian lang
						posX = Minecraft.getMinecraft().fontRenderer.getUnicodeFlag() ? 6 : 4;
						if (!isVertical) {
							if (gl != -1) posX += gl + 14;
						} else posX += sl;
					}

					if (direction / 3 == 1) {
						posY = event.resolution.getScaledHeight() / 2;
						posY -= 5;
						if (isVertical) posY -= 5;
					} else if (direction / 3 == 2) {
						posY = event.resolution.getScaledHeight() - 10;
						if (isVertical) {
							if (cl != -1) posY -= 10;
						} else posY -= 3;
					} else {
						if (isVertical) {
							posY = 2;
							if (gl != -1) posY += 10;
						} else posY = 4;
					}

					posX += offsetX;
					posY += offsetY;

					if (addMoney > 0)
						Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("+" + Integer.toString(j), posX, posY,
								(((int)(k * 255F) & 0xFF) << 24 | 220 << 16 | 220 << 8 | 250));
					else
						Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(Integer.toString(j), posX, posY,
								(((int)(k * 255F) & 0xFF) << 24 | 250 << 16 | 63 << 8 | 31));
					/*
					if (addMoney > 0)
						Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("+" + Integer.toString(j),
								offsetX + 12 + (gl != -1 ? gl + 14 : 0),
								offsetY + 3, (((int)(k * 255F) & 0xFF) << 24 | 220 << 16 | 220 << 8 | 250));
					else
						Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(Integer.toString(j),
								offsetX + 12 + (gl != -1 ? gl + 14 : 0),
								offsetY + 3, (((int)(k * 255F) & 0xFF) << 24 | 250 << 16 | 63 << 8 | 31));
					 */
				}

				if ((j = MoneyPlayer.getCopper(addMoney)) != 0) {

					if (direction % 3 == 1) {
						posX = event.resolution.getScaledWidth() / 2;
					} else if (direction % 3 == 2) {
						posX = event.resolution.getScaledWidth();
						posX -= cl;
						//fix for russian lang
						posX -= Minecraft.getMinecraft().fontRenderer.getUnicodeFlag() ? 5 : 7;
						if (isVertical) posX -= cl;
					} else {
						//fix for russian lang
						posX = Minecraft.getMinecraft().fontRenderer.getUnicodeFlag() ? 6 : 4;
						if (!isVertical) {
							if (sl != -1) posX += sl + 14;
							if (gl != -1) posX += gl + 14;
						}
						if (isVertical) posX += cl;
					}

					if (direction / 3 == 1) {
						posY = event.resolution.getScaledHeight() / 2;
						posY -= 5;
						if (isVertical) posY += 5;
					} else if (direction / 3 == 2) {
						posY = event.resolution.getScaledHeight() - 10;
						if (!isVertical) posY -= 3;
					} else {
						if (isVertical) {
							posY = 2; 
							if (sl != -1) posY += 10;
							if (gl != -1) posY += 10;
						} else posY = 4;
					}

					posX += offsetX;
					posY += offsetY;

					if (addMoney > 0)
						Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("+" + Integer.toString(j), posX, posY,
								(((int)(k * 255F) & 0xFF) << 24 | 255 << 16 | 180 << 8 | 30));
					else
						Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(Integer.toString(j), posX, posY,
								(((int)(k * 255F) & 0xFF) << 24 | 250 << 16 | 63 << 8 | 31));
					/*
					if (addMoney > 0)
						Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("+" + Integer.toString(j),
								offsetX + 12 + (gl != -1 ? gl + 14 : 0) + (sl != -1 ? sl + 14 : 0),
								offsetY + 3, (((int)(k * 255F) & 0xFF) << 24 | 255 << 16 | 180 << 8 | 30));
					else
						Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(Integer.toString(j),
								offsetX + 12 + (gl != -1 ? gl + 14 : 0) + (sl != -1 ? sl + 14 : 0),
								offsetY + 3, (((int)(k * 255F) & 0xFF) << 24 | 250 << 16 | 63 << 8 | 31));
					 */
				}
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glPopMatrix();
			}
			//------------------------------------------------------------------------
			if (gl != -1) {

				if (direction % 3 == 1) {
					posX = event.resolution.getScaledWidth() / 2;
				} else if (direction % 3 == 2) {
					posX = event.resolution.getScaledWidth();
					posX -= gl + 1;
					if (!isVertical) {
						if (sl != -1) posX -= sl + 13;
						if (cl != -1) posX -= cl + 13;
					}
				} else {
					posX = 10;
				}

				if (direction / 3 == 1) {
					posY = event.resolution.getScaledHeight() / 2;
					if (isVertical) posY -= 20; else posY -= 5;
				} else if (direction / 3 == 2) {
					posY = event.resolution.getScaledHeight() - 10;
					if (isVertical) {
						if (sl != -1) posY -= 10;
						if (cl != -1) posY -= 10;
					}
				} else {
					posY = 2;
				}

				posX += offsetX;
				posY += offsetY;

				Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(Integer.toString(moneyPlayer.getGold()), posX, posY, 0xFFFFFF);
				/*
				Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(Integer.toString(moneyPlayer.getGold()),
						offsetX + 13,
						offsetY, 0xFFFFFF);
				 */
			}

			if (sl != -1) {

				if (direction % 3 == 1) {
					posX = event.resolution.getScaledWidth() / 2;
				} else if (direction % 3 == 2) {
					posX = event.resolution.getScaledWidth();
					posX -= sl + 1;
					if (!isVertical) {
						if (cl != -1) posX -= cl + 13;
					}
				} else {
					posX = 10;
					if (!isVertical) {
						if (gl != -1) posX += gl + 14;
					}
				}

				if (direction / 3 == 1) {
					posY = event.resolution.getScaledHeight() / 2;
					posY -= 5;
					if (isVertical) posY -= 5;
				} else if (direction / 3 == 2) {
					posY = event.resolution.getScaledHeight() - 10;
					if (isVertical) {
						if (cl != -1) posY -= 10;
					}
				} else {
					posY = 2; 
					if (isVertical) {
						if (gl != -1) posY += 10;
					}
				}

				posX += offsetX;
				posY += offsetY;

				Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(Integer.toString(moneyPlayer.getSilver()), posX, posY, 0xFFFFFF);
				/*
				Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(Integer.toString(moneyPlayer.getSilver()),
						offsetX + 13 + (gl != -1 ? gl + 14 : 0),
						offsetY, 0xFFFFFF);
				 */
			}
			if (cl != -1) {

				if (direction % 3 == 1) {
					posX = event.resolution.getScaledWidth() / 2;
				} else if (direction % 3 == 2) {
					posX = event.resolution.getScaledWidth();
					posX -= cl + 1;
				} else {
					posX = 10;
					if (!isVertical) {
						if (sl != -1) posX += sl + 14;
						if (gl != -1) posX += gl + 14;
					}
				}

				if (direction / 3 == 1) {
					posY = event.resolution.getScaledHeight() / 2;
					posY -= 5;
					if (isVertical) posY += 5;
				} else if (direction / 3 == 2) {
					posY = event.resolution.getScaledHeight() - 10;
				} else {
					posY = 2;
					if (isVertical) {
						if (sl != -1) posY += 10;
						if (gl != -1) posY += 10;
					}
				}

				posX += offsetX;
				posY += offsetY;

				Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(Integer.toString(moneyPlayer.getCopper()), posX, posY, 0xFFFFFF);
				/*
				Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(Integer.toString(moneyPlayer.getCopper()),
						offsetX + 13 + (gl != -1 ? gl + 14 : 0) + (sl != -1 ? sl + 14 : 0),
						offsetY, 0xFFFFFF);
				 */
			}

			break;
		default:return;
		}
	}

	@SubscribeEvent
	public void firstMessegeOut(ClientDisconnectionFromServerEvent event) {
		//reset 'first' sync setMoney.
		firstMessege = true;
	}

	@SubscribeEvent
	public void tickClient(TickEvent.ClientTickEvent event) {
		if (event.phase != Phase.END) return;
		if (time > 0) --time;
		else if (time < 0) return;
		else if (time == 0) {
			addMoney = 0;
			--time;
			if (Minecraft.getMinecraft().thePlayer == null) return;
			MoneyPlayer moneyPlayer = MoneyPlayer.get(Minecraft.getMinecraft().thePlayer);
			if (moneyPlayer == null) return;
			int j;
			if ((j = moneyPlayer.getGold()) == 0) gl = -1; else gl = Minecraft.getMinecraft().fontRenderer.getStringWidth(Integer.toString(j));
			if ((j = moneyPlayer.getSilver()) == 0) sl = -1; else sl = Minecraft.getMinecraft().fontRenderer.getStringWidth(Integer.toString(j));
			if ((j = moneyPlayer.getCopper()) == 0) cl = -1; else cl = Minecraft.getMinecraft().fontRenderer.getStringWidth(Integer.toString(j));
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
		//----------------------------------------------------------------------------------------------------------
		if (!event.side.isClient()) return;
		if (event.isCanceled()) return;
		if (event.moneyPlayer == null) return;
		//----------------------------------------------------------------------------------------------------------
		int j;
		//----------------------------------------------------------------------------------------------------------
		if (firstMessege) {//#fix for 'first' sync setMoney.
			firstMessege = false;
			if ((j = event.moneyPlayer.getGold()) == 0) gl = -1; else gl = Minecraft.getMinecraft().fontRenderer.getStringWidth(Integer.toString(j));
			if ((j = event.moneyPlayer.getSilver()) == 0) sl = -1; else sl = Minecraft.getMinecraft().fontRenderer.getStringWidth(Integer.toString(j));
			if ((j = event.moneyPlayer.getCopper()) == 0) cl = -1; else cl = Minecraft.getMinecraft().fontRenderer.getStringWidth(Integer.toString(j));
			return;
		}
		//----------------------------------------------------------------------------------------------------------
		final int i = event.moneyPlayer.getMoney() - event.oldMoney;
		//----------------------------------------------------------------------------------------------------------
		if (multiAddCoins && time > (maxDelay / 2) && ((i > 0 && addMoney > 0) || (i <= 0 && addMoney <= 0)))
			addMoney += i; else addMoney = i;
		time = maxDelay;
		//----------------------------------------------------------------------------------------------------------
		if ((j = MoneyPlayer.getGold(addMoney)) != 0) {
			if (addMoney > 0)
				j = Math.max(event.moneyPlayer.getGold(), j);
			else
				j = Math.min(event.moneyPlayer.getGold(), j);
		} else j = event.moneyPlayer.getGold();
		if (j == 0) gl = -1; else gl = Minecraft.getMinecraft().fontRenderer.getStringWidth(Integer.toString(Math.abs(j)));
		//----------------------------------------------------------------------------------------------------------
		if ((j = MoneyPlayer.getSilver(addMoney)) != 0) {
			if (addMoney > 0)
				j = Math.max(event.moneyPlayer.getSilver(), j);
			else
				j = Math.min(event.moneyPlayer.getSilver(), j);
		} else j = event.moneyPlayer.getSilver();
		if (j == 0) sl = -1; else sl = Minecraft.getMinecraft().fontRenderer.getStringWidth(Integer.toString(Math.abs(j)));
		//----------------------------------------------------------------------------------------------------------
		if ((j = MoneyPlayer.getCopper(addMoney)) != 0) {
			if (addMoney > 0)
				j = Math.max(event.moneyPlayer.getCopper(), j);
			else
				j = Math.min(event.moneyPlayer.getCopper(), j);
		} else j = event.moneyPlayer.getCopper();
		if (j == 0) cl = -1; else cl = Minecraft.getMinecraft().fontRenderer.getStringWidth(Integer.toString(Math.abs(j)));
		//----------------------------------------------------------------------------------------------------------
		if (i == 0) return;
		String mess = "";

		if ((j = MoneyPlayer.getGold(i)) != 0) {
			mess += StatCollector.translateToLocalFormatted("money2b.gold.short.name", new Object[]{Math.abs(j)});
		}
		if ((j = MoneyPlayer.getSilver(i)) != 0) {
			if (mess.length() > 0) mess += ", ";
			mess += StatCollector.translateToLocalFormatted("money2b.silver.short.name", new Object[]{Math.abs(j)});
		}
		if ((j = MoneyPlayer.getCopper(i)) != 0) {
			if (mess.length() > 0) mess += ", ";
			mess += StatCollector.translateToLocalFormatted("money2b.copper.short.name", new Object[]{Math.abs(j)});
		}

		if (i > 0) event.entityPlayer.addChatMessage(new ChatComponentTranslation("money2b.add.coins", mess));
		else event.entityPlayer.addChatMessage(new ChatComponentTranslation("money2b.remove.coins", mess));
	}

}
