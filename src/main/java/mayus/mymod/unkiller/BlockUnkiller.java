package mayus.mymod.unkiller;

import mayus.mymod.MyMod;

import mayus.mymod.tools.GenericBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockUnkiller extends GenericBlock implements ITileEntityProvider {

    public static final PropertyInteger STATE = PropertyInteger.create("stage", 0, 9);

    public static final ResourceLocation UNKILLER = new ResourceLocation(MyMod.MODID, "unkiller");




    public BlockUnkiller() {
        super(Material.IRON);
        setRegistryName(UNKILLER);
        setTranslationKey(MyMod.MODID + ".unkiller");
        setHarvestLevel("pickaxe", 1);
        setDefaultState(this.getBlockState().getBaseState().withProperty(STATE, 0));
    }



    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileUnkiller();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STATE);
    }

    private int getLevel(IBlockState state) {
        return state.getValue(STATE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.withLevel(meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return this.getLevel(state);
    }
    private IBlockState withLevel(int level)
    {
        return this.getDefaultState().withProperty(STATE, level);
    }
}
