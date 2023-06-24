package com.nyfaria.herespawn.network;

import com.nyfaria.herespawn.CommonClass;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

public record SetSpawnPacket() implements IPacket{
    @Override
    public void handle(NetworkEvent.Context context) {
        CommonClass.setRespawnInfo(context.getSender());
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
