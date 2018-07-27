package com.github.teamrapture.aquatic.api.capability.ph;

import net.minecraftforge.energy.IEnergyStorage;

/**
 * TODO javadoc
 * @see {@link IEnergyStorage} for how to use
 */
public interface IPhProvider {

    //FIXME add simulate and real return values, (see EnergyStorage)!

    //add oxygen to the storage
    int receivePh(int maxReceive, boolean simulate);

    //drain stored oxygen
    int extractPh(int maxExtract, boolean simulate);

    boolean canReceivePh();

    boolean canExtractPh();

    int getPhStored();

    int getMaxPhStorage();
}
