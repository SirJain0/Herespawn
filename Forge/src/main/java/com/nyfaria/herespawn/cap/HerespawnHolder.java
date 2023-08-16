package com.nyfaria.herespawn.cap;

import com.nyfaria.herespawn.api.HerespawnInfo;
import com.nyfaria.herespawn.network.NetworkHandler;
import dev._100media.capabilitysyncer.core.EntityCapability;
import dev._100media.capabilitysyncer.core.PlayerCapability;
import dev._100media.capabilitysyncer.network.EntityCapabilityStatusPacket;
import dev._100media.capabilitysyncer.network.SimpleEntityCapabilityStatusPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.simple.SimpleChannel;

public class HerespawnHolder extends PlayerCapability implements HerespawnInfo {
    public ResourceKey<Level> dimension = null;
    public BlockPos pos = BlockPos.ZERO;
    public int cooldown = 0;
    public boolean shouldHerespawn = false;
    protected HerespawnHolder(Player entity) {
        super(entity);
    }


    @Override
    public CompoundTag serializeNBT(boolean savingToDisk) {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("shouldHerespawn", shouldHerespawn);
        tag.putInt("cooldown", cooldown);
        tag.putInt("posX", pos.getX());
        tag.putInt("posY", pos.getY());
        tag.putInt("posZ", pos.getZ());
        if(dimension != null) {
            tag.putString("dimension", dimension.location().toString());
        }
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt, boolean readingFromDisk) {
        shouldHerespawn = nbt.getBoolean("shouldHerespawn");
        cooldown = nbt.getInt("cooldown");
        pos = new BlockPos(nbt.getInt("posX"), nbt.getInt("posY"), nbt.getInt("posZ"));
        if(nbt.contains("dimension")) {
            dimension = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(nbt.getString("dimension")));
        }
    }

    @Override
    public EntityCapabilityStatusPacket createUpdatePacket() {
        return new SimpleEntityCapabilityStatusPacket(this.entity.getId(), HerespawnHolderAttacher.RESOURCE_LOCATION, this);
    }

    @Override
    public SimpleChannel getNetworkChannel() {
        return NetworkHandler.INSTANCE;
    }

    public void setHerespawnInfo(int cooldown, boolean shouldHerespawn){
        this.cooldown = cooldown;
        this.shouldHerespawn = shouldHerespawn;
        updateTracking();
    }



    @Override
    public int getCooldown() {
        return Mth.ceil(cooldown % 20d);
    }

    @Override
    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
        updateTracking();
    }

    @Override
    public boolean shouldHerespawn() {
        return shouldHerespawn;
    }

    @Override
    public void setShouldHerespawn(boolean shouldHerespawn) {
        this.shouldHerespawn = shouldHerespawn;
        updateTracking();
    }
}
