package com.github.teamrapture.aquatic.util;

import com.github.teamrapture.aquatic.Aquatic;
import com.github.teamrapture.aquatic.config.AquaticConfig;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class UpdateChecker {

    public static boolean hasUpdate() {
        if (AquaticConfig.updates.enableUpdateChecker) {
            ForgeVersion.CheckResult result = getResult();
            ForgeVersion.Status status = result.status;
            if (status != ForgeVersion.Status.PENDING && status != ForgeVersion.Status.FAILED) {
                return status == ForgeVersion.Status.OUTDATED || (AquaticConfig.updates.announceBetaUpdates && status == ForgeVersion.Status.BETA_OUTDATED);
            }
            Aquatic.getLogger().warn("Error getting update status for {}, found status {}!", Aquatic.MODID, status.toString());
        }
        return false;
    }

    public static ForgeVersion.CheckResult getResult() {
        return ForgeVersion.getResult(FMLCommonHandler.instance().findContainerFor(Aquatic.MODID));
    }

    public static void notifyServer() {
        if (hasUpdate()) {
            ForgeVersion.CheckResult result = getResult();
            Aquatic.getLogger().warn("There's an update available for {}, download version {} here: {}", Aquatic.MODNAME, result.target, result.url);
        }
    }
}
