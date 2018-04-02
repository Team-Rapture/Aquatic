package com.github.rapture.aquatic.util;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.config.AquaticConfig;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class UpdateChecker {

    public static boolean hasUpdate() {
        ForgeVersion.CheckResult result = getResult();
        ForgeVersion.Status status = result.status;
        if (status == ForgeVersion.Status.PENDING || status == ForgeVersion.Status.FAILED) {
            Aquatic.getLogger().warn("Error getting update status for {}, found status {}!", Aquatic.MODID, status.toString());
            return false;
        } else
            return status == ForgeVersion.Status.OUTDATED || (AquaticConfig.announceBetaUpdates && status == ForgeVersion.Status.BETA_OUTDATED);
    }

    public static ForgeVersion.CheckResult getResult() {
        return ForgeVersion.getResult(FMLCommonHandler.instance().findContainerFor(Aquatic.MODID));
    }

    //TODO call in serverstarting
    public static void notifyServer() {
        if (hasUpdate()) {
            ForgeVersion.CheckResult result = getResult();
            Aquatic.getLogger().warn("There's an update available for {}, download version {} here: {}", Aquatic.MODNAME, result.target, result.url);
        }
    }
}
