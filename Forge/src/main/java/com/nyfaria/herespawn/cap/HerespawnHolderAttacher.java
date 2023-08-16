package com.nyfaria.herespawn.cap;

import com.nyfaria.herespawn.Constants;
import dev._100media.capabilitysyncer.core.CapabilityAttacher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MODID)
public class HerespawnHolderAttacher extends CapabilityAttacher {
    public static final Capability<HerespawnHolder> CAPABILITY = getCapability(new CapabilityToken<>() {});
    public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(Constants.MODID, "herespawn");
    private static final Class<HerespawnHolder> CAPABILITY_CLASS = HerespawnHolder.class;

    @SuppressWarnings("ConstantConditions")
    public static HerespawnHolder getHolderUnwrap(Entity player) {
        return getHolder(player).orElse(null);
    }

    public static LazyOptional<HerespawnHolder> getHolder(Entity player) {
        return player.getCapability(CAPABILITY);
    }

    private static void attach(AttachCapabilitiesEvent<Entity> event, Player entity) {
        genericAttachCapability(event, new HerespawnHolder(entity), CAPABILITY, RESOURCE_LOCATION);
    }

    public static void register() {
        CapabilityAttacher.registerCapability(CAPABILITY_CLASS);
        CapabilityAttacher.registerPlayerAttacher(HerespawnHolderAttacher::attach, HerespawnHolderAttacher::getHolder,true);
    }
}
