package tfar.heroicdeath;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;

public class ModCommand {

	public static void command(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(LiteralArgumentBuilder.<CommandSource>literal(HeroicDeath.MODID)
				.then(reload())
		);
	}

	public static ArgumentBuilder<CommandSource, ?> reload() {
		return Commands.literal("reload")
				.requires(cs->cs.hasPermissionLevel(2)) //permission
				.executes(ctx -> {
							Setup.reload();
							ctx.getSource().sendFeedback( new TranslationTextComponent("commands.heroicdeath.reload.success"),true);
							return 0;
						}
				);
	}
}
