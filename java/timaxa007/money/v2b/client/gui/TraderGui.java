package timaxa007.money.v2b.client.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import timaxa007.money.v2b.entity.EntityCoinTrader;
import timaxa007.money.v2b.inventory.TraderContainer;

@SideOnly(Side.CLIENT)
public class TraderGui extends GuiContainer {

	private static final ResourceLocation texture = new ResourceLocation("textures/gui/container/generic_54.png");//vanilla texture
	private final InventoryPlayer player_inventory;
	private final IInventory add_inventory;

	public TraderGui(EntityPlayer player, EntityCoinTrader entity) {
		super(new TraderContainer(player, entity));
		this.player_inventory = player.inventory;
		this.add_inventory = entity.inventory;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRendererObj.drawString(add_inventory.hasCustomInventoryName() ? add_inventory.getInventoryName() : I18n.format(add_inventory.getInventoryName(), new Object[0]), 8, 6, 4210752);
		fontRendererObj.drawString(player_inventory.hasCustomInventoryName() ? player_inventory.getInventoryName() : I18n.format(player_inventory.getInventoryName(), new Object[0]), 8, ySize - 96 + 4, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float parTick, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, 3 * 18 + 17);
		drawTexturedModalRect(guiLeft, guiTop + 3 * 18 + 17, 0, 126, xSize, 96);
	}

}
