package com.nyfaria.herespawn.api;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public interface HerespawnInfo {
    int getCooldown();
    void setCooldown(int cooldown);
    boolean shouldHerespawn();
    void setShouldHerespawn(boolean shouldHerespawn);
    void setHerespawnInfo(int cooldown, boolean shouldHerespawn);
}
