package com.github.sunglow.lead.common;

import com.github.sunglow.lead.registry.ItemRegistry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * 盔甲材料
 *
 * @Author xueyuntong
 * @Date 2023/4/11 18:22
 */
public enum ModArmorMaterial implements ArmorMaterial {

    /**
     * 铅
     */
    LEAD("lead", 40, new int[]{5, 8, 10, 15}, 20
            , SoundEvents.ARMOR_EQUIP_LEATHER, 2.0F, 0.0F, () -> Ingredient.of(ItemRegistry.leadIngot.get()));

    /**
     * 盔甲材料名称
     */
    private final String name;
    /**
     * 装甲耐久度的倍数
     */
    private final int durabilityMultiplier;
    /**
     * 每个装备槽位提供的防御值数组
     */
    private final int[] slotProtections;
    /**
     * 可以在装备上附加的附魔等级
     */
    private final int enchantmentValue;
    /**
     * 装备时播放的声音
     */
    private final SoundEvent sound;
    /**
     * 装备提供的抗击打值
     */
    private final float toughness;
    /**
     * 装备提供的减少击退的值
     */
    private final float knockbackResistance;
    /**
     * 修复装备所需的材料
     */
    private final LazyLoadedValue<Ingredient> repairIngredient;
    /**
     * 每个槽位提供的额外生命值
     */
    protected static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};

    ModArmorMaterial(String name, int durabilityMultiplier, int[] slotProtections, int enchantmentValue, SoundEvent sound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.slotProtections = slotProtections;
        this.enchantmentValue = enchantmentValue;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
    }

    public int getDurabilityForSlot(EquipmentSlot p_40484_) {
        return HEALTH_PER_SLOT[p_40484_.getIndex()] * this.durabilityMultiplier;
    }

    public int getDefenseForSlot(EquipmentSlot p_40487_) {
        return this.slotProtections[p_40487_.getIndex()];
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public @NotNull SoundEvent getEquipSound() {
        return this.sound;
    }

    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    public @NotNull String getName() {
        return this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
