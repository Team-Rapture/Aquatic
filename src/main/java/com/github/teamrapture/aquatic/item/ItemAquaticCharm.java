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
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ItemAquaticCharm extends ItemBase {

    protected final int DIMENSION_FROM_ID = 0; //TODO store in player capability?

    public ItemAquaticCharm(String name) {
        super(name);
        this.setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (worldIn instanceof WorldServer) {
            PlayerList playerList = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList();
            TeleporterAquatic teleporter = new TeleporterAquatic((WorldServer) worldIn);
            if (playerIn instanceof EntityPlayerMP) {
                EntityPlayerMP teleportee = (EntityPlayerMP) playerIn;
                if (teleportee.dimension == AquaticConfig.dimension.dimensionID) {
                    playerList.transferPlayerToDimension((EntityPlayerMP) playerIn, DIMENSION_FROM_ID, teleporter);
                    moveToEmptyArea(playerIn, worldIn);
                } else {
                    playerList.transferPlayerToDimension((EntityPlayerMP) playerIn, AquaticConfig.dimension.dimensionID, teleporter);
                    moveToEmptyArea(playerIn, worldIn);
                }
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    private void moveToEmptyArea(EntityPlayer entity, World world) {
        while (!TeleporterAquatic.isPosClear(entity, world)) {
            entity.setPosition(entity.posX, entity.posY + 1, entity.posZ); //TODO better algorithm for finding a suitable spawn position
        }
    }


}
