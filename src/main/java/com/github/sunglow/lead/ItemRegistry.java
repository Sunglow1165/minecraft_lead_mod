package com.github.sunglow.lead;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * 物品注册
 */
public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,Utils.MOD_ID);
    public static final RegistryObject<Item> leadIngot = ITEMS.register("lead_ingot", LeadIngot::new);
}
