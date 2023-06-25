package com.nyfaria.herespawn.mixin;

import com.nyfaria.herespawn.platform.Services;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(DeathScreen.class)
public abstract class DeathScreenMixin extends Screen {
    @Shadow
    @Final
    private List<Button> exitButtons;

    protected DeathScreenMixin(Component $$0) {
        super($$0);
    }

    @Inject(at = @At("TAIL"), method = "init")
    protected void init(CallbackInfo info) {
        if (this.minecraft == null) return;
        final LocalPlayer player = this.minecraft.player;
        if (player == null) return;
        Button herespawnButton = new Button(this.width / 2 - 100, this.height / 4 + 120,200, 20,
                        new TranslatableComponent("gui.herespawn.respawn_at_death_location"),
                        (button) -> {
                            Services.PLATFORM.sendPacket(player);
                            player.respawn();
                        }
                );
        this.addRenderableWidget(herespawnButton);
        exitButtons.add(herespawnButton);
    }
}