package com.github.sunglow.lead.item;

import com.github.sunglow.lead.client.ModGroup;
import com.github.sunglow.lead.common.ModItemTier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;

/**
 * 铅稿
 *
 * @Author xueyuntong
 * @Date 2023/4/11 16:57
 */
public class LeadPickaxeItem extends PickaxeItem {
    public LeadPickaxeItem() {
        super(ModItemTier.LEAD,2,-3F,(new Item.Properties()).tab(ModGroup.CREATIVE_MODE_TAB));
    }
}
