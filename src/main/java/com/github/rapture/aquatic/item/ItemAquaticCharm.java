package com.github.rapture.aquatic.item;

import com.github.rapture.aquatic.config.AquaticConfig;
import com.github.rapture.aquatic.world.dimension.TeleporterDim;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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
            TeleporterDim teleporter = new TeleporterDim((WorldServer) worldIn);
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
        while (!isClear(entity, world)) {
            entity.setPosition(entity.posX, entity.posY + 1, entity.posZ); //TODO better algorithm for finding a suitable spawn position
        }
    }

    private boolean isClear(EntityPlayer ent, World world) {
        AxisAlignedBB box = ent.getEntityBoundingBox();
        int minX = MathHelper.floor(box.minX);
        int maxX = MathHelper.floor(box.maxX + 1.0D);
        int minY = MathHelper.floor(box.minY);
        int maxY = MathHelper.floor(box.maxY + 1.0D);
        int minZ = MathHelper.floor(box.minZ);
        int maxZ = MathHelper.floor(box.maxZ + 1.0D);

        if (minX < 0.0D)
            --minX;
        if (minY < 0.0D)
            --minY;
        if (minZ < 0.0D)
            --minZ;

        for (int x = minX; x < maxX; ++x)
            for (int z = minZ; z < maxZ; ++z) {
                for (int y = minY; y < maxY; ++y) {
                    IBlockState block = world.getBlockState(new BlockPos(x, y, z));

                    final Material mat = block.getMaterial();
                    if (mat.isSolid() || mat.getMobilityFlag() == EnumPushReaction.BLOCK)
                        return false;
                }
            }
        return true;
    }
}
