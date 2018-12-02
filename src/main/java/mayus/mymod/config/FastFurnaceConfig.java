package mayus.mymod.config;


import mayus.mymod.MyMod;
import net.minecraftforge.common.config.Config;

@Config(modid = MyMod.MODID)
public class FastFurnaceConfig {

    @Config.Comment(value = "Maximum of power the Fast Furnace can hold")
    public static int MAX_POWER = 100000;


    @Config.Comment(value = "How much power per tick the Fast Furnace consumes")
    public static int RF_PER_TICK = 40;

    @Config.Comment(value = "Maximum of power input")
    public static int RF_PER_TICK_INPUT = 10000;

    @Config.Comment(value = "Duration in ticks of one smelting operation")
    public static int MAX_PROGRESS = 40;
}
