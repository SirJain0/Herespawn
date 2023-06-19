package net.herespawn;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Herespawn implements ModInitializer {
    public static final String MOD_ID = "herespawn";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initialized Herespawn");

        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
//            newPlayer.teleport(100, 100, 100); // dummy coordinates
            if (RespawnHelper.getAndClearSpawn() == null) return;
            else RespawnHelper.getAndClearSpawn();
        });
    }
}