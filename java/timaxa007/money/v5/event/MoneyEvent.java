package timaxa007.money.v5.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import timaxa007.money.v5.MoneyPlayer;

public class MoneyEvent extends PlayerEvent {

	public final MoneyPlayer moneyPlayer;

	public MoneyEvent(EntityPlayer player) {
		super(player);
		moneyPlayer = MoneyPlayer.get(player);
	}

	public static class ChargeMoney extends MoneyEvent {

		public int newMoney = -1;

		public ChargeMoney(EntityPlayer player, int newMoney) {
			super(player);
			this.newMoney = newMoney;
		}
		//----------------------------------------------------------
		public static class Pre extends ChargeMoney {

			public Pre(EntityPlayer player, int newMoney) {
				super(player, newMoney);
			}

		}

		public static class Post extends ChargeMoney {

			public Post(EntityPlayer player, int money) {
				super(player, money);
			}

		}
		//----------------------------------------------------------
	}

}
