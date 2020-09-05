package tfar.heroicdeath;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MobDeathEntry {

	final Map<String,Category> list;
	final Random random = new Random();

	public MobDeathEntry(Map<String, Category> list) {
		this.list = list;
	}

	public static class Category {
		final List<String> normal;
		final List<String> afk;
		final Biggest biggest;

		public Category(List<String> normal, List<String> afk, Biggest biggest) {
			this.normal = normal;
			this.afk = afk;
			this.biggest = biggest;
		}

		public static class Biggest {
			final Map<String,List<String>> strings;

			public Biggest(Map<String, List<String>> strings) {
				this.strings = strings;
			}
		}
	}

	@Nullable
	public String getRandom(String entityType, boolean afk, @Nullable String biggest) {
		if (afk) {
			List<String> afkEntries = list.get(entityType).afk;
			if (afkEntries.isEmpty()){
				HeroicDeath.logger.warn("no afk entry found for "+entityType);
				return null;
			}
			return afkEntries.get(random.nextInt(afkEntries.size()));
		} else if (entityType.equals(biggest)) {
			List<String> normalEntries = list.get(entityType).normal;
			if (normalEntries.isEmpty()) {
				HeroicDeath.logger.warn("no normal entry found for " + entityType);
				return null;
			}
			return normalEntries.get(random.nextInt(normalEntries.size()));
		} else {
			Category.Biggest biggest1 = list.get(entityType).biggest;
			List<String> biggestEntries = biggest1.strings.get(biggest);
			if (biggestEntries.isEmpty()) {
				HeroicDeath.logger.warn("no biggest entry found for "+entityType);
				return null;
			}
			return biggestEntries.get(random.nextInt(biggestEntries.size()));
		}
	}

}
