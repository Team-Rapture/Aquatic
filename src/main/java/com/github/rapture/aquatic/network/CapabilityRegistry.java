package com.github.rapture.aquatic.network;

import com.github.rapture.aquatic.api.oxygen.IOxygenProvider;
import com.github.rapture.aquatic.api.oxygen.OxygenHandler;
import com.github.rapture.aquatic.api.ph.IPHProvider;
import com.github.rapture.aquatic.api.ph.PHHandler;
import com.github.rapture.aquatic.network.capability.OxygenStorage;
import com.github.rapture.aquatic.network.capability.PHStorage;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityRegistry {

    public static void registerCapabilities() {
        CapabilityManager.INSTANCE.register(IOxygenProvider.class, new OxygenStorage(), OxygenHandler::new);
        CapabilityManager.INSTANCE.register(IPHProvider.class, new PHStorage(), PHHandler::new);
    }
}
