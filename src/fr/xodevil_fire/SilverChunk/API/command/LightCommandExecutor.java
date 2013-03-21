package fr.xodevil_fire.SilverChunk.API.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class LightCommandExecutor implements CommandExecutor {

    public abstract boolean onCommand(LightCommand cmd);
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        LightCommand lightCmd = new LightCommand(sender, cmd, args);
    	return onCommand(lightCmd);
    }
	
}
