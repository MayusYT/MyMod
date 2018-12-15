package mayus.mymod.unkiller;

import mayus.mymod.config.FastFurnaceConfig;
import mayus.mymod.tools.IGuiTile;
import mayus.mymod.tools.IRestorableTileEntity;
import mayus.mymod.tools.MyEnergyStorage;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
public class TileUnkiller extends TileEntity implements ITickable, IRestorableTileEntity, IGuiTile {
    //number from 0 to 100
    public int progress = 0;

    public static final int INPUT_SLOTS = 1;
    public static final int OUTPUT_SLOTS = 1;
    public static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;


    private int clientProgress = -1;
    private int clientEnergy = -1;




    @Override
    public void update() {
        if (getClientEnergy() < FastFurnaceConfig.RF_PER_TICK) {
            //set state to '0' when block has no power
            world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockUnkiller.STATE, 0));
            return;
        }


        //translate ClientProgress (range: 0 ~ 100) to BlockUnkiller.STATE (range: 0 ~ 10)
        if(getClientProgress() < 10) {
            world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockUnkiller.STATE, 0));
        }else if(getClientProgress() >= 10) {
            world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockUnkiller.STATE, 1));
        } else if(getClientProgress() >= 20) {
            world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockUnkiller.STATE, 2));
        } else if(getClientProgress() >= 30) {
            world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockUnkiller.STATE, 3));
        } else if(getClientProgress() >= 40) {
            world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockUnkiller.STATE, 4));
        } else if(getClientProgress() >= 50) {
            world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockUnkiller.STATE, 5));
        } else if(getClientProgress() >= 60) {
            world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockUnkiller.STATE, 6));
        } else if(getClientProgress() >= 70) {
            world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockUnkiller.STATE, 7));
        } else if(getClientProgress() >= 80) {
            world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockUnkiller.STATE, 8));
        } else if(getClientProgress() >= 90) {
            world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockUnkiller.STATE, 9));
        }
    }


    public  int getProgress() {
        return progress;
    }


    public  void setProgress(Integer progress) {
        this.progress = progress;
    }

    public int getClientProgress() {
        return this.clientProgress;
    }

    public void setClientProgress(int clientProgress) {
        this.clientProgress = clientProgress;
    }

    public int getClientEnergy() {
        return clientEnergy;
    }

    public void setClientEnergy(int clientEnergy) {
        this.clientEnergy = clientEnergy;
    }

    public int getEnergy() {
        return myEnergyStorage.getEnergyStored();
    }



    private void startSmelt() {
        for (int i = 0 ; i < INPUT_SLOTS ; ++i) {
            ItemStack result = FurnaceRecipes.instance().getSmeltingResult(inputHandler.getStackInSlot(i));
            if(!result.isEmpty()) {
                if(insertOutput(result.copy(), true)) {
                    progress = FastFurnaceConfig.MAX_PROGRESS;
                    markDirty();
                }
                break;
            }
        }
    }

    private void attemptSmelt() {
        for (int i = 0 ; i < INPUT_SLOTS ; ++i) {
            ItemStack result = FurnaceRecipes.instance().getSmeltingResult(inputHandler.getStackInSlot(i));
            if(!result.isEmpty()) {
                if(insertOutput(result.copy(), false)) {
                    inputHandler.extractItem(i, 1, false);
                    break;
                }
            }
        }
    }

    private boolean insertOutput(ItemStack output, boolean simulate) {
        for(int i = 0; i < OUTPUT_SLOTS; i++) {
            ItemStack remaining = outputHandler.insertItem(i, output, simulate);
            if(remaining.isEmpty()) {
                return true;
            }

        }
        return false;
    }

    /**
     * Handler for the Input Slots
     */
    private ItemStackHandler inputHandler = new ItemStackHandler(INPUT_SLOTS) {

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            ItemStack result = FurnaceRecipes.instance().getSmeltingResult(stack);
            return !result.isEmpty();
        }

        @Override
        protected void onContentsChanged(int slot) {
            TileUnkiller.this.markDirty();
        }
    };


    /**
     * Handler for the Output Slots
     */
    private ItemStackHandler outputHandler = new ItemStackHandler(OUTPUT_SLOTS) {
        @Override
        protected void onContentsChanged(int slot) {
            TileUnkiller.this.markDirty();
        }
    };

    private CombinedInvWrapper combinedHandler = new CombinedInvWrapper(inputHandler, outputHandler);

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        readRestorableFromNBT(compound);

    }

    @Override
    public void readRestorableFromNBT(NBTTagCompound compound) {
        if(compound.hasKey("itemsIn")) {
            inputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsIn"));
        }
        if(compound.hasKey("itemsOut")) {
            outputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsOut"));
        }

        progress = compound.getInteger("progress");
        myEnergyStorage.setEnergy(compound.getInteger("energy"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        writeRestorableToNBT(compound);
        return compound;
    }


    @Override
    public void writeRestorableToNBT(NBTTagCompound compound) {
        compound.setTag("itemsIn", inputHandler.serializeNBT());
        compound.setTag("itemsOut", outputHandler.serializeNBT());
        compound.setInteger("progress", progress);
        compound.setInteger("energy", myEnergyStorage.getEnergyStored());
    }


    /**
     * If we are too far away from this tile entity you cannot use it
     */
    public boolean canInteractWith(EntityPlayer playerIn) {
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }



    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        if(capability == CapabilityEnergy.ENERGY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }


    /**
     * Sets the hopper/pipe capability (allows access to the inventory slots by automation pipes/ hoppers
     */

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if(facing == null) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(combinedHandler);

                //UP is automation Input
            } else if(facing == EnumFacing.UP) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inputHandler);

                //DOWN is automation Output
            } else if(facing == EnumFacing.DOWN) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(outputHandler);
            }

        }
        if(capability == CapabilityEnergy.ENERGY) {
            return CapabilityEnergy.ENERGY.cast(myEnergyStorage);
        }
        return super.getCapability(capability, facing);
    }

    private MyEnergyStorage myEnergyStorage = new MyEnergyStorage(FastFurnaceConfig.MAX_POWER, FastFurnaceConfig.RF_PER_TICK_INPUT);

    @Override
    public Container createContainer(EntityPlayer player) {
        return new ContainerUnkiller(player.inventory, this);
    }

    @Override
    public GuiContainer createGui(EntityPlayer player) {
        return new GuiUnkiller(this, new ContainerUnkiller(player.inventory, this));
    }


}
