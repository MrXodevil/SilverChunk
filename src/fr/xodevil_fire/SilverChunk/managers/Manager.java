package fr.xodevil_fire.SilverChunk.managers;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.xodevil_fire.SilverChunk.Core;
import fr.xodevil_fire.SilverChunk.types.Chunk;

public class Manager {
	
	public static boolean isNumericInteger(String s)
	{
		try
		{
			@SuppressWarnings("unused")
			int i = Integer.parseInt(s);
		}
		catch (Exception e)
		{
			@SuppressWarnings("unused")
			int i;
			return false;
		}
		return true;
	}
	
	public static boolean isNumericDouble(String s)
	{
		try
		{
			@SuppressWarnings("unused")
			double i = Double.parseDouble(s);
		}
		catch (Exception e)
		{
			@SuppressWarnings("unused")
			double i;
			return false;
		}
		return true;
	}
	
	public static void deny(Command cmd, CommandSender sender, String[] args)
	{
		StringBuilder message = new StringBuilder("");
		for (String part : args) 
		{
			if (!message.toString().equals(""))
			message.append(" ");
			message.append(part);
		}
		sender.sendMessage(ChatColor.RED + "Vous n'avez pas la permission d'éxecuter cette commande !");
		System.out.println(ChatColor.RED + "La commande /" + cmd.getName() + " " + message + " a été refusée à  " + sender.getName() + " pour la raison suivante : Ce joueur n'a pas la permission d'éxecuter cette commande.");
	}
	
	public static void debug(Command cmd, CommandSender sender, String[] args)
	{
		StringBuilder message = new StringBuilder("");
		for (String part : args) 
		{
			if (!message.toString().equals(""))
			message.append(" ");
			message.append(part);
		}
		System.out.println(sender.getName() + " vient d'utiliser la commande /" + cmd.getName() + " " + message + ".");
	}
	
	public static Chunk getChunk(World w, Integer x, Integer z) {
		for (Chunk chunk : Core.chunk) {
			if (chunk.getWorld().equals(w) && chunk.getX().equals(x) && chunk.getZ().equals(z)) {
				return chunk;
			}
		}
		return null;
	}
	
	public static Boolean hasChunk(World w, Integer x, Integer z) {
		for (Chunk chunk : Core.chunk) {
			if (chunk.getWorld().equals(w) && chunk.getX().equals(x) && chunk.getZ().equals(z)) {
				return true;
			}
		}
		return false;
	}

}
