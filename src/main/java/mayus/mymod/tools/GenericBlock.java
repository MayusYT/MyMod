package mayus.mymod.tools;

import mayus.mymod.MyMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class GenericBlock extends Block {


    public GenericBlock(Material materialIn) {
        super(materialIn);
        setCreativeTab(MyMod.creativeTab);

    }

    private static final Pattern COMPILE = Pattern.compile("@", Pattern.LITERAL);

    protected void addInformationLocalized(List<String> tooltip, String key, Object... parameters) {
        String translated = I18n.format(key, parameters);
        translated = COMPILE.matcher(translated).replaceAll("\u00a7");
        Collections.addAll(tooltip, StringUtils.split(translated, "\n"));
    }

    @Override
    public void getDrops(NonNullList<ItemStack> result, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        TileEntity tileEntity = world.getTileEntity(pos);

        if(tileEntity instanceof IRestorableTileEntity) {
            ItemStack stack = new ItemStack(Item.getItemFromBlock(this));
            NBTTagCompound tagCompound = new NBTTagCompound();
            ((IRestorableTileEntity)tileEntity).writeRestorableToNBT(tagCompound);

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

        if(tileEntity instanceof IRestorableTileEntity) {


            NBTTagCompound tagCompound = stack.getTagCompound();
            if(tagCompound != null) {
                ((IRestorableTileEntity) tileEntity).readRestorableFromNBT(tagCompound);
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
        if (!(te instanceof IGuiTile)) {
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
}
