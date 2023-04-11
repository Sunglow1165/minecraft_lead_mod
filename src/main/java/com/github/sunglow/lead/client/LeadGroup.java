package com.github.sunglow.lead.client;

import com.github.sunglow.lead.registry.ItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

/**
 * 模组的创造模式物品栏
 *
 * @Author xueyuntong
 * @Date 2023/4/11 10:40
 */
public class LeadGroup extends CreativeModeTab {

    /**
     * 设置物品栏的标签名
     */
    public LeadGroup() {
        super("lead_group");
    }

    /**
     * 设置物品栏的图标
     */
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ItemRegistry.leadIngot.get());
    }
}
