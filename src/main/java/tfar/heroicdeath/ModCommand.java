package tfar.heroicdeath;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;

public class ModCommand {

	public ModCommand(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(LiteralArgumentBuilder.<CommandSource>literal(HeroicDeath.MODID)
						.then(ReloadMessageCommand.register())
		);
	}



}
