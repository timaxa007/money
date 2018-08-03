package timaxa007.money.v2b.event;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.player.PlayerEvent;
import timaxa007.money.v2b.MoneyPlayer;

public abstract class MoneyEvent extends PlayerEvent {

	public final Side side;
	public final MoneyPlayer moneyPlayer;

	public MoneyEvent(final EntityPlayer player) {
		super(player);
        this.side = player instanceof EntityPlayerMP ? Side.SERVER : Side.CLIENT;
		moneyPlayer = MoneyPlayer.get(player);
	}

	public abstract static class SetMoney extends MoneyEvent {

		public SetMoney(final EntityPlayer player) {
			super(player);
		}
		//----------------------------------------------------------
	    @Cancelable
		public static class Pre extends SetMoney {

			public int newMoney;

			public Pre(final EntityPlayer player, final int newMoney) {
				super(player);
				this.newMoney = newMoney;
			}

		}

		public static class Post extends SetMoney {

			public final int oldMoney;
			
			public Post(final EntityPlayer player, final int oldMoney) {
				super(player);
				this.oldMoney = oldMoney;
			}

		}
		//----------------------------------------------------------
	}

}
