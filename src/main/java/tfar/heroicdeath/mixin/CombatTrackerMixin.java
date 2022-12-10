package tfar.heroicdeath.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.heroicdeath.MixinEvents;

@Mixin(CombatTracker.class)
public class CombatTrackerMixin {

	@Shadow @Final public LivingEntity mob;

	@Inject(method = "getDeathMessage",at = @At("HEAD"),cancellable = true)
	private void modifyDeathMessage(CallbackInfoReturnable<Component> text) {
		if (this.mob instanceof Player) {
			Component message = MixinEvents.getString((CombatTracker)(Object)this);
			if (message != null) {
				text.setReturnValue(message);
			}
		}
	}
}
