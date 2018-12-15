package mayus.mymod.unkiller;

import mayus.mymod.config.FastFurnaceConfig;
import mayus.mymod.config.UnkillerConfig;
import mayus.mymod.network.Messages;
import mayus.mymod.network.PacketSyncMachineState;
import mayus.mymod.tools.IMachineStateContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerUnkiller extends Container implements IMachineStateContainer {
    private TileUnkiller te;

    private static final int PROGRESS_ID = 0;

    public ContainerUnkiller(IInventory playerInventory, TileUnkiller te) {
        this.te = te;

        // This container references items out of our own inventory (the 9 slots we hold ourselves)
        // as well as the slots from the player inventory so that the user can transfer items between
        // both inventories. The two calls below make sure that slots are defined for both inventories.
        addOwnSlots();
        addPlayerSlots(playerInventory);
    }

    private void addPlayerSlots(IInventory playerInventory) {
        // Slots for the main inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = 10 + col * 18;
                int y = row * 18 + 70;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
            }
        }

        // Slots for the hotbar
        for (int row = 0; row < 9; ++row) {
            int x = 10 + row * 18;
            int y = 58 + 70;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }

    private void addOwnSlots() {
        IItemHandler itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        int x = 45;
        int y = 25;

        int slotIndex = 0;

        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));


        x = 117;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));

    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < TileUnkiller.SIZE) {
                if (!this.mergeItemStack(itemstack1, TileUnkiller.SIZE, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, TileUnkiller.SIZE, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return te.canInteractWith(playerIn);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        if (!te.getWorld().isRemote) {
            if (te.getProgress() != te.getClientProgress() || te.getEnergy() != te.getClientEnergy()) {
                te.setClientEnergy(te.getEnergy());
                te.setClientProgress(te.getProgress());

                for (IContainerListener listener : listeners) {
                    if (listener instanceof EntityPlayerMP) {
                        EntityPlayerMP player = (EntityPlayerMP) listener;
                        int pct = 100 - te.getProgress() * 100 / UnkillerConfig.MAX_PROGRESS;
                        Messages.INSTANCE.sendTo(new PacketSyncMachineState(te.getEnergy(), pct), player);
                    }
                }
            }
        }
    }

    @Override
    public void sync(int energy, int progress) {
        te.setClientEnergy(energy);
        te.setClientProgress(progress);
    }
}
