package com.github.sunglow.lead.common;

import com.github.sunglow.lead.client.group.ModGroup;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

/**
 * 盔甲穿在身上的渲染纹理
 *
 * @Author xueyuntong
 * @Date 2023/4/12 11:50
 */
public class ModArmor extends ArmorItem {


    public ModArmor(ArmorMaterial armorMaterial, EquipmentSlot equipmentSlot) {
        super(armorMaterial, equipmentSlot, new Item.Properties().tab(ModGroup.LEAD_MODE_TAB));
    }

    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        if (this.material == ModArmorMaterial.LEAD) {
            return "xue_lead:textures/armor/lead_layer_1.png";
        }
        return super.getArmorTexture(stack, entity, slot, type);
    }
}
