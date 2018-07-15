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

	public static class SetMoney extends MoneyEvent {

		public int money = -1;

		public SetMoney(EntityPlayer player, int money) {
			super(player);
			this.money = money;
		}
		//----------------------------------------------------------
		public static class Pre extends SetMoney {

			public Pre(EntityPlayer player, int newMoney) {
				super(player, newMoney);
			}

		}

		public static class Post extends SetMoney {

			public Post(EntityPlayer player, int oldMoney) {
				super(player, oldMoney);
			}

		}
		//----------------------------------------------------------
	}

}
