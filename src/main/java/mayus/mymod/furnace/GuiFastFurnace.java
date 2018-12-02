package mayus.mymod.furnace;

import mayus.mymod.MyMod;
import mayus.mymod.config.GeneralConfig;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;


import java.util.Collections;


public class GuiFastFurnace extends GuiContainer
{
    public static final int WIDTH = 180;
    public static final int HEIGHT = 152;

    private TileFastFurnace furnace;

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
        int proz = (100 - ((furnace.getClientProgress() * 100) / 40));

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


        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        int energyPercent = ((furnace.getClientEnergy() * 100) / GeneralConfig.MAX_POWER);
        drawEnergyBar(energyPercent);
        /*
        if (furnace.getClientProgress() > 0) {
            int percentage = 100 - ((furnace.getClientProgress() * 100) / 40);
            drawString(mc.fontRenderer, "Progress: " + percentage + "%", guiLeft + 10, guiTop + 50, 0xffffff);
        }*/
    }



    //Black background and Tooltips
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);

        if (mouseX > guiLeft + 10 && mouseX < guiLeft + 112 && mouseY > guiTop + 5 && mouseY < guiTop + 15) {
            drawHoveringText(Collections.singletonList("Energy: " + furnace.getClientEnergy() + " RF"), mouseX, mouseY, fontRenderer);
        }


    }

    private void drawEnergyBar(int percentage) {
        drawRect(guiLeft + 10, guiTop + 5, guiLeft + 112, guiTop + 15, 0xff555555);
        for(int i = 0; i < percentage; i++) {
            drawVerticalLine(guiLeft + 10 + 1 + i, guiTop + 5, guiTop + 14, i % 2 == 0 ? 0xffff0000 : 0xff000000);
        }
    }

    public static boolean getRandomBoolean() {
        return Math.random() < 0.5;
     }
}
