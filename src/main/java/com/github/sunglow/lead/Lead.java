package com.github.sunglow.lead;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * 模组主类
 */
@Mod(Utils.MOD_ID)
public class Lead {
    public Lead() {
        ItemRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
