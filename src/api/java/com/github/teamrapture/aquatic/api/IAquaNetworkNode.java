package com.github.teamrapture.aquatic.api;

import net.minecraftforge.common.capabilities.ICapabilityProvider;

public interface IAquaNetworkNode extends ICapabilityProvider {

    void setDirty();
}
