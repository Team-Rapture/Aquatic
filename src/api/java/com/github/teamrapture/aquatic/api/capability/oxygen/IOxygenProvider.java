package com.github.teamrapture.aquatic.api.capability.oxygen;

import net.minecraftforge.energy.IEnergyStorage;

/**
 * TODO javadoc
 * @see {@link IEnergyStorage} for how to use
 */
public interface IOxygenProvider {

    //FIXME add simulate and real return values, (see EnergyStorage)!

    //add oxygen to the storage
    int receiveOxygen(int maxReceive, boolean simulate);

    //drain stored oxygen
    int extractOxygen(int maxExtract, boolean simulate);

    boolean canReceiveOxygen();

    boolean canExtractOxygen();

    int getOxygenStored();

    int getMaxOxygenStorage();
}
