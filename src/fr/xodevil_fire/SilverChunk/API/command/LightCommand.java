package fr.xodevil_fire.SilverChunk.API.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.xodevil_fire.SilverChunk.Core;


public class LightCommand {
	
	private CommandSender sender;
	private Command cmd;
	private String[] args;
	
	public LightCommand(CommandSender sender, Command cmd, String[] args) {
		this.sender = sender;
		this.cmd = cmd;
		this.args = args;
	}
	
	public Command getCmd() {
		return cmd;
	}
	
	public void setCmd(Command cmd) {
		this.cmd = cmd;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}
	
	public String getArg(Integer i) {
		return args[i];
	}
	
	public void setArg(Integer i, String arg) {
		this.args[i] = arg;
	}

	public CommandSender getSender() {
		return sender;
	}

	public void setSender(CommandSender sender) {
		this.sender = sender;
	}
	
	public Player getPlayer() {
		return (Player) sender;
	}
	
	public Player getPlayer(String player) {
		return Bukkit.getServer().getPlayer(player);
	}
	
	public boolean isPlayer() {
		if ((sender instanceof Player)) {
			return true;
		} else { return false; }
	}
	
	public boolean checkPermission(String permission, LightCommand cmd) {
		if (cmd.isPlayer()) {
			if (!cmd.getPlayer().hasPermission(permission)) {
				fr.xodevil_fire.SilverChunk.managers.Manager.deny(cmd.getCmd(), cmd.getSender(), cmd.getArgs());
		        return false;
			}
		}
		return true;
	}
	
	public boolean hasPermission(CommandSender sender, String permission) {
		if (sender.hasPermission(permission)) {
			return true;
		} else { return false; }
	}
	
	public boolean hasPermission(Player player, String permission) {
		if (player.hasPermission(permission)) {
			return true;
		} else { return false; }
	}
	
	public void sendPrefixedMessage(String message) {
		sender.sendMessage(Core.prefix + message);
	}
	
	public void sendPublicPrefixedMessage(String message) {
		sender.sendMessage(Core.publicPrefix + message);
	}

}
