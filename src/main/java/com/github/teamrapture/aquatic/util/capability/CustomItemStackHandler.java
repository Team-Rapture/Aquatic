package com.github.teamrapture.aquatic.util.capability;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

//TODO remove??
public class CustomItemStackHandler extends ItemStackHandler {

    private boolean tempIgnoreConditions;

    public CustomItemStackHandler(int slots) {
        super(slots);
    }

    public static ItemStack setStackSize(ItemStack stack, int size, boolean containerOnEmpty) {
        if (size <= 0) {
            if (isValid(stack) && containerOnEmpty) {
                return stack.getItem().getContainerItem(stack);
            } else {
                return ItemStack.EMPTY;
            }
        }
        stack.setCount(size);
        return stack;
    }

    public static ItemStack addStackSize(ItemStack stack, int size) {
        return addStackSize(stack, size, false);
    }

    public static ItemStack addStackSize(ItemStack stack, int size, boolean containerOnEmpty) {
        return setStackSize(stack, getStackSize(stack) + size, containerOnEmpty);
    }

    public static boolean isValid(ItemStack stack) {
        return stack != null && !stack.isEmpty();
    }

    public static int getStackSize(ItemStack stack) {
        if (!isValid(stack)) {
            return 0;
        } else {
            return stack.getCount();
        }
    }

    public void decrStackSize(int slot, int amount) {
        this.setStackInSlot(slot, addStackSize(this.getStackInSlot(slot), -amount));
    }

    public NonNullList<ItemStack> getItems() {
        return this.stacks;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (!this.tempIgnoreConditions && !this.canInsert(stack, slot)) {
            return stack;
        }

        return super.insertItem(slot, stack, simulate);
    }

    public ItemStack insertItemInternal(int slot, ItemStack stack, boolean simulate) {
        this.tempIgnoreConditions = true;
        ItemStack result = this.insertItem(slot, stack, simulate);
        this.tempIgnoreConditions = false;
        return result;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (!this.tempIgnoreConditions && !this.canExtract(this.getStackInSlot(slot), slot)) {
            return ItemStack.EMPTY;
        }

        return super.extractItem(slot, amount, simulate);
    }

    public ItemStack extractItemInternal(int slot, int amount, boolean simulate) {
        this.tempIgnoreConditions = true;
        ItemStack result = this.extractItem(slot, amount, simulate);
        this.tempIgnoreConditions = false;
        return result;
    }

    public boolean canInsert(ItemStack stack, int slot) {
        return true;
    }

    public boolean canExtract(ItemStack stack, int slot) {
        return true;
    }
}