package mayus.mymod;

import mayus.mymod.furnace.BlockFastFurnace;
import mayus.mymod.generator.BlockGenerator;
import mayus.mymod.santaHat.BlockSantaHat;
import mayus.mymod.unkiller.BlockUnkiller;
import mayus.mymod.worldgen.BlockFancyOre;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {





    @SideOnly(Side.CLIENT)
    public static void initModels() {
    }

    public static void register(IForgeRegistry<Item> registry) {
        registry.register(new ItemBlock(ModBlocks.blockFastFurnace).setRegistryName(BlockFastFurnace.FAST_FURNACE));
        registry.register(new ItemBlock(ModBlocks.blockGenerator).setRegistryName(BlockGenerator.GENERATOR));
        registry.register(new ItemBlock(ModBlocks.blockSantaHat).setRegistryName(BlockSantaHat.SANTAHAT));
        registry.register(new ItemBlock(ModBlocks.blockUnkiller).setRegistryName(BlockUnkiller.UNKILLER));
        registry.register(
                new ItemBlock(ModBlocks.blockFancyOre) {
                    @Override
                    public int getMetadata(int damage) {
                        return damage;
                    }
                }
                        .setHasSubtypes(true)
                        .setRegistryName(BlockFancyOre.FANCY_ORE));

    }

}
