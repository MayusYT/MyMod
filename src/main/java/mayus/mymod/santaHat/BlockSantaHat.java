package mayus.mymod.santaHat;

import mayus.mymod.MyMod;
import mayus.mymod.tools.GenericBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;


import javax.annotation.Nullable;
import java.util.List;

public class BlockSantaHat extends GenericBlock {
    //public static final PropertyDirection FACING_HORIZ = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);


    public static final ResourceLocation SANTAHAT = new ResourceLocation(MyMod.MODID, "santahat");

    public BlockSantaHat() {
        super(Material.IRON);
        setRegistryName(SANTAHAT);
        setTranslationKey(MyMod.MODID + ".santahat");

        //setDefaultState(blockState.getBaseState().withProperty(FACING_HORIZ, EnumFacing.NORTH));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flags) {
        NBTTagCompound tagCompound = stack.getTagCompound();
        if (tagCompound != null) {
            addInformationLocalized(tooltip, "message.mymod.santahat");
        }
    }

    @Override
    public void initModel() {
        super.initModel();
    }
}
