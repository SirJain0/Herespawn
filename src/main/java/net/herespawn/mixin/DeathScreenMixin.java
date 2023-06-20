package net.herespawn.mixin;

import net.herespawn.Herespawn;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.ClientConnection;
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
		World world = player.getWorld();

		Herespawn.REQUESTS_RESPAWN_AT_DEATH = false;

		ButtonWidget herespawnButton = ButtonWidget.builder(
			Text.translatable("gui.herespawn.respawn_at_death_location"),
			(button) -> {
				Herespawn.DEATH_POS = player.getPos();
				Herespawn.REQUESTS_RESPAWN_AT_DEATH = true;

				player.requestRespawn();
				button.active = false;
			}
		)
		.dimensions(this.width / 2 - 100, this.height / 4 + 120, 200, 20)
		.build();

		ClientConnection connection = MinecraftClient.getInstance().getNetworkHandler().getConnection();
		if (connection != null && connection.isLocal()) this.addDrawableChild(herespawnButton);

		buttons.add(herespawnButton);
		herespawnButton.active = false;
	}
}