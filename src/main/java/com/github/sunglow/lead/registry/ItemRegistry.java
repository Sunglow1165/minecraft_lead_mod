package com.github.sunglow.lead.registry;

import com.github.sunglow.lead.client.group.ModGroup;
import com.github.sunglow.lead.common.ModArmor;
import com.github.sunglow.lead.common.ModArmorMaterial;
import com.github.sunglow.lead.common.Utils;
import com.github.sunglow.lead.item.LeadAppleItem;
import com.github.sunglow.lead.item.LeadIngotItem;
import com.github.sunglow.lead.item.LeadPickaxeItem;
import com.github.sunglow.lead.item.LeadSwordItem;
import com.github.sunglow.lead.item.MagicIngotItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * 物品注册表
 */
public class ItemRegistry {
    //延迟注册对象，用于在mod中方便地创建和注册对象，而不需要自己处理注册事件和注册表。它的原理是使用了ModEventBus和RegistryEvent来在正确的时机进行注册
    //DeferredRegister.create(注册表的类型,命名空间)
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);
    //RegistryObject类是一个用来存储和获取注册对象的类，它可以让你在你的mod中方便地访问你注册的对象，而不需要自己处理对象持有者（object holder）。
    //它的原理是使用了Supplier接口和LazyValue类来在正确的时机获取对象(注册名,实例对象)
    public static final RegistryObject<Item> leadIngot = ITEMS.register("lead_ingot", LeadIngotItem::new);
    public static final RegistryObject<Item> magicIngot = ITEMS.register("magic_ingot", MagicIngotItem::new);
    public static final RegistryObject<Item> leadApple = ITEMS.register("lead_apple", LeadAppleItem::new);
    public static final RegistryObject<Item> leadPickaxe = ITEMS.register("lead_pickaxe", LeadPickaxeItem::new);
    public static final RegistryObject<Item> leadSword = ITEMS.register("lead_sword", LeadSwordItem::new);
    //    public static final RegistryObject<Item> leadSword = ITEMS.register("lead_sword",
//            () -> new SwordItem(ModItemTier.LEAD, 7, -2.4F, new Item.Properties().tab(ModGroup.LEAD_MODE_TAB)));//两种注册的方法
    public static final RegistryObject<Item> leadHelmet = ITEMS.register("lead_helmet",
            () -> new ModArmor(ModArmorMaterial.LEAD, EquipmentSlot.HEAD));
    public static final RegistryObject<Item> leadBlock = ITEMS.register("lead_block",
            () -> new BlockItem(BlockRegistry.leadBlock.get(), new Item.Properties().tab(ModGroup.LEAD_MODE_TAB)));

}