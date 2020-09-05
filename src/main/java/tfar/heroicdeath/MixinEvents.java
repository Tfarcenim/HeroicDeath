package tfar.heroicdeath;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.CombatEntry;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class MixinEvents {


	public static boolean set = false;

	public static ITextComponent get = null;

	public static ITextComponent getString(CombatTracker combatTracker) {
		ServerPlayerEntity player = (ServerPlayerEntity) combatTracker.fighter;
		boolean afk = player.getLastActiveTime() > 0L
						&& player.server.getMaxPlayerIdleMinutes() > 0
						&& Util.milliTime() - player.getLastActiveTime() > player.server.getMaxPlayerIdleMinutes()  * 1000 * 6;

		if (combatTracker.combatEntries.isEmpty()) {
			if (HeroicDeath.registry.get("generic") == null) {
				HeroicDeath.logger.warn("no death message found for damage source generic");
				return null;
			}
			return new TranslationTextComponent(HeroicDeath.registry.get("generic").getRandom(afk), combatTracker.fighter.getDisplayName());
		} else {
			CombatEntry biggestDeathCause = combatTracker.getBestCombatEntry();
			CombatEntry lastDeathCause = combatTracker.combatEntries.get(combatTracker.combatEntries.size() - 1);
			ITextComponent itextcomponent1 = lastDeathCause.getDamageSrcDisplayName();
			Entity killer = lastDeathCause.getDamageSrc().getTrueSource();

			String damageName = lastDeathCause.getDamageSrc().damageType;
			if (HeroicDeath.registry.get(damageName) == null) {
				HeroicDeath.logger.warn("no death message found for damage source " + damageName);
				return null;
			} else {

				if ("mob".equals(damageName)) {

					String entityType = killer.getType().getRegistryName().toString();

					String biggest = biggestDeathCause != null ? biggestDeathCause.getDamageSrc().damageType : null;

					String message = HeroicDeath.mobDeathEntry.getRandom(entityType,afk,biggest);

					if (message == null) return null;

					else {
							return new TranslationTextComponent(message,
											combatTracker.fighter.getDisplayName());
					}

				} else {

					return new TranslationTextComponent(HeroicDeath.registry.get(damageName).getRandom(afk),
									combatTracker.fighter.getDisplayName());
				}
			}
		}
	}
}
