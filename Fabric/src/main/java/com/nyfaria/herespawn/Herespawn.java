package com.nyfaria.herespawn;

import com.nyfaria.herespawn.api.HerespawnInfo;
import com.nyfaria.herespawn.config.CommonConfig;
import com.nyfaria.herespawn.platform.Services;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.config.ModConfig;

public class Herespawn implements ModInitializer {
    
    @Override
    public void onInitialize() {
        CommonClass.init();
        ForgeConfigRegistry.INSTANCE.register(Constants.MODID, ModConfig.Type.COMMON, CommonConfig.CONFIG_SPEC);
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            CommonClass.doRespawn(newPlayer);
        });
        ServerPlayNetworking.registerGlobalReceiver(CommonClass.REQUEST_RESPAWN_AT_DEATH, (server, player, handler, buf, responseSender) -> {
            HerespawnInfo herespawnInfo = Services.PLATFORM.getHerespawnInfo(player);
            if (herespawnInfo == null) return;
            herespawnInfo.setHerespawnInfo( CommonConfig.INSTANCE.herespawnCooldown.get(), true);
        });
    }
}
