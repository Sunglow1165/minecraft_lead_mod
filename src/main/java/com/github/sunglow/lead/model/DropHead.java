package com.github.sunglow.lead.model;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.Event;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

/**
 * @Author xueyuntong
 * @Date 2023/4/12 17:39
 */
@Mod.EventBusSubscriber
public class DropHead {

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        if (event != null && event.getEntity() != null) {
            execute(event, event.getEntity());
        }
    }

    public static void execute(Entity entity) {
        execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        if (entity == null)
            return;
        {
            Entity _ent = entity;
            _ent.setYRot(180);
            _ent.setXRot(180);
            _ent.setYBodyRot(_ent.getYRot());
            _ent.setYHeadRot(_ent.getYRot());
            _ent.yRotO = _ent.getYRot();
            _ent.xRotO = _ent.getXRot();
            if (_ent instanceof LivingEntity _entity) {
                _entity.yBodyRotO = _entity.getYRot();
                _entity.yHeadRotO = _entity.getYRot();
            }
        }
    }

}
