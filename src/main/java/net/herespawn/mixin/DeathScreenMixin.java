package net.herespawn.mixin;

import net.herespawn.Herespawn;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.List;

@Mixin(DeathScreen.class)
public class DeathScreenMixin extends Screen {
	@Shadow
	private int ticksSinceDeath;

	@Shadow
	private final List<ButtonWidget> buttons;

	@Shadow public void tick() {
		super.tick();
		this.setButtonsActive(false);
		++this.ticksSinceDeath;
		if (this.ticksSinceDeath == 20) {
			this.setButtonsActive(true);
		}
	}

	@Shadow private void setButtonsActive(boolean active) {
		ButtonWidget buttonWidget;
		for(Iterator var2 = this.buttons.iterator(); var2.hasNext(); buttonWidget.active = active) {
			buttonWidget = (ButtonWidget)var2.next();
		}
	}

	protected DeathScreenMixin(Text title, List<ButtonWidget> buttons) {
		super(title);
		this.buttons = buttons;
	}

	@Inject(at = @At("HEAD"), method = "init()V")
	protected void init(CallbackInfo info) {
		Herespawn.REQUESTS_RESPAWN_AT_DEATH = false;
		if (this.client == null) return;

		ButtonWidget herespawnButton = ButtonWidget.builder(
			Text.translatable("gui.herespawn.respawn_at_death_location"),
			(button) -> {
				ClientPlayerEntity player = this.client.player;
				if (player == null) return;

				Herespawn.DEATH_POS = player.getPos();
				Herespawn.REQUESTS_RESPAWN_AT_DEATH = true;

				player.requestRespawn();
				button.active = false;
			}
		)
		.dimensions(this.width / 2 - 100, this.height / 4 + 120, 200, 20)
		.build();

		// Adds button to death screen
		this.addDrawableChild(herespawnButton);
		buttons.add(herespawnButton);
	}
}