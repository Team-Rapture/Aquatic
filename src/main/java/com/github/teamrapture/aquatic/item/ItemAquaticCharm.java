package com.github.teamrapture.aquatic.item;

import com.github.teamrapture.aquatic.config.AquaticConfig;
import com.github.teamrapture.aquatic.world.TeleporterAquatic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ItemAquaticCharm extends ItemBase {

    protected final int DIMENSION_FROM_ID = 0; //TODO store in player capability?

    public ItemAquaticCharm(String name) {
        super(name);
        this.setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (!worldIn.isRemote) {
            PlayerList playerList = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList();
            if (playerIn instanceof EntityPlayerMP) {
                EntityPlayerMP player = (EntityPlayerMP) playerIn;
                int targetDim = player.dimension == AquaticConfig.dimension.dimensionID ? DIMENSION_FROM_ID : AquaticConfig.dimension.dimensionID;
                TeleporterAquatic teleporter = new TeleporterAquatic();
                playerList.transferPlayerToDimension(player, targetDim, teleporter);
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }


}
