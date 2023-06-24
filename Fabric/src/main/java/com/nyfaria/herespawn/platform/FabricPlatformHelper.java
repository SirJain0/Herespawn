package com.nyfaria.herespawn.platform;

import com.nyfaria.herespawn.CommonClass;
import com.nyfaria.herespawn.Herespawn;
import com.nyfaria.herespawn.platform.services.IPlatformHelper;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public void sendPacket(Player player) {
        ClientPlayNetworking.send(CommonClass.REQUEST_RESPAWN_AT_DEATH, new FriendlyByteBuf(Unpooled.buffer()));
    }
}
