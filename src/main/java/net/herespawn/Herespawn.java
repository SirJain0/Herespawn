package net.herespawn;

import it.unimi.dsi.fastutil.ints.Int2BooleanMap;
import it.unimi.dsi.fastutil.ints.Int2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Herespawn implements ModInitializer {
    public static final String MOD_ID = "herespawn";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static Int2ObjectMap<Vec3d> DEATH_POS = new Int2ObjectOpenHashMap<>();
    public static Int2BooleanMap REQUESTS_RESPAWN_AT_DEATH = new Int2BooleanOpenHashMap();
    public static Identifier REQUEST_RESPAWN_AT_DEATH = new Identifier(MOD_ID, "request_respawn_at_death");

    @Override
    public void onInitialize() {
        LOGGER.info("Initialized Herespawn");

        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            if (REQUESTS_RESPAWN_AT_DEATH.get(newPlayer.getId()) && DEATH_POS.get(newPlayer.getId()) != null) {
                Vec3d deathPos = DEATH_POS.get(newPlayer.getId());
                newPlayer.teleport(deathPos.x, deathPos.y, deathPos.z);
            }
        });
        ServerPlayNetworking.registerGlobalReceiver(REQUEST_RESPAWN_AT_DEATH, (server, player, handler, buf, responseSender) -> {
            REQUESTS_RESPAWN_AT_DEATH.put(player.getId(), true);
            DEATH_POS.put(player.getId(), player.getPos());
        });
    }
}