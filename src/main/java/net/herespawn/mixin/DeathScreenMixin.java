package net.herespawn.mixin;

import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DeathScreen.class)
public class DeathScreenMixin extends Screen {
	protected DeathScreenMixin(Text title) {
		super(title);
	}

	@Inject(at = @At("HEAD"), method = "init()V")
	protected void init(CallbackInfo info) {
		if (this.client == null) return;

		// Adds button to death screen
		this.addDrawableChild(ButtonWidget.builder(
			Text.translatable("gui.herespawn.respawn_at_death_location"),
			(button) -> {
				System.out.println("Mixin for Herespawn accessed!");
			}
		)
		.dimensions(this.width / 2 - 100, this.height / 4 + 120, 200, 20)
		.build());
	}
}