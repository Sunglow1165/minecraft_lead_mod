package com.github.sunglow.lead.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * 铅框架
 *
 * @Author xueyuntong
 * @Date 2023/4/13 14:41
 */
public class LeadFrame extends Block {

    private static VoxelShape shape;
    private static final BooleanProperty WATERLOGGED = BooleanProperty.create("waterlogged");

    public LeadFrame() {
        super(BlockBehaviour.Properties.of(Material.STONE));
    }


}