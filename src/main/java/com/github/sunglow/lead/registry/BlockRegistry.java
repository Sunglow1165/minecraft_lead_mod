package com.github.sunglow.lead.registry;

import com.github.sunglow.lead.block.LeadBlock;
import com.github.sunglow.lead.common.Utils;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * 方块注册表
 */
public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Utils.MOD_ID);
    public static final RegistryObject<Block> leadBlock = BLOCKS.register("lead_block",LeadBlock::new);
}
