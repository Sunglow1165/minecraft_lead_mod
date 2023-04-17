package com.github.sunglow.lead.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

/**
 * 铅块
 *
 * @Author xueyuntong
 * @Date 2023/4/12 18:28
 */
public class LeadBlock extends Block {

    public LeadBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL)
                .strength(0.2F, 1.5F)
                .sound(SoundType.METAL)
                .requiresCorrectToolForDrops());
    }
}