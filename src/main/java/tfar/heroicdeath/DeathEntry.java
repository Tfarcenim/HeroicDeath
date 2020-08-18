package tfar.heroicdeath;

import java.util.List;
import java.util.Random;

public class DeathEntry {

	private final List<String> normal;
	private final List<String> afk;
	private final Random rand = new Random(7);

	public DeathEntry(List<String> normal, List<String> afk) {
		this.normal = normal;
		this.afk = afk;
	}

	public String getRandom(boolean wasafk) {
		return wasafk ? afk.get(rand.nextInt(afk.size())) : normal.get(rand.nextInt(normal.size()));
	}
}
