package mayus.mymod.unkiller;

import mayus.mymod.MyMod;
import mayus.mymod.config.FastFurnaceConfig;
import mayus.mymod.config.UnkillerConfig;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;

public class GuiUnkiller extends GuiContainer {

    public static final int WIDTH = 180;
    public static final int HEIGHT = 152;

    private TileUnkiller unkiller;

    private static final ResourceLocation gui0to10 = new ResourceLocation(MyMod.MODID, "textures/gui/unkiller.png");
    private static final ResourceLocation gui10to20 = new ResourceLocation(MyMod.MODID, "textures/gui/unkiller.png");
    private static final ResourceLocation gui20to30 = new ResourceLocation(MyMod.MODID, "textures/gui/unkiller.png");
    private static final ResourceLocation gui30to40 = new ResourceLocation(MyMod.MODID, "textures/gui/unkiller.png");
    private static final ResourceLocation gui40to50 = new ResourceLocation(MyMod.MODID, "textures/gui/unkiller.png");
    private static final ResourceLocation gui50to60 = new ResourceLocation(MyMod.MODID, "textures/gui/unkiller.png");
    private static final ResourceLocation gui60to70 = new ResourceLocation(MyMod.MODID, "textures/gui/unkiller.png");
    private static final ResourceLocation gui70to80 = new ResourceLocation(MyMod.MODID, "textures/gui/unkiller.png");
    private static final ResourceLocation gui80to90 = new ResourceLocation(MyMod.MODID, "textures/gui/unkiller.png");
    private static final ResourceLocation gui90to100 = new ResourceLocation(MyMod.MODID, "textures/gui/unkiller.png");



    public GuiUnkiller(TileUnkiller tileEntity, ContainerUnkiller container) {
        super(container);

        xSize = WIDTH;
        ySize = HEIGHT;

        unkiller = tileEntity;
    }
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        switch (unkiller.getWorld().getBlockState(unkiller.getPos()).getValue(BlockUnkiller.STATE)) {
            case 0:
                mc.getTextureManager().bindTexture(gui0to10);
            case 1:
                mc.getTextureManager().bindTexture(gui10to20);
            case 2:
                mc.getTextureManager().bindTexture(gui20to30);
            case 3:
                mc.getTextureManager().bindTexture(gui30to40);
            case 4:
                mc.getTextureManager().bindTexture(gui40to50);
            case 5:
                mc.getTextureManager().bindTexture(gui50to60);
            case 6:
                mc.getTextureManager().bindTexture(gui60to70);
            case 7:
                mc.getTextureManager().bindTexture(gui70to80);
            case 8:
                mc.getTextureManager().bindTexture(gui80to90);
            case 9:
                mc.getTextureManager().bindTexture(gui90to100);
        }


        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        int energyPercent = ((unkiller.getClientEnergy() * 100) / UnkillerConfig.MAX_POWER);
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
            drawHoveringText(Collections.singletonList("Energy: " + unkiller.getClientEnergy() + " RF"), mouseX, mouseY, fontRenderer);
        }


    }

    private void drawEnergyBar(int percentage) {
        drawRect(guiLeft + 10, guiTop + 5, guiLeft + 112, guiTop + 15, 0xff555555);
        for(int i = 0; i < percentage; i++) {
            drawVerticalLine(guiLeft + 10 + 1 + i, guiTop + 5, guiTop + 14, i % 2 == 0 ? 0xffff0000 : 0xff000000);
        }
    }

}
