package mayus.mymod.furnace;

import mayus.mymod.MyMod;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockFastFurnace extends Block implements ITileEntityProvider {

    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    public static final PropertyEnum<FurnaceState> STATE = PropertyEnum.<FurnaceState>create("state", FurnaceState.class);


    public static final ResourceLocation FAST_FURNACE = new ResourceLocation(MyMod.MODID, "fast_furnace");

    public BlockFastFurnace() {
        //Material set for sounds when break and walk over it
        super(Material.IRON);

        //mymod:furnace
        setRegistryName(FAST_FURNACE);
        setUnlocalizedName(MyMod.MODID + ".fast_furnace");
        setHarvestLevel("pickaxe", 1);
        setCreativeTab(MyMod.creativeTab);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileFastFurnace();
    }


    @Override
    public void getDrops(NonNullList<ItemStack> result, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        TileEntity tileEntity = world.getTileEntity(pos);

        if(tileEntity instanceof TileFastFurnace) {
            ItemStack stack = new ItemStack(Item.getItemFromBlock(this));
            NBTTagCompound tagCompound = new NBTTagCompound();
            ((TileFastFurnace)tileEntity).writeRestorableToNBT(tagCompound);

            stack.setTagCompound(tagCompound);
            result.add(stack);
        } else {
            super.getDrops(result, world, pos, state, fortune);
        }

    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        if(willHarvest) {
            return true;
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        worldIn.setBlockToAir(pos);
    }


    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

        TileEntity tileEntity = worldIn.getTileEntity(pos);

        if(tileEntity instanceof TileFastFurnace) {


            NBTTagCompound tagCompound = stack.getTagCompound();
            if(tagCompound != null) {
                ((TileFastFurnace) tileEntity).readRestorableFromNBT(tagCompound);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        // Only execute on the server
        if (world.isRemote) {
            return true;
        }
        TileEntity te = world.getTileEntity(pos);
        if (!(te instanceof TileFastFurnace)) {
            return false;
        }
        if(!player.isSneaking()) {
            player.openGui(MyMod.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
        } else {
            return false;
        }

        return true;
    }

    //Render Item
    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity te = world instanceof ChunkCache ? ((ChunkCache)world).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : world.getTileEntity(pos);
        if (te instanceof TileFastFurnace) {
            return state.withProperty(STATE, ((TileFastFurnace) te).getState());
        }
        return super.getActualState(state, world, pos);
    }



    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, STATE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }
}
