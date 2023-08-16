package com.nyfaria.herespawn.cap;

import com.nyfaria.herespawn.Constants;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.resources.ResourceLocation;

public class HerespawnHolderAttacher implements EntityComponentInitializer {
    public static final ComponentKey<HerespawnHolder> HERESPAWN = ComponentRegistryV3.INSTANCE.getOrCreate(new ResourceLocation(Constants.MODID, "herespawn"), HerespawnHolder.class);


    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(HERESPAWN, HerespawnHolder::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
}
