package tfar.heroicdeath;

import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.CombatEntry;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

public class MixinEvents {


	public static boolean set = false;

	public static Component get = null;

	public static Component getString(CombatTracker combatTracker) {
		ServerPlayer player = (ServerPlayer) combatTracker.mob;
		boolean afk = player.getLastActionTime() > 0L
						&& player.server.getPlayerIdleTimeout() > 0
						&& Util.getMillis() - player.getLastActionTime() > player.server.getPlayerIdleTimeout()  * 1000L * 6L;
		
		if (combatTracker.entries.isEmpty()) {
			if (HeroicDeath.registry.get("generic") == null) {
				HeroicDeath.logger.warn("no death message found for damage source generic");
				return null;
			}
			return Component.translatable(HeroicDeath.registry.get("generic").getRandom(afk), combatTracker.mob.getDisplayName());
		} else {
			CombatEntry biggestDeathCause = combatTracker.getMostSignificantFall();
			CombatEntry lastDeathCause = combatTracker.entries.get(combatTracker.entries.size() - 1);
			DamageSource source = lastDeathCause.source();
			Entity killer = source.getEntity();

			String damageName = lastDeathCause.source().getMsgId();
			if (HeroicDeath.registry.get(damageName) == null) {
				HeroicDeath.logger.warn("no death message found for damage source " + damageName);
				return null;
			} else {

				if ("mob".equals(damageName)) {

					String entityType = BuiltInRegistries.ENTITY_TYPE.getKey(killer.getType()).toString();

					String biggest = biggestDeathCause != null ? biggestDeathCause.source().getMsgId() : null;

					String message = HeroicDeath.mobDeathEntry.getRandom(entityType,afk,biggest);

					if (message == null) return null;

					else {
							return Component.translatable(message,
											combatTracker.mob.getDisplayName());
					}

				} else {

					return Component.translatable(HeroicDeath.registry.get(damageName).getRandom(afk),
									combatTracker.mob.getDisplayName());
				}
			}
		}
	}
}
