package mayus.mymod.furnace;

import mayus.mymod.MyMod;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiFastFurnace extends GuiContainer
{
    public static final int WIDTH = 180;
    public static final int HEIGHT = 152;

    private TileFastFurnace furnace;

    private static final ResourceLocation background = new ResourceLocation(MyMod.MODID, "textures/gui/fast_furnace.png");
    private static final ResourceLocation background_on = new ResourceLocation(MyMod.MODID, "textures/gui/fast_furnace_burn.png");

    public GuiFastFurnace(TileFastFurnace tileEntity, ContainerFastFurnace container) {
        super(container);

        xSize = WIDTH;
        ySize = HEIGHT;

        furnace = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        boolean running = furnace.getProgress() > 0;
        if(!running) {
            mc.getTextureManager().bindTexture(background);
        } else {
            mc.getTextureManager().bindTexture(background_on);
        }

        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);


        /*
         * Rendered Strings are going here
         */

        //Progress
        if(furnace.getProgress() > 0) {
            drawString(mc.fontRenderer, "Progress: " + (100 - ((furnace.getProgress() * 100) / 40)) + "%", guiLeft + 10, guiTop + 50, 0xffffff);
        }
    }



    //Black background and Tooltips
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);

    }
}
