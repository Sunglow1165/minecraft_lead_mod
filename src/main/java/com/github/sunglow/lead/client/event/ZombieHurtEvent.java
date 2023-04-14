package com.github.sunglow.lead.client.event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * 僵尸受伤事件
 *
 * @Author xueyuntong
 * @Date 2023/4/14 18:26
 */
@Mod.EventBusSubscriber
public class ZombieHurtEvent {
    @SubscribeEvent
    public static void hurtEvent(LivingHurtEvent event){
        LivingEntity entity = event.getEntity();
        String s = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString();
    }
}
