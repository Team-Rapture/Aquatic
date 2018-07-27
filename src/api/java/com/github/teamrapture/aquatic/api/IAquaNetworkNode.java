package com.github.teamrapture.aquatic.api;

import net.minecraftforge.common.capabilities.ICapabilityProvider;

/**
 * used as globbal identifier for aqua network nodes
 */
public interface IAquaNetworkNode extends ICapabilityProvider {

    /**
     * called after making changes to the node, most of the time via capabilities
     */
    default void setDirty() {}
}
