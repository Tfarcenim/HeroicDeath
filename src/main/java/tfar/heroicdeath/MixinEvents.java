package tfar.heroicdeath;

import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.CombatEntry;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class MixinEvents {


	public static boolean set = false;

	public static Component get = null;

	public static Component getString(CombatTracker combatTracker) {
		ServerPlayer player = (ServerPlayer) combatTracker.mob;
		boolean afk = player.getLastActionTime() > 0L
						&& player.server.getPlayerIdleTimeout() > 0
						&& Util.getMillis() - player.getLastActionTime() > player.server.getPlayerIdleTimeout()  * 1000 * 6;

		if (combatTracker.entries.isEmpty()) {
			if (HeroicDeath.registry.get("generic") == null) {
				HeroicDeath.logger.warn("no death message found for damage source generic");
				return null;
			}
			return new TranslatableComponent(HeroicDeath.registry.get("generic").getRandom(afk), combatTracker.mob.getDisplayName());
		} else {
			CombatEntry biggestDeathCause = combatTracker.getMostSignificantFall();
			CombatEntry lastDeathCause = combatTracker.entries.get(combatTracker.entries.size() - 1);
			Component itextcomponent1 = lastDeathCause.getAttackerName();
			Entity killer = lastDeathCause.getSource().getEntity();

			String damageName = lastDeathCause.getSource().msgId;
			if (HeroicDeath.registry.get(damageName) == null) {
				HeroicDeath.logger.warn("no death message found for damage source " + damageName);
				return null;
			} else {

				if ("mob".equals(damageName)) {

					String entityType = killer.getType().getRegistryName().toString();

					String biggest = biggestDeathCause != null ? biggestDeathCause.getSource().msgId : null;

					String message = HeroicDeath.mobDeathEntry.getRandom(entityType,afk,biggest);

					if (message == null) return null;

					else {
							return new TranslatableComponent(message,
											combatTracker.mob.getDisplayName());
					}

				} else {

					return new TranslatableComponent(HeroicDeath.registry.get(damageName).getRandom(afk),
									combatTracker.mob.getDisplayName());
				}
			}
		}
	}
}
