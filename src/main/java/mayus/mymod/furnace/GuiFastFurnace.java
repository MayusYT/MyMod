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
    private static final ResourceLocation background_on_0_20 = new ResourceLocation(MyMod.MODID, "textures/gui/fast_furnace_burn_00-20.png");
    private static final ResourceLocation background_on_20_40 = new ResourceLocation(MyMod.MODID, "textures/gui/fast_furnace_burn_20-40.png");
    private static final ResourceLocation background_on_40_60 = new ResourceLocation(MyMod.MODID, "textures/gui/fast_furnace_burn_40-60.png");
    private static final ResourceLocation background_on_60_80 = new ResourceLocation(MyMod.MODID, "textures/gui/fast_furnace_burn_60-80.png");
    private static final ResourceLocation background_on_80_100 = new ResourceLocation(MyMod.MODID, "textures/gui/fast_furnace_burn_80-100.png");

    public GuiFastFurnace(TileFastFurnace tileEntity, ContainerFastFurnace container) {
        super(container);

        xSize = WIDTH;
        ySize = HEIGHT;

        furnace = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        int progress = furnace.getProgress();
        int proz = (100 - ((furnace.getProgress() * 100) / 40));
        if(progress == 0) {
            mc.getTextureManager().bindTexture(background);
        } else {
            if(proz <= 20) {
                mc.getTextureManager().bindTexture(background_on_0_20);
            } else if(proz <= 40) {
                mc.getTextureManager().bindTexture(background_on_20_40);
            } else if(proz <= 60) {
                mc.getTextureManager().bindTexture(background_on_40_60);
            } else if(proz <= 80) {
                mc.getTextureManager().bindTexture(background_on_60_80);
            } else if(proz <= 100) {
                mc.getTextureManager().bindTexture(background_on_80_100);
            }

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
