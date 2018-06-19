package com.github.teamrapture.aquatic.client.gui.slot;

import com.github.rapture.aquatic.util.capability.CustomItemStackHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SlotInput extends SlotItemHandler {

    private final CustomItemStackHandler handler;

    public SlotInput(CustomItemStackHandler handler, int index, int xPosition, int yPosition) {
        super(handler, index, xPosition, yPosition);
        this.handler = handler;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if (CustomItemStackHandler.isValid(stack)) {
            ItemStack currentStack = this.handler.getStackInSlot(this.getSlotIndex());
            this.handler.setStackInSlot(this.getSlotIndex(), ItemStack.EMPTY);
            ItemStack remainder = this.handler.insertItemInternal(this.getSlotIndex(), stack, true);
            this.handler.setStackInSlot(this.getSlotIndex(), currentStack);
            return remainder == null || remainder.getCount() < stack.getCount();
        }
        return false;
    }

    @Override
    @Nonnull
    public ItemStack getStack() {
        return this.handler.getStackInSlot(this.getSlotIndex());
    }

    @Override
    public void putStack(ItemStack stack) {
        this.handler.setStackInSlot(this.getSlotIndex(), stack);
        this.onSlotChanged();
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        ItemStack maxAdd = stack.copy();
        maxAdd.setCount(stack.getMaxStackSize());
        ItemStack currentStack = this.handler.getStackInSlot(this.getSlotIndex());
        this.handler.setStackInSlot(this.getSlotIndex(), ItemStack.EMPTY);
        ItemStack remainder = this.handler.insertItemInternal(this.getSlotIndex(), maxAdd, true);
        this.handler.setStackInSlot(this.getSlotIndex(), currentStack);
        return stack.getMaxStackSize() - remainder.getCount();
    }

    @Override
    public boolean canTakeStack(EntityPlayer playerIn) {
        return !this.handler.extractItemInternal(this.getSlotIndex(), 1, true).isEmpty();
    }

    @Override
    public ItemStack decrStackSize(int amount) {
        return this.handler.extractItemInternal(this.getSlotIndex(), amount, false);
    }
}