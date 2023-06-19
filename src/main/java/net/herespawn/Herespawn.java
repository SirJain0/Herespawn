package net.herespawn;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Herespawn implements ModInitializer {
    public static final String MOD_ID = "herespawn";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static Vec3d DEATH_POS = null;
    public static boolean REQUESTS_RESPAWN_AT_DEATH = false;

    @Override
    public void onInitialize() {
        LOGGER.info("Initialized Herespawn");

        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            if (REQUESTS_RESPAWN_AT_DEATH && DEATH_POS != null) {
                newPlayer.teleport(DEATH_POS.x, DEATH_POS.y, DEATH_POS.z);
            }
        });
    }
}