package com.github.sunglow.lead.item;

import com.github.sunglow.lead.client.ModGroup;
import net.minecraft.world.item.Item;

/**
 * 铅锭
 */
public class LeadIngotItem extends Item {

    public LeadIngotItem() {
        //放入创造物品栏中，自定义的一个物品栏
        super(new Properties().tab(ModGroup.CREATIVE_MODE_TAB));
    }
}
