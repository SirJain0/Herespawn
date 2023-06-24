package com.nyfaria.herespawn.event;


import com.nyfaria.herespawn.CommonClass;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonForgeEvents {

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event){
        CommonClass.doRespawn(event.getEntity());
    }
}
