package com.github.sunglow.lead.item;

import com.github.sunglow.lead.client.ModGroup;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

/**
 * 铅苹果
 *
 * @Author xueyuntong
 * @Date 2023/4/11 11:40
 */
public class LeadAppleItem extends Item {

    /**
     * 食物的饱和度=饱和度修饰值×饥饿值
     */

    private static final FoodProperties foodData = (new FoodProperties.Builder()
            //设置饱和度修饰值
            .saturationMod(0.6F)
            //设置饥饿值
            .nutrition(4)
            //赋予中毒药水状态,60tick,药水等级1,百分百触发
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 60, 1), 1)
            //创建对象
            .build());

    public LeadAppleItem() {
        //设置属性-食物-属于哪个创造模式选项卡
        super(new Properties().food(foodData).tab(ModGroup.CREATIVE_MODE_TAB));
    }

}
