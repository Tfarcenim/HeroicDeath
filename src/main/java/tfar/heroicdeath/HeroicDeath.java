package tfar.heroicdeath;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

@Mod(HeroicDeath.MODID)
@Mod.EventBusSubscriber
public class HeroicDeath {
    public static final String MODID = "heroicdeath";

    public static Logger logger = LogManager.getLogger();

    public static final Map<String,DeathEntry> registry = new HashMap<>();

    public static MobDeathEntry mobDeathEntry;

    public HeroicDeath() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::post);
        MinecraftForge.EVENT_BUS.addListener(this::onCommandsRegister);
    }

    private void post(FMLCommonSetupEvent event) {
        Setup.parse();
    }

    private void onCommandsRegister(RegisterCommandsEvent event) {
        ModCommand.command(event.getDispatcher());
    }
}
