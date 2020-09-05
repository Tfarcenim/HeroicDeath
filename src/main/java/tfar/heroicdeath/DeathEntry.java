package tfar.heroicdeath;

import java.util.List;
import java.util.Random;

public class DeathEntry {

	private final List<String> normal;
	private final List<String> afk;
	private final Random rand = new Random();

	public DeathEntry(List<String> normal, List<String> afk) {
		this.normal = normal;
		this.afk = afk;
	}

	public String getRandom(boolean wasafk) {
		if (wasafk) {
			int rng = rand.nextInt(afk.size());
			return afk.get(rng);
		} else {
			int rng = rand.nextInt(normal.size());
			return normal.get(rng);
		}
	}
}
