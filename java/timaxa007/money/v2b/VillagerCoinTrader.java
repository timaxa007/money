package timaxa007.money.v2b;

import java.util.Random;

import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class VillagerCoinTrader implements IVillageTradeHandler {

	@Override
	public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random) {
		if (villager.getProfession() != MoneyMod.villagerTraderCoinProfession) return;
		//recipeList.add(new MerchantRecipe(coin_gold, new ItemStack(Items.stick)));
		//recipeList.add(new MerchantRecipe(coin_silver, coin_copper, new ItemStack(Items.coal)));
		
		ItemStack input1, input2, output;
		
		input1 = input2 = ItemCoin.coin_copper.copy();
		input1.stackSize = 64;
		input2.stackSize = 36;
		output = ItemCoin.coin_silver.copy();
		
		recipeList.add(new MerchantRecipe(input1, input2, output));

		input1 = input2 = ItemCoin.coin_silver.copy();
		input1.stackSize = 64;
		input2.stackSize = 36;
		output = ItemCoin.coin_gold.copy();
		
		recipeList.add(new MerchantRecipe(input1, input2, output));
	}

}
