package com.nyfaria.herespawn;

import com.nyfaria.herespawn.api.HerespawnInfo;
import com.nyfaria.herespawn.config.CommonConfig;
import com.nyfaria.herespawn.init.ItemInit;
import com.nyfaria.herespawn.platform.Services;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class CommonClass {
    public static Int2ObjectMap<Vec3> DEATH_POS = new Int2ObjectOpenHashMap<>();
    public static Int2ObjectMap<ResourceKey<Level>> DEATH_DIM = new Int2ObjectOpenHashMap<>();
    public static ResourceLocation REQUEST_RESPAWN_AT_DEATH = new ResourceLocation(Constants.MODID, "request_respawn_at_death");

    // The loader specific projects are able to import and use any code from the common project. This allows you to
    // write the majority of your code here and load it from your loader specific projects. This example has some
    // code that gets invoked by the entry point of the loader specific projects.
    public static void init() {
        ItemInit.loadClass();
    }

//    public static void setRespawnInfo(Player player) {
//        CommonClass.DEATH_POS.put(player.getId(), player.position());
//        CommonClass.DEATH_DIM.put(player.getId(), player.level().dimension());
//    }

    public static void doRespawn(Player newPlayer) {
        HerespawnInfo herespawnInfo = Services.PLATFORM.getHerespawnInfo(newPlayer);
        if (herespawnInfo == null) return;
        if (herespawnInfo.shouldHerespawn()) {
            BlockPos deathPos = newPlayer.getLastDeathLocation().get().pos();
            newPlayer.changeDimension(newPlayer.level().getServer().getLevel(newPlayer.getLastDeathLocation().get().dimension()));

            if (CommonConfig.INSTANCE.herespawnAreaRange.get() > 0) {
                int range = CommonConfig.INSTANCE.herespawnAreaRange.get();
                int randX = newPlayer.getRandom().nextInt(-range, range);
                int randZ = newPlayer.getRandom().nextInt(-range, range);
                newPlayer.randomTeleport(deathPos.getX() + randX, deathPos.getY(), deathPos.getZ() + randZ, false);
            } else {
                newPlayer.teleportTo(deathPos.getX(), deathPos.getY(), deathPos.getZ());
            }
            herespawnInfo.setShouldHerespawn(false);
        }
    }
}