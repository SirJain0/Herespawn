package com.nyfaria.herespawn;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.phys.Vec3;

public class Herespawn implements ModInitializer {
    
    @Override
    public void onInitialize() {
        CommonClass.init();
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            CommonClass.doRespawn(newPlayer);
        });
        ServerPlayNetworking.registerGlobalReceiver(CommonClass.REQUEST_RESPAWN_AT_DEATH, (server, player, handler, buf, responseSender) -> {
            CommonClass.setRespawnInfo(player);
        });
    }
}
