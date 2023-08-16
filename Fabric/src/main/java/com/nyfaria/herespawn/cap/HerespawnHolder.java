package com.nyfaria.herespawn.cap;

import com.nyfaria.herespawn.api.HerespawnInfo;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.CopyableComponent;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class HerespawnHolder implements HerespawnInfo, ComponentV3, Component, CopyableComponent<HerespawnHolder>, AutoSyncedComponent {
    public ResourceKey<Level> dimension;
    public BlockPos pos;
    public int cooldown;
    public boolean shouldHerespawn;
    public Player provider;
    public HerespawnHolder(Player provider) {
        this.provider = provider;
    }



    @Override
    public int getCooldown() {
        return 0;
    }

    @Override
    public void setCooldown(int cooldown) {

    }

    @Override
    public boolean shouldHerespawn() {
        return false;
    }

    @Override
    public void setShouldHerespawn(boolean shouldHerespawn) {

    }

    @Override
    public void setHerespawnInfo(int cooldown, boolean shouldHerespawn) {
        this.shouldHerespawn = shouldHerespawn;
        this.cooldown = cooldown;
        HerespawnHolderAttacher.HERESPAWN.sync(provider);
    }

    @Override
    public void readFromNbt(CompoundTag tag) {

    }

    @Override
    public void writeToNbt(CompoundTag tag) {

    }

    @Override
    public void copyFrom(HerespawnHolder other) {

    }
}
