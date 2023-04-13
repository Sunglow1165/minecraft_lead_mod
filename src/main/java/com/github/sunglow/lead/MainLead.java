package com.github.sunglow.lead;

import com.github.sunglow.lead.common.Utils;
import com.github.sunglow.lead.registry.BlockRegistry;
import com.github.sunglow.lead.registry.ItemRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * 模组主类
 */
@Mod(Utils.MOD_ID)
public class MainLead {
    public MainLead() {
        ItemRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockRegistry.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
