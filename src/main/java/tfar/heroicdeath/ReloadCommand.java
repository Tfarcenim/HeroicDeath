package tfar.heroicdeath;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.command.TextComponentHelper;

public class ReloadCommand extends CommandBase {
	@Override
	public String getName() {
		return "reload";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "commands.heroicdeath.reload.usage";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		Setup.reload();
		sender.sendMessage(TextComponentHelper.createComponentTranslation(sender, "commands.heroicdeath.reload.success"));

	}
}
