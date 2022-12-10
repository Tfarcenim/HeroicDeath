package tfar.heroicdeath;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;

public class ModCommand {

	public static void command(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(LiteralArgumentBuilder.<CommandSourceStack>literal(HeroicDeath.MODID)
				.then(reload())
		);
	}

	public static ArgumentBuilder<CommandSourceStack, ?> reload() {
		return Commands.literal("reload")
				.requires(cs->cs.hasPermission(2)) //permission
				.executes(ctx -> {
							Setup.reload();
							ctx.getSource().sendSuccess( new TranslatableComponent("commands.heroicdeath.reload.success"),true);
							return 0;
						}
				);
	}
}
