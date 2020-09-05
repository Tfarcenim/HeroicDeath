package tfar.heroicdeath;

import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;

public class ReloadMessageCommand {


	public static ArgumentBuilder<CommandSource, ?> register() {
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
