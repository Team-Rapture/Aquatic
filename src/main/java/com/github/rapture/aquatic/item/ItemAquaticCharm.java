package com.github.rapture.aquatic.item;

import com.github.rapture.aquatic.world.dimension.TeleporterDim;
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

    protected final int DIMENSION_FROM_ID;
    protected final int DIMENSION_TO_ID;

    public ItemAquaticCharm(String name, int dimFromID, int dimToID) {
        super(name);
        this.DIMENSION_FROM_ID = dimFromID;
        this.DIMENSION_TO_ID = dimToID;
        this.setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (worldIn instanceof WorldServer) {
            PlayerList playerList = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList();
            TeleporterDim teleporter = new TeleporterDim((WorldServer) worldIn);
            if (playerIn instanceof EntityPlayerMP) {
                EntityPlayerMP teleportee = (EntityPlayerMP) playerIn;
                if (teleportee.dimension == DIMENSION_TO_ID)
                    playerList.transferPlayerToDimension((EntityPlayerMP) playerIn, DIMENSION_FROM_ID, teleporter);
                else playerList.transferPlayerToDimension((EntityPlayerMP) playerIn, DIMENSION_TO_ID, teleporter);
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
}
