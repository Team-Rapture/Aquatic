package com.github.rapture.aquatic.client.gui.container;

import com.github.rapture.aquatic.client.gui.slot.SlotInput;
import com.github.rapture.aquatic.client.gui.slot.SlotOutput;
import com.github.rapture.aquatic.tileentity.TileEnergyFiller;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerEnergyFiller extends Container {

    public TileEnergyFiller te;

    public ContainerEnergyFiller(IInventory playerInv, TileEnergyFiller te) {
        this.te = te;

        //input energy handler
        addSlotToContainer(new SlotInput(this.te.inventory, 0, 81, 60));

        //output filled
        addSlotToContainer(new SlotOutput(this.te.inventory, 1, 125, 24));

        int i;
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        return ItemStack.EMPTY;
    }
}
