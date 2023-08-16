package com.nyfaria.herespawn.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class CommonConfig {
    public static final ForgeConfigSpec CONFIG_SPEC;
    public static final CommonConfig INSTANCE;

    static {
        Pair<CommonConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        CONFIG_SPEC = pair.getRight();
        INSTANCE = pair.getLeft();
    }
    public final ForgeConfigSpec.IntValue herespawnAreaRange;
    public final ForgeConfigSpec.IntValue herespawnCooldown;
    public final ForgeConfigSpec.BooleanValue autoHerespawn;
    public final ForgeConfigSpec.BooleanValue enableRingOfHerespawn;
    public final ForgeConfigSpec.BooleanValue curiosTrinketsSupportEnabled;

    private CommonConfig(ForgeConfigSpec.Builder builder) {
        this.herespawnAreaRange = builder.defineInRange("herespawn-area-rang", 0, 0, 10000);
        this.herespawnCooldown = builder.defineInRange("herespawn-cooldown", 0, 0, 10000);
        this.autoHerespawn = builder.define("auto-herespawn", false);
        this.enableRingOfHerespawn = builder.define("enable-ring-of-herespawn", false);
        this.curiosTrinketsSupportEnabled = builder.define("curios-trinkets-support-enabled", false);
    }
}
