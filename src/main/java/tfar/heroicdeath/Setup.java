package tfar.heroicdeath;

import com.google.gson.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber
public class Setup {

	public static Gson g = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

	public static String configLocation;

	public static void reload() {
		File folder = new File(configLocation);

		File[] files = folder.listFiles();

		if (files == null) {
			HeroicDeath.logger.warn("No custom messages found in /config/heroicdeath/");
			return;
		}

		for (File file : files) {
			String name = file.getName();
			String name1 = name.substring(0, name.length() - 5);

			FileReader fileReader = null;
			try {
				fileReader = new FileReader(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			if (name1.equals("mob")) {
				JsonArray jsonObject = g.fromJson(fileReader, JsonArray.class);
				MobDeathEntry.Category.Biggest biggest = null;
				JsonArray jsonObject1 = jsonObject.getAsJsonArray();
				Map<String, MobDeathEntry.Category> stringCategoryMap = new HashMap<>();
				for (JsonElement element : jsonObject1) {

					JsonObject jsonObject2 = element.getAsJsonObject();

					Map.Entry<String,JsonElement> elementEntry = jsonObject2.entrySet().stream().findFirst().get();

					String entityType = elementEntry.getKey();
					JsonObject jsonObjectEntityType = elementEntry.getValue().getAsJsonObject();

					JsonArray afkArray = jsonObjectEntityType.getAsJsonArray("afk");
					JsonArray normalArray = jsonObjectEntityType.getAsJsonArray("normal");
					JsonObject bigObject = jsonObjectEntityType.getAsJsonObject("biggest");
					Map<String,List<String>> biggest1 = new HashMap<>();

					for (Map.Entry<String, JsonElement> element1 : bigObject.entrySet()) {
						String damageType = element1.getKey();
						JsonArray jsonArray = element1.getValue().getAsJsonArray();
						List<String> variants = new ArrayList<>();
						jsonArray.forEach(jsonElement -> variants.add(jsonElement.getAsString()));
						biggest1.put(damageType,variants);
					}
					biggest = new MobDeathEntry.Category.Biggest(biggest1);

					List<String> normal = new ArrayList<>();
					for (JsonElement element2 : normalArray) {
						normal.add(element2.getAsString());
					}

					List<String> afk = new ArrayList<>();
					for (JsonElement element2 : afkArray) {
						afk.add(element2.getAsString());
					}
					MobDeathEntry.Category category = new MobDeathEntry.Category(normal,afk,biggest);
					stringCategoryMap.put(entityType,category);
				}

				HeroicDeath.mobDeathEntry = new MobDeathEntry(stringCategoryMap);

			} else {
				JsonObject jsonObject = g.fromJson(fileReader, JsonObject.class);
				JsonArray normalArr;
				try {
					normalArr = jsonObject.get("normal").getAsJsonArray();
				} catch (Exception e) {
					normalArr = new JsonArray();
					e.printStackTrace();
				}

				List<String> normal = new ArrayList<>();
				for (JsonElement element : normalArr) {
					normal.add(element.getAsString());
				}

				JsonArray afkArr;
				try {
					afkArr = jsonObject.get("afk").getAsJsonArray();
				} catch (Exception e) {
					afkArr = new JsonArray();
					e.printStackTrace();
				}

				List<String> afk = new ArrayList<>();
				for (JsonElement element : afkArr) {
					afk.add(element.getAsString());
				}

				DeathEntry deathEntry = new DeathEntry(normal, afk);
				HeroicDeath.registry.put(name1, deathEntry);
			}
		}
	}

	public static void parse(FMLPreInitializationEvent event) {
		configLocation = event.getModConfigurationDirectory().getAbsolutePath() + "/" + HeroicDeath.MODID;
		reload();
	}
}
