package mayus.mymod;

import mayus.mymod.furnace.BlockFastFurnace;
import mayus.mymod.furnace.TileFastFurnace;
import mayus.mymod.generator.BlockGenerator;
import mayus.mymod.generator.TileGenerator;
import mayus.mymod.santaHat.BlockSantaHat;
import mayus.mymod.unkiller.BlockUnkiller;
import mayus.mymod.unkiller.TileUnkiller;
import mayus.mymod.worldgen.BlockFancyOre;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {


    @GameRegistry.ObjectHolder("mymod:fast_furnace")
    public static BlockFastFurnace blockFastFurnace;

    @GameRegistry.ObjectHolder("mymod:generator")
    public static BlockGenerator blockGenerator;

    @GameRegistry.ObjectHolder("mymod:fancy_ore")
    public static BlockFancyOre blockFancyOre;

    @GameRegistry.ObjectHolder("mymod:santahat")
    public static BlockSantaHat blockSantaHat;

    @GameRegistry.ObjectHolder("mymod:unkiller")
    public static BlockUnkiller blockUnkiller;


    @SideOnly(Side.CLIENT)
    public static void initModels() {
        blockFastFurnace.initModel();
        blockGenerator.initModel();
        blockFancyOre.initModel();
        blockSantaHat.initModel();
        blockUnkiller.initModel();
    }

    public static void register(IForgeRegistry<Block> registry) {
        registry.register(new BlockFastFurnace());
        GameRegistry.registerTileEntity(TileFastFurnace.class, MyMod.MODID + "_fast_furnace");

        registry.register(new BlockGenerator());
        GameRegistry.registerTileEntity(TileGenerator.class, MyMod.MODID + "_generator");

        registry.register(new BlockUnkiller());
        GameRegistry.registerTileEntity(TileUnkiller.class, MyMod.MODID + "_unkiller");

        registry.register(new BlockFancyOre());
        registry.register(new BlockSantaHat());

    }


}
