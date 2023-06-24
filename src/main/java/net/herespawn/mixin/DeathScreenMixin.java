package net.herespawn.mixin;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.herespawn.Herespawn;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(DeathScreen.class)
public class DeathScreenMixin extends Screen {
	@Shadow private final List<ButtonWidget> buttons;

	protected DeathScreenMixin(Text title, List<ButtonWidget> buttons) {
		super(title);
		this.buttons = buttons;
	}

	@Inject(at = @At("TAIL"), method = "init()V")
	protected void init(CallbackInfo info) {
		if (this.client == null) return;
		final ClientPlayerEntity player = this.client.player;
		if (player == null) return;
		Herespawn.REQUESTS_RESPAWN_AT_DEATH.remove(player.getId());

		ButtonWidget herespawnButton = ButtonWidget.builder(
			Text.translatable("gui.herespawn.respawn_at_death_location"),
			(button) -> {
//				Herespawn.DEATH_POS.put(player.getId(),player.getPos());
//				Herespawn.REQUESTS_RESPAWN_AT_DEATH.put(player.getId(), true);
				ClientPlayNetworking.send(Herespawn.REQUEST_RESPAWN_AT_DEATH, new PacketByteBuf(Unpooled.buffer()));
				player.requestRespawn();
				button.active = false;
			}
		)
		.dimensions(this.width / 2 - 100, this.height / 4 + 120, 200, 20)
		.build();
		this.addDrawableChild(herespawnButton);
		buttons.add(herespawnButton);
	}
}