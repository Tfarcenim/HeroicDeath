package tfar.heroicdeath;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class ModCommand {

	public static void command(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(LiteralArgumentBuilder.<CommandSourceStack>literal(HeroicDeath.MODID)
				.then(reload())
				.then(defaults())
		);
	}

	public static ArgumentBuilder<CommandSourceStack, ?> reload() {
		return Commands.literal("reload")
				.requires(cs->cs.hasPermission(2)) //permission
				.executes(ctx -> {
							Setup.reload();
							ctx.getSource().sendSuccess(Component.translatable("commands.heroicdeath.reload.success"),true);
							return 0;
						}
				);
	}

	public static ArgumentBuilder<CommandSourceStack, ?> defaults() {
		return Commands.literal("defaults")
				.requires(cs->cs.hasPermission(2)) //permission
				.executes(ctx -> {
							Setup.reload();
							ctx.getSource().sendSuccess(Component.translatable("commands.heroicdeath.reload.success"),true);
							return 0;
						}
				);
	}

}
