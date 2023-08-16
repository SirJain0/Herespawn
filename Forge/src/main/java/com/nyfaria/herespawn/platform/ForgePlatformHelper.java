package com.nyfaria.herespawn.platform;

import com.nyfaria.herespawn.api.HerespawnInfo;
import com.nyfaria.herespawn.cap.HerespawnHolderAttacher;
import com.nyfaria.herespawn.network.NetworkHandler;
import com.nyfaria.herespawn.network.SetSpawnPacket;
import com.nyfaria.herespawn.platform.services.IPlatformHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public void sendPacket(Player player) {
        NetworkHandler.INSTANCE.sendToServer(new SetSpawnPacket());
    }

    @Override
    public HerespawnInfo getHerespawnInfo(Player player) {
        if(HerespawnHolderAttacher.getHolderUnwrap(player) != null)
            return HerespawnHolderAttacher.getHolderUnwrap(player);
        else
            return null;
    }
}