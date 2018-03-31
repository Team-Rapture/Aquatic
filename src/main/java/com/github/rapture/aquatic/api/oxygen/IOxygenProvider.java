package com.github.rapture.aquatic.api.oxygen;

public interface IOxygenProvider {

    //Set the oxygen amount
    void setOxygen(int amount);
    void setStorageAmount(int amount);

    //Fill and drain oxygen stored
    void fillOxygen(int amount);
    void drainOxygen(int amount);

    //Checks is oxygen can be received/extracted based on the amount input
    boolean canReceiveOxygen(int amount);
    boolean canSendOxygen(int amount);

    //Grab oxygen variables
    int getOxygenStored();
    int getMaxOxygenStorage();
}
