package com.github.sunglow.lead;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

/**
 * 铅锭
 */
public class LeadIngot extends Item {

    public LeadIngot() {
        //放入创造物品栏中的"杂项"中
        super(new Properties().tab(CreativeModeTab.TAB_MATERIALS));
    }
}
