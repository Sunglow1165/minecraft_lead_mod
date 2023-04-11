package com.github.sunglow.lead.registry;

import com.github.sunglow.lead.client.ModGroup;
import com.github.sunglow.lead.common.ModArmorMaterial;
import com.github.sunglow.lead.common.Utils;
import com.github.sunglow.lead.item.LeadAppleItem;
import com.github.sunglow.lead.item.LeadIngotItem;
import com.github.sunglow.lead.item.LeadSwordItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * 物品注册
 */
public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);
    public static final RegistryObject<Item> leadIngot = ITEMS.register("lead_ingot", LeadIngotItem::new);
    public static final RegistryObject<Item> leadApple = ITEMS.register("lead_apple", LeadAppleItem::new);
    public static final RegistryObject<Item> leadSword = ITEMS.register("lead_sword", LeadSwordItem::new);
    public static final RegistryObject<Item> leadPickaxe = ITEMS.register("lead_pickaxe", LeadSwordItem::new);
    public static final RegistryObject<Item> leadHelmet = ITEMS.register("lead_helmet",
            () -> new ArmorItem(ModArmorMaterial.LEAD, EquipmentSlot.HEAD,
                    (new Item.Properties().tab(ModGroup.CREATIVE_MODE_TAB))));
}