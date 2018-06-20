package com.github.teamrapture.aquatic.item.tool;

import com.github.teamrapture.aquatic.Aquatic;
import com.github.teamrapture.aquatic.entity.hostile.EntityShark;
import com.github.teamrapture.aquatic.init.AquaticBlocks;
import com.github.teamrapture.aquatic.init.AquaticItems;
import com.github.teamrapture.aquatic.util.NameUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class DivingKnife extends ItemSword {
	private static final List<Block> breakable = new ArrayList<>();

	static {
		breakable.add(AquaticBlocks.CORAL_REEF_BLUE);
		breakable.add(AquaticBlocks.CORAL_REEF_PINK);
		breakable.add(AquaticBlocks.CORAL_REEF_RED);
		breakable.add(AquaticBlocks.CORAL_REEF_YELLOW);
		breakable.add(AquaticBlocks.CORAL_REEF_GREEN);
		breakable.add(AquaticBlocks.PISTIA);
		breakable.add(AquaticBlocks.HYDRILLA);
	}

	public DivingKnife(String name, ToolMaterial material) {
		super(material);
		NameUtil.name(this, name);
		this.setCreativeTab(Aquatic.CREATIVE_TAB);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving) {
		if (!worldIn.isRemote) {
			stack.damageItem(1, entityLiving);
			Block block = state.getBlock();
			if (breakable.contains(block))
				return !worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(),
						new ItemStack(AquaticItems.ORGANIC_MATTER)))
						|| super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);

		}
		return true;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {

		stack = player.getHeldItemMainhand();
		if (!player.world.isRemote) {
			if (entity instanceof EntityAnimal) {
				if (player.world.rand.nextBoolean()) {

					player.world.spawnEntity(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ,
							new ItemStack(AquaticItems.RAW_SLICED_MEAT)));
				}
			}
			if (entity instanceof EntityShark) {
				if (player.world.rand.nextBoolean()) {
					player.world.spawnEntity(new EntityItem(player.world, entity.posX, entity.posY, entity.posZ,
							new ItemStack(AquaticItems.SHARK_TOOTH)));

				}
			}
		}
		return super.onLeftClickEntity(stack, player, entity);
	}
}
