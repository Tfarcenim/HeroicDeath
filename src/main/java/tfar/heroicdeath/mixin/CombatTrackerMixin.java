package tfar.heroicdeath.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.heroicdeath.MixinEvents;

@Mixin(CombatTracker.class)
public class CombatTrackerMixin {

	@Shadow @Final public LivingEntity fighter;

	@Inject(method = "getDeathMessage",at = @At("HEAD"),cancellable = true)
	private void modifyDeathMessage(CallbackInfoReturnable<ITextComponent> text) {
		if (this.fighter instanceof PlayerEntity) {
			ITextComponent message = MixinEvents.getString((CombatTracker)(Object)this);
			if (message != null) {
				text.setReturnValue(message);
			}
		}
	}
}
