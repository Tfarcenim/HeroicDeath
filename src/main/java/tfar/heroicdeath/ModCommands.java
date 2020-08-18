package tfar.heroicdeath;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;

public class ModCommands extends CommandTreeBase {

	public ModCommands() {
		addSubcommand(new ReloadCommand());
	}

	@Override
	public String getName() {
		return "heroicdeath";
	}

	@Override
	public String getUsage(ICommandSender iCommandSender) {
		return "commands.heroicdeath.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}
}
