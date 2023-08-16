package com.nyfaria.herespawn.network;

import com.nyfaria.herespawn.CommonClass;
import com.nyfaria.herespawn.api.HerespawnInfo;
import com.nyfaria.herespawn.config.CommonConfig;
import com.nyfaria.herespawn.platform.Services;
import dev._100media.capabilitysyncer.network.IPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

public record SetSpawnPacket() implements IPacket {
    @Override
    public void handle(NetworkEvent.Context context) {
        context.getSender().reviveCaps();
        HerespawnInfo info = Services.PLATFORM.getHerespawnInfo(context.getSender());
        if (info == null) return;
        info.setCooldown(CommonConfig.INSTANCE.herespawnCooldown.get());
        info.setShouldHerespawn(true);
    }
    public static SetSpawnPacket read(FriendlyByteBuf packetBuf) {
        return new SetSpawnPacket();
    }
    @Override
    public void write(FriendlyByteBuf packetBuf) {

    }
    public static void register(SimpleChannel channel, int id) {
        IPacket.register(channel, id, NetworkDirection.PLAY_TO_SERVER, SetSpawnPacket.class, SetSpawnPacket::read);
    }
}
