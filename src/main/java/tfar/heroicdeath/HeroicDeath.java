package tfar.heroicdeath;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

@Mod(modid = HeroicDeath.MODID, name = HeroicDeath.NAME, version = HeroicDeath.VERSION)
@Mod.EventBusSubscriber
public class HeroicDeath {
    public static final String MODID = "heroicdeath";
    public static final String NAME = "Heroic Death";
    public static final String VERSION = "1.0";

    public static Logger logger = LogManager.getLogger();

    public static final Map<String,DeathEntry> registry = new HashMap<>();

    public static MobDeathEntry mobDeathEntry;

    @EventHandler
    public void post(FMLPreInitializationEvent event) {
        Setup.parse(event);
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent evt) {
        evt.registerServerCommand(new ModCommands());
    }

}
