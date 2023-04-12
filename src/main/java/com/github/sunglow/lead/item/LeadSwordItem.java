package com.github.sunglow.lead.item;

import com.github.sunglow.lead.client.ModGroup;
import com.github.sunglow.lead.common.ModItemTier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;

/**
 * 铅剑,两种注册的方法
 *
 * @Author xueyuntong
 * @Date 2023/4/11 14:22
 */
public class LeadSwordItem extends SwordItem {

    public LeadSwordItem() {
        //材料,攻击力,攻击速度,属性-放入模组的物品栏中
        super(ModItemTier.LEAD,7,-2.4F,new Item.Properties().tab(ModGroup.LEAD_MODE_TAB));
    }
}
