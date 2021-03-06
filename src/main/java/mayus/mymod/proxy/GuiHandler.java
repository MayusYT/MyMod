package mayus.mymod.proxy;

import mayus.mymod.furnace.ContainerFastFurnace;
import mayus.mymod.furnace.GuiFastFurnace;
import mayus.mymod.furnace.TileFastFurnace;
import mayus.mymod.tools.IGuiTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof IGuiTile) {
            return ((IGuiTile)te).createContainer(player);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof IGuiTile) {

            return ((IGuiTile) te).createGui(player);
        }
        return null;
    }
}
